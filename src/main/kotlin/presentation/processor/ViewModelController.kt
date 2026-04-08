package presentation.processor

import androidx.compose.runtime.mutableStateOf
import core.exception.ExpressionException
import core.exception.InitException
import core.exception.SolverException
import core.model.Border
import core.model.Epsilon
import core.solver.SolverFactory
import kotlinx.coroutines.*
import presentation.exception.ParserException
import presentation.model.IntegralFactory
import presentation.utils.StringUtils

class ViewModelController {
    val integrals = IntegralFactory.createKeys()
    val solvers = SolverFactory.createKeys()

    val currentIntegral = mutableStateOf(integrals.first())
    val currentSolver = mutableStateOf(solvers.first())

    val leftBorder = mutableStateOf("-5")
    val rightBorder = mutableStateOf("5")
    val rawEpsilon = mutableStateOf("0.01")

    val message = mutableStateOf("Начните работу")
    val error = mutableStateOf(false)

    fun leftBorderValue(): String {
        return StringUtils.prepareNumber(leftBorder.value)
    }

    fun rightBorderValue(): String {
        return StringUtils.prepareNumber(rightBorder.value)
    }

    private val isLoading = mutableStateOf(false)
    private val scope = CoroutineScope(Dispatchers.Default + Job())

    fun execute() {
        if (isLoading.value) return

        scope.launch {
            try {
                isLoading.value = true

                val solver = SolverFactory.create(currentSolver.value)
                val integral = IntegralFactory.create(currentIntegral.value)

                val left = StringUtils.parseBigDecimal(leftBorder.value)
                val right = StringUtils.parseBigDecimal(rightBorder.value)
                val numberEpsilon = StringUtils.parseBigDecimal(rawEpsilon.value)

                val border = Border(left, right)
                val epsilon = Epsilon(numberEpsilon)

                message.value =
                    "Ответ: ${
                        StringUtils.removeZeros(
                            StringUtils.checkZero(
                                solver.solve(
                                    integral.expression,
                                    border,
                                    epsilon
                                )
                            ).toString()
                        )
                    }"
                error.value = false
            } catch (e: Exception) {
                error.value = true
                message.value = when (e) {
                    is InitException, is SolverException, is ParserException, is ExpressionException -> e.message!!
                    is NumberFormatException -> "Произошла вычислительная ошибка"
                    else -> "Неожиданная ошибка"
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    fun clear() {
        scope.cancel()
    }
}
