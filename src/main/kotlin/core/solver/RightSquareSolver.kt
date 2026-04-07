package text.solver

import text.basic.CanSolve
import text.exception.InitException
import text.model.Border
import text.model.Epsilon
import text.model.Expression
import text.utils.ErrorMessages
import text.utils.IntegralUtils
import java.math.BigDecimal
import java.math.RoundingMode

class RightSquareSolver : CanSolve {
    companion object {
        private const val MAX_ITERATIONS = 25
        private const val METHOD = 1
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
            result += expression.calculate(border.left + step * i.toBigDecimal())
        }

        return result
    }
}
