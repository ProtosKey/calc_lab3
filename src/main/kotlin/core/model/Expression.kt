package text.model

import java.math.BigDecimal

class Expression(private val function: Function) {
    fun calculate(x: BigDecimal): BigDecimal {
        return function.apply(x)
    }

    fun interface Function {
        fun apply(x: BigDecimal): BigDecimal
    }
}
