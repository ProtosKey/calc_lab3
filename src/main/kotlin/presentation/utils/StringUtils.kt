package presentation.utils

import presentation.exception.ParserException
import java.math.BigDecimal

object StringUtils {
    private val ZEROS = "0*$".toRegex()
    private val EXTRA = "\\.$".toRegex()
    private val ZERO_EPSILON = BigDecimal("1E-79")

    fun prepareNumber(value: String): String {
        var result = value.replace(",", ".").trim()
        if (result.isEmpty()) return ""

        result = when {
            result.startsWith(".") -> "0$result"
            result.startsWith("-.") -> result.replace("-.", "-0.")
            else -> result
        }

        return removeZeros(
            if (result.startsWith("-")) {
                "-" + result.substring(1).replaceFirst("^0+(?=\\d)".toRegex(), "")
            } else {
                result.replaceFirst("^0+(?=\\d)".toRegex(), "")
            }
        )
    }

    fun parseBigDecimal(value: String): BigDecimal {
        try {
            1
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

    fun checkZero(value: BigDecimal): BigDecimal {
        return if ((value).abs() < ZERO_EPSILON) BigDecimal.ZERO else value
    }
}
