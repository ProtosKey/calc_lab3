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

        val essentialPoints = checkPoints.filter { it.type == PointType.ESSENTIAL }
        val secondPoints = checkPoints.filter { it.type == PointType.SECOND }
        if (essentialPoints.isNotEmpty() || secondPoints.any { it.x == border.left || it.x == border.right }) {
            TODO("Добавить проверку сходимости")
        } else if (secondPoints.isNotEmpty()) {
            return secondPoints.indices.mapNotNull { index ->
                try {
                    if (index == 0)
                        Border(border.left, secondPoints[index].x - epsilon.epsilon)
                    else
                        Border(secondPoints[index - 1].x + epsilon.epsilon, secondPoints[index].x - epsilon.epsilon)
                } catch (e: InitException) {
                    null
                }
            }.plus(
                listOfNotNull(
                    try {
                        Border(secondPoints.last().x + epsilon.epsilon, border.right)
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
