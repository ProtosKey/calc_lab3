package core.model

import text.exception.InitException
import java.math.BigDecimal

class Epsilon(val epsilon: BigDecimal) {
    init {
        if (epsilon <= BigDecimal.ZERO) {
            throw InitException("Погрешность должна быть положительной")
        }
    }
}