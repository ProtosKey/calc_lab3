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

class SimpsonSolver : CanSolve {
    companion object {
        private const val MAX_ITERATIONS = 20
        private const val METHOD = 4
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

        result += expression.calculate(border.left)
        result += expression.calculate(border.right)

        for (i in 1 until n) {
            result += (if (i % 2 == 1) 4 else 2).toBigDecimal() * expression
                .calculate(border.left + step * i.toBigDecimal())
        }

        return result * step.divide(3.toBigDecimal(), 40, RoundingMode.HALF_UP)
    }
}
