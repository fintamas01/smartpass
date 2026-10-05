package ro.futuretechapps.smartpass.ui.screens.session

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ro.futuretechapps.smartpass.data.repository.AuthRepository

class SessionViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        AuthRepository(application)

    private val _uiState =
        MutableStateFlow(
            SessionUiState()
        )

    val uiState: StateFlow<SessionUiState> =
        _uiState.asStateFlow()

    init {
        checkSession()
    }

    fun checkSession() {

        _uiState.update {
            it.copy(
                status = SessionStatus.CHECKING,
                errorMessage = null
            )
        }

        if (!repository.hasStoredToken()) {

            _uiState.update {
                it.copy(
                    status =
                        SessionStatus.UNAUTHENTICATED
                )
            }

            return
        }

        viewModelScope.launch {

            try {

                repository.getCurrentUser()

                _uiState.update {
                    it.copy(
                        status =
                            SessionStatus.AUTHENTICATED
                    )
                }

            } catch (exception: HttpException) {

                if (
                    exception.code() == 401 ||
                    exception.code() == 419
                ) {

                    repository.clearStoredToken()

                    _uiState.update {
                        it.copy(
                            status =
                                SessionStatus.UNAUTHENTICATED
                        )
                    }

                } else {

                    _uiState.update {
                        it.copy(
                            status =
                                SessionStatus.ERROR,
                            errorMessage =
                                "Unable to verify session"
                        )
                    }
                }

            } catch (exception: IOException) {

                _uiState.update {
                    it.copy(
                        status =
                            SessionStatus.ERROR,
                        errorMessage =
                            "Cannot connect to SmartPass server"
                    )
                }

            } catch (exception: Exception) {

                repository.clearStoredToken()

                _uiState.update {
                    it.copy(
                        status =
                            SessionStatus.UNAUTHENTICATED
                    )
                }
            }
        }
    }
}