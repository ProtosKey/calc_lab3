package core.solver

import core.basic.CanSolve
import core.exception.InitException
import core.model.Border
import core.model.Epsilon
import core.model.Expression
import core.solver.LeftSquareSolver.Companion
import core.utils.ErrorMessages
import core.utils.IntegralUtils
import java.math.BigDecimal
import java.math.RoundingMode

class TrapezeSolver : CanSolve {
    companion object {
        private const val MAX_ITERATIONS = 20
        private const val METHOD = 2
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

        for (i in 1..n) {
            result += (expression.calculate(border.left + step * i.toBigDecimal())
                    + expression.calculate(border.left + step * (i - 1)
                .toBigDecimal())).divide(2.toBigDecimal(), 40, RoundingMode.HALF_UP)
        }

        return result * step
    }
}
