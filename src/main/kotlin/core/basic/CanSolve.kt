package core.basic

import core.exception.InitException
import core.exception.SolverException
import core.model.*
import core.utils.ErrorMessages
import java.math.BigDecimal
import java.math.RoundingMode

interface CanSolve {
    companion object {
        private const val CHECK_RULE = 5
    }

    fun solve(expression: Expression, border: Border, epsilon: Epsilon): BigDecimal {
        var flag = false
        var result: BigDecimal
        var gap = epsilon.epsilon
        var newResult = calculatePreResult(expression, border, epsilon, gap)

        for (i in 1..CHECK_RULE) {
            result = newResult
            gap = gap.divide(BigDecimal("2"), 40, RoundingMode.HALF_UP)
            newResult = calculatePreResult(expression, border, epsilon, gap)

            if ((result - newResult).abs() <= epsilon.epsilon) {
                flag = true
                break
            }
        }

        if (flag) {
            return newResult
        }
        throw SolverException(ErrorMessages.INTEGRAL_DIVERGES.message)
    }

    private fun calculatePreResult(
        expression: Expression, border: Border, epsilon: Epsilon, gap: BigDecimal
    ): BigDecimal {
        var n = 4
        var k = 0
        val intervals = prepareIntervals(expression, border, gap)
        val length = intervals.sumOf { it.right - it.left }

        var result: BigDecimal
        var newResult = calculateByIntervals(length, n, intervals, expression)

        do {
            n *= 2
            result = newResult
            newResult = calculateByIntervals(length, n, intervals, expression)

            if (iterations(k++)) {
                throw SolverException(ErrorMessages.MAX_ITERATIONS.message)
            }
        } while (check(result, newResult) >= epsilon.epsilon)

        return newResult
    }

    private fun calculateByIntervals(
        length: BigDecimal,
        n: Int,
        intervals: List<Border>,
        expression: Expression
    ): BigDecimal {
        var result = BigDecimal.ZERO
        for (interval in intervals) {
            val share = (interval.right - interval.left).divide(length, 40, RoundingMode.HALF_UP)
            var steps = share.multiply(BigDecimal(n)).toInt()

            if (steps < 2) steps = 2
            if (steps % 2 != 0) steps++

            result += calculate(expression, interval, steps)
        }
        return result
    }

    private fun prepareIntervals(expression: Expression, border: Border, gap: BigDecimal): List<Border> {
        val checkPoints = expression.dangerousPoints().filter { it.x >= border.left && it.x <= border.right }

        if (checkPoints.isNotEmpty()) {
            return checkPoints.indices.mapNotNull { index ->
                try {
                    if (index == 0)
                        Border(border.left, checkPoints[index].x - gap)
                    else
                        Border(checkPoints[index - 1].x + gap, checkPoints[index].x - gap)
                } catch (e: InitException) {
                    null
                }
            }.plus(
                listOfNotNull(
                    try {
                        Border(checkPoints.last().x + gap, border.right)
                    } catch (e: InitException) {
                        null
                    }
                )
            ).sortedBy { elem -> elem.left }
        } else {
            return listOf(border)
        }
    }

    fun iterations(k: Int): Boolean
    fun check(result: BigDecimal, newResult: BigDecimal): BigDecimal
    fun calculate(expression: Expression, border: Border, n: Int): BigDecimal
}
