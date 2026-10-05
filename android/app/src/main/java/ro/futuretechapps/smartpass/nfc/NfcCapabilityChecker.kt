package ro.futuretechapps.smartpass.nfc

import android.content.Context
import android.content.pm.PackageManager
import android.nfc.NfcAdapter

class NfcCapabilityChecker(
    private val context: Context
) {

    fun check(): NfcCapabilities {

        val packageManager =
            context.packageManager

        val isNfcSupported =
            packageManager.hasSystemFeature(
                PackageManager.FEATURE_NFC
            )

        val isHceSupported =
            packageManager.hasSystemFeature(
                PackageManager.FEATURE_NFC_HOST_CARD_EMULATION
            )

        val nfcAdapter =
            NfcAdapter.getDefaultAdapter(context)

        val isNfcEnabled =
            nfcAdapter?.isEnabled == true

        return NfcCapabilities(
            isNfcSupported = isNfcSupported,
            isNfcEnabled = isNfcEnabled,
            isHceSupported = isHceSupported
        )
    }
}