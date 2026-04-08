package core.solver

import core.basic.CanSolve
import core.model.Border
import core.model.Expression
import core.utils.IntegralUtils
import java.math.BigDecimal
import java.math.RoundingMode

class SimpsonSolver : CanSolve {
    companion object {
        private const val MAX_ITERATIONS = 20
        private const val METHOD = 4
    }

    override fun iterations(k: Int): Boolean {
        return k >= MAX_ITERATIONS
    }

    override fun check(result: BigDecimal, newResult: BigDecimal): BigDecimal {
        return IntegralUtils.calcRungeRule(result, newResult, METHOD)
    }

    override fun calculate(expression: Expression, border: Border, n: Int): BigDecimal {
        var result = BigDecimal.ZERO
        val step = (border.right - border.left).divide(n.toBigDecimal(), 40, RoundingMode.HALF_UP)

        result += expression.calculate(border.left)
        result += expression.calculate(border.right)

        for (i in 1 until n) {
            result += (if (i % 2 == 1) 4 else 2).toBigDecimal() * expression
                .calculate(border.left + step * i.toBigDecimal())
        }

        return result * step.divide(3.toBigDecimal(), 40, RoundingMode.HALF_UP)
    }
}
