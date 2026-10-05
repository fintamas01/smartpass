package ro.futuretechapps.smartpass.ui.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ro.futuretechapps.smartpass.data.repository.AuthRepository
import ro.futuretechapps.smartpass.nfc.NfcCapabilityChecker

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        AuthRepository(application)

    private val nfcCapabilityChecker =
        NfcCapabilityChecker(application)

    private val _uiState =
        MutableStateFlow(
            HomeUiState(
                nfcCapabilities =
                    nfcCapabilityChecker.check()
            )
        )

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()

    fun refreshNfcStatus() {

        _uiState.update {
            it.copy(
                nfcCapabilities =
                    nfcCapabilityChecker.check()
            )
        }
    }

    fun logout(
        onLogoutComplete: () -> Unit
    ) {

        if (_uiState.value.isLoggingOut) {
            return
        }

        _uiState.update {
            it.copy(
                isLoggingOut = true
            )
        }

        viewModelScope.launch {

            try {
                repository.logout()
            } catch (_: Exception) {
                // Local token is cleared by repository.
            }

            _uiState.update {
                it.copy(
                    isLoggingOut = false
                )
            }

            onLogoutComplete()
        }
    }
}