import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import presentation.processor.ViewModelController
import view.screen.mainScreen

fun main() = application {
    val controller = remember { ViewModelController() }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Интегральный калькулятор",
        state = rememberWindowState(width = 900.dp, height = 700.dp),
        resizable = false
    ) {
        mainScreen(controller)
    }
}
