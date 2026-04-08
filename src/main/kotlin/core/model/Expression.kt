package core.model

import core.exception.ExpressionException
import java.math.BigDecimal

class Expression(
    private val function: Function,
    private val points: List<Point>,
    private val check: Condition = Condition({ true }),
) {
    fun calculate(x: BigDecimal): BigDecimal {
        val point = points.find { it.x == x }
        if (!check.verify(x)) {
            throw ExpressionException(check.message)
        }
        return when (point) {
            is Point.Removable -> point.fixed
            is Point -> throw ExpressionException("Неустранимая точка разрыва в ${x.toFloat()}")
            else -> function.apply(x)
        }
    }

    fun dangerousPoints(): List<Point> {
        return points.filter { point -> point.type != PointType.REMOVABLE }
    }

    fun interface Function {
        fun apply(x: BigDecimal): BigDecimal
    }
}
