package ro.futuretechapps.smartpass.ui.screens.home

import ro.futuretechapps.smartpass.nfc.NfcCapabilities

data class HomeUiState(
    val nfcCapabilities: NfcCapabilities? = null,
    val isLoggingOut: Boolean = false
)