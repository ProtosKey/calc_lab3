package core.model

import core.exception.ExpressionException
import java.math.BigDecimal

class Expression(private val function: Function, val points: List<Point>) {
    fun calculate(x: BigDecimal): BigDecimal {
        val point = points.find { it.x == x }
        return when (point) {
            is Point.Removable -> point.fixed
            is Point -> throw ExpressionException("Неустранимая точка разрыва в ${x.toFloat()}")
            else -> function.apply(x)
        }
    }

    fun interface Function {
        fun apply(x: BigDecimal): BigDecimal
    }
}
