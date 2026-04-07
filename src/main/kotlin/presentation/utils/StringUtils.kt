package presentation.utils

import presentation.exception.ParserException
import java.math.BigDecimal

object StringUtils {
    private fun prepareNumber(value: String): String {
        val result = value.replace(",", ".").trim()
        if (result.isEmpty()) return "0"
        return when {
            result.startsWith(".") -> "0$result"
            result.startsWith("-.") -> result.replace("-.", "-0.")
            else -> result
        }
    }

    fun parseBigDecimal(value: String): BigDecimal {
        try {
            return prepareNumber(value).toBigDecimal()
        } catch (e: NumberFormatException) {
            throw ParserException("Значение <$value> должно быть числом")
        }
    }
}
