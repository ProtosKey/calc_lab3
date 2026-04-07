package core.basic

import core.model.Border
import core.model.Epsilon
import core.model.Expression
import java.math.BigDecimal

interface CanSolve {
    fun solve(expression: Expression, border: Border, epsilon: Epsilon): BigDecimal
}
