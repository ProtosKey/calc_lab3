package presentation.model

import core.basic.Factory
import core.model.Expression
import kotlin.math.exp

object IntegralFactory : Factory<IntegralType, Integral> {
    private val INTEGRALS = linkedMapOf(
        IntegralType.SQUARE to Integral(Expression { it.pow(2) }),
        IntegralType.CUBE_PLUS to Integral(Expression { it.pow(3) + it.multiply(2.toBigDecimal()) }),
        IntegralType.EXPONENT to Integral(Expression { x -> java.math.BigDecimal.valueOf(exp(x.toDouble())) })
    )

    override fun create(type: IntegralType): Integral {
        return INTEGRALS[type]!!
    }

    override fun createKeys(): List<IntegralType> {
        return INTEGRALS.keys.toList()
    }
}
