package ro.futuretechapps.smartpass.ui.screens.session

data class SessionUiState(
    val status: SessionStatus =
        SessionStatus.CHECKING,

    val errorMessage: String? = null
)