package ro.futuretechapps.smartpass.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ro.futuretechapps.smartpass.data.repository.AuthRepository

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                emailError = null,
                loginError = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                passwordError = null,
                loginError = null
            )
        }
    }

    fun togglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                passwordVisible = !currentState.passwordVisible
            )
        }
    }

    fun login() {

        if (_uiState.value.isLoading) {
            return
        }

        if (!validateLogin()) {
            return
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    loginError = null
                )
            }

            try {

                repository.login(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSucceeded = true
                    )
                }

            } catch (exception: HttpException) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginError = when (exception.code()) {

                            422 -> {
                                "Incorrect email or password"
                            }

                            401 -> {
                                "Unauthorized"
                            }

                            else -> {
                                "Login failed (${exception.code()})"
                            }
                        }
                    )
                }

            } catch (exception: IOException) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginError = "Cannot connect to SmartPass server"
                    )
                }

            } catch (exception: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginError = "An unexpected error occurred"
                    )
                }
            }
        }
    }

    fun consumeLoginSuccess() {
        _uiState.update {
            it.copy(
                loginSucceeded = false
            )
        }
    }

    private fun validateLogin(): Boolean {

        val currentState = _uiState.value
        val trimmedEmail = currentState.email.trim()

        val emailError = when {

            trimmedEmail.isEmpty() -> {
                "Email is required"
            }

            !isValidEmail(trimmedEmail) -> {
                "Enter a valid email address"
            }

            else -> null
        }

        val passwordError = when {

            currentState.password.isEmpty() -> {
                "Password is required"
            }

            currentState.password.length < 8 -> {
                "Password must be at least 8 characters"
            }

            else -> null
        }

        _uiState.update {
            it.copy(
                email = trimmedEmail,
                emailError = emailError,
                passwordError = passwordError,
                loginError = null
            )
        }

        return emailError == null && passwordError == null
    }

    private fun isValidEmail(email: String): Boolean {
        return EMAIL_REGEX.matches(email)
    }

    companion object {

        private val EMAIL_REGEX =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    }
}