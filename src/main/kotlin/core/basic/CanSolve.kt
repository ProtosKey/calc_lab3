package text.basic

import text.model.Border
import text.model.Epsilon
import text.model.Expression
import java.math.BigDecimal

interface CanSolve {
    fun solve(expression: Expression, border: Border, epsilon: Epsilon): BigDecimal
}
