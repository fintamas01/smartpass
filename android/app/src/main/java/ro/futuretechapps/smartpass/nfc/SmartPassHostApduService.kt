package ro.futuretechapps.smartpass.nfc

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import ro.futuretechapps.smartpass.nfc.SmartPassApduProtocol.toHexString

class SmartPassHostApduService :
    HostApduService() {

    override fun processCommandApdu(
        commandApdu: ByteArray?,
        extras: Bundle?
    ): ByteArray {

        if (commandApdu == null) {
            return SmartPassApduProtocol
                .instructionNotSupportedStatus
        }

        Log.d(
            TAG,
            "Received APDU: " +
                    commandApdu.toHexString()
        )

        if (
            SmartPassApduProtocol
                .isSelectAidCommand(
                    commandApdu
                )
        ) {

            Log.d(
                TAG,
                "SmartPass AID selected"
            )

            return SmartPassApduProtocol
                .readyResponse
        }

        Log.d(
            TAG,
            "Unsupported APDU"
        )

        return SmartPassApduProtocol
            .instructionNotSupportedStatus
    }

    override fun onDeactivated(
        reason: Int
    ) {

        Log.d(
            TAG,
            "HCE deactivated. Reason: $reason"
        )
    }

    companion object {

        private const val TAG =
            "SmartPassHCE"
    }
}