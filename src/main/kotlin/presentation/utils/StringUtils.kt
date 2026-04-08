package presentation.utils

import presentation.exception.ParserException
import java.math.BigDecimal

object StringUtils {
    private val ZEROS = "0*$".toRegex()
    private val EXTRA = "\\.$".toRegex()

    fun prepareNumber(value: String): String {
        var result = value.replace(",", ".").trim()
        if (result.isEmpty()) return ""

        result = when {
            result.startsWith(".") -> "0$result"
            result.startsWith("-.") -> result.replace("-.", "-0.")
            else -> result
        }

        return removeZeros(if (result.startsWith("-")) {
            "-" + result.substring(1).replaceFirst("^0+(?=\\d)".toRegex(), "")
        } else {
            result.replaceFirst("^0+(?=\\d)".toRegex(), "")
        })
    }

    fun parseBigDecimal(value: String): BigDecimal {
        try {
            return prepareNumber(value).toBigDecimal()
        } catch (e: NumberFormatException) {
            if (value.isEmpty()) {
                throw ParserException("Значение не может быть пустым")
            } else {
                throw ParserException("Значение <$value> должно быть числом")
            }
        }
    }

    fun removeZeros(value: String): String {
        if (value.isEmpty() || !value.contains(".")) return value
        return value
            .replace(ZEROS, "")
            .replace(EXTRA, "")
    }
}
