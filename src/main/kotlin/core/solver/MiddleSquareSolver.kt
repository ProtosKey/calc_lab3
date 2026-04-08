package core.solver

import core.basic.CanSolve
import core.exception.InitException
import core.model.Border
import core.model.Epsilon
import core.model.Expression
import core.utils.ErrorMessages
import core.utils.IntegralUtils
import java.math.BigDecimal
import java.math.RoundingMode

class MiddleSquareSolver : CanSolve {
    companion object {
        private const val MAX_ITERATIONS = 20
        private const val METHOD = 2
    }

    override fun solve(expression: Expression, border: Border, epsilon: Epsilon): BigDecimal {
        var n = 4
        var k = 0
        var result: BigDecimal
        var newResult: BigDecimal = calculate(expression, border, n)
        do {
            n *= 2
            result = newResult
            newResult = calculate(expression, border, n)

            if (k++ > MAX_ITERATIONS) {
                throw InitException(ErrorMessages.MAX_ITERATIONS.message)
            }
        } while (IntegralUtils.calcRungeRule(result, newResult, METHOD) >= epsilon.epsilon)

        return newResult
    }

    private fun calculate(expression: Expression, border: Border, n: Int): BigDecimal {
        var result = BigDecimal.ZERO
        val step = (border.right - border.left).divide(n.toBigDecimal(), 40, RoundingMode.HALF_UP)

        for (i in 1..n) {
            result += expression.calculate(border.left + step * (i.toBigDecimal() + (i - 1).toBigDecimal())
                .divide(2.toBigDecimal(), 40, RoundingMode.HALF_UP))
        }

        return result * step
    }
}
