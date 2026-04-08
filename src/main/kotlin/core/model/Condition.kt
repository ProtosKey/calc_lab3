package core.model

import java.math.BigDecimal

class Condition(private val function: Function, val message: String = "Всегда выполнено") {
    fun verify(x: BigDecimal): Boolean {
        return function.check(x)
    }

    fun interface Function {
        fun check(x: BigDecimal): Boolean
    }
}
