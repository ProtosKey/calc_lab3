package presentation.processor

import androidx.compose.runtime.mutableStateOf
import core.exception.InitException
import core.exception.SolverException
import core.model.Border
import core.model.Epsilon
import core.solver.SolverFactory
import presentation.exception.ParserException
import presentation.model.IntegralFactory
import presentation.utils.StringUtils

class ViewModelController {
    val integrals = IntegralFactory.createKeys()
    val solvers = SolverFactory.createKeys()

    val currentIntegral = mutableStateOf(integrals.first())
    val currentSolver = mutableStateOf(solvers.first())

    val leftBorder = mutableStateOf("0")
    val rightBorder = mutableStateOf("10")
    val rawEpsilon = mutableStateOf("0.01")

    val message = mutableStateOf("Начните работу")
    val error = mutableStateOf(false)

    fun execute() {
        try {
            error.value = false

            val solver = SolverFactory.create(currentSolver.value)
            val integral = IntegralFactory.create(currentIntegral.value)

            val left = StringUtils.parseBigDecimal(leftBorder.value)
            val right = StringUtils.parseBigDecimal(rightBorder.value)
            val numberEpsilon = StringUtils.parseBigDecimal(rawEpsilon.value)

            val border = Border(left, right)
            val epsilon = Epsilon(numberEpsilon)

            message.value = "Ответ ${solver.solve(integral.expression, border, epsilon)}"
        } catch (e: Exception) {
            error.value = true
            message.value = when (e) {
                is InitException, is SolverException, is ParserException -> e.message!!
                else -> "Неожиданная ошибка"
            }
        }
    }
}
