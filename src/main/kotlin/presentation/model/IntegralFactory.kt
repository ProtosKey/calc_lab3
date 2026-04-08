package presentation.model

import core.basic.Factory
import core.model.Expression
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.*

object IntegralFactory : Factory<IntegralType, Integral> {
    private val mc = MathContext.DECIMAL128

    private val INTEGRALS = linkedMapOf(
        IntegralType.SQUARE to Integral(Expression({ it.pow(2) }, listOf())),

        IntegralType.CUBE_PLUS to Integral(Expression({
            it.pow(3) + it.multiply(BigDecimal("2"))
        }, listOf())),

        IntegralType.EXPONENT to Integral(Expression({
            BigDecimal(exp(it.toDouble()), mc)
        }, listOf())),

        IntegralType.LINEAR to Integral(Expression({ it }, listOf())),

        IntegralType.SIN to Integral(Expression({
            BigDecimal(sin(it.toDouble()), mc)
        }, listOf())),

        IntegralType.COS to Integral(Expression({
            BigDecimal(cos(it.toDouble()), mc)
        }, listOf())),

        IntegralType.POLYNOMIAL to Integral(Expression({
            it.pow(4) - it.pow(2).multiply(BigDecimal("3")) + BigDecimal("5")
        }, listOf())),

        IntegralType.SIN_SQUARE to Integral(Expression({
            BigDecimal(sin(it.toDouble()).pow(2), mc)
        }, listOf())),

        IntegralType.X_SIN to Integral(Expression({
            it.multiply(BigDecimal(sin(it.toDouble()), mc))
        }, listOf())),

        IntegralType.ABS to Integral(Expression({ it.abs() }, listOf())),

        IntegralType.EXP_SUM to Integral(Expression({
            val x = it.toDouble()
            BigDecimal(exp(x) + exp(-x), mc)
        }, listOf())),

        IntegralType.SQRT to Integral(Expression({
            BigDecimal(sqrt(it.toDouble()), mc)
        }, listOf())),

        IntegralType.LN to Integral(Expression({
            BigDecimal(ln(it.toDouble()), mc)
        }, listOf()))
    )

    override fun create(type: IntegralType): Integral = INTEGRALS[type]!!

    override fun createKeys(): List<IntegralType> = INTEGRALS.keys.toList()
}
