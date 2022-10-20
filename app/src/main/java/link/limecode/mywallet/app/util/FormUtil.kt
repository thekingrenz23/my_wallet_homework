package link.limecode.mywallet.app.util

object FormUtil {

    fun isValidCurrency(value: String): Boolean {
        return value.matches("^[0-9]*\\.?[0-9]*\$".toRegex())
    }
}

