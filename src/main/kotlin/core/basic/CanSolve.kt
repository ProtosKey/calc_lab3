package core.basic

import core.exception.InitException
import core.model.Border
import core.model.Epsilon
import core.model.Expression
import core.utils.ErrorMessages
import java.math.BigDecimal

interface CanSolve {
    fun solve(expression: Expression, border: Border, epsilon: Epsilon): BigDecimal {
        var n = 4
        var k = 0
        var result: BigDecimal
        var newResult: BigDecimal = calculate(expression, border, n)
        do {
            n *= 2
            result = newResult
            newResult = calculate(expression, border, n)

            if (iterations(k++)) {
                throw InitException(ErrorMessages.MAX_ITERATIONS.message)
            }
        } while (check(result, newResult) >= epsilon.epsilon)

        return newResult
    }

    fun iterations(k: Int): Boolean
    fun check(result: BigDecimal, newResult: BigDecimal): BigDecimal
    fun calculate(expression: Expression, border: Border, n: Int): BigDecimal
}
