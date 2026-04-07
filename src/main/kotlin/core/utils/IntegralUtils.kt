package core.utils

import java.math.BigDecimal
import java.math.RoundingMode

object IntegralUtils {
    fun calcRungeRule(first: BigDecimal, second: BigDecimal, method: Int): BigDecimal {
        return (second - first).divide(BigDecimal.valueOf(2)
            .pow(method) - BigDecimal.ONE, 40, RoundingMode.HALF_UP)
    }
}
