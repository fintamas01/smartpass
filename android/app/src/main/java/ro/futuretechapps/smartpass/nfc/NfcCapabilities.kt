package ro.futuretechapps.smartpass.nfc

data class NfcCapabilities(
    val isNfcSupported: Boolean,
    val isNfcEnabled: Boolean,
    val isHceSupported: Boolean
) {

    val isSmartPassReady: Boolean
        get() =
            isNfcSupported &&
                    isNfcEnabled &&
                    isHceSupported
}