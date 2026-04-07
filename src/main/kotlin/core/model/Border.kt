package core.model

import text.exception.InitException
import java.math.BigDecimal

class Border(val left: BigDecimal, val right: BigDecimal) {
    init {
        if (left >= right) {
            throw InitException("Левая граница должна быть меньше правой")
        }
    }
}
