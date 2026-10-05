package ro.futuretechapps.smartpass.ui.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.futuretechapps.smartpass.data.repository.AuthRepository

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        AuthRepository(application)

    fun logout(
        onLogoutComplete: () -> Unit
    ) {

        viewModelScope.launch {

            try {
                repository.logout()
            } catch (_: Exception) {
                // Local token is cleared
                // by AuthRepository finally block.
            }

            onLogoutComplete()
        }
    }
}