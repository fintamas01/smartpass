package ro.futuretechapps.smartpass.nfc

object SmartPassApduProtocol {

    const val AID = "F0010203040506"

    private val aidBytes =
        AID.hexToByteArray()

    val successStatus =
        byteArrayOf(
            0x90.toByte(),
            0x00.toByte()
        )

    val instructionNotSupportedStatus =
        byteArrayOf(
            0x6D.toByte(),
            0x00.toByte()
        )

    val readyResponse =
        "SMARTPASS_READY_V1"
            .toByteArray(Charsets.UTF_8) +
                successStatus

    fun isSelectAidCommand(
        commandApdu: ByteArray
    ): Boolean {

        if (commandApdu.size < 5) {
            return false
        }

        val cla = commandApdu[0]
        val ins = commandApdu[1]
        val p1 = commandApdu[2]
        val p2 = commandApdu[3]

        if (
            cla != 0x00.toByte() ||
            ins != 0xA4.toByte() ||
            p1 != 0x04.toByte() ||
            p2 != 0x00.toByte()
        ) {
            return false
        }

        val aidLength =
            commandApdu[4]
                .toInt() and 0xFF

        if (
            commandApdu.size <
            5 + aidLength
        ) {
            return false
        }

        val selectedAid =
            commandApdu.copyOfRange(
                5,
                5 + aidLength
            )

        return selectedAid.contentEquals(
            aidBytes
        )
    }

    fun ByteArray.toHexString(): String {
        return joinToString("") {
            "%02X".format(
                it.toInt() and 0xFF
            )
        }
    }

    private fun String.hexToByteArray():
            ByteArray {

        require(length % 2 == 0)

        return chunked(2)
            .map {
                it.toInt(16).toByte()
            }
            .toByteArray()
    }
}