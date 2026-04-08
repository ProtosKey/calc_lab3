package core.basic

import core.exception.InitException
import core.exception.SolverException
import core.model.*
import core.utils.ErrorMessages
import java.math.BigDecimal
import java.math.RoundingMode

interface CanSolve {
    fun solve(expression: Expression, border: Border, epsilon: Epsilon): BigDecimal {
        var n = 4
        var k = 0
        val intervals = prepareIntervals(expression, border, epsilon)
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

            println(newResult)
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

    private fun prepareIntervals(expression: Expression, border: Border, epsilon: Epsilon): List<Border> {
        val checkPoints = expression.dangerousPoints().filter { it.x >= border.left && it.x <= border.right }
        val gap = epsilon.epsilon * BigDecimal("1E-5")

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
