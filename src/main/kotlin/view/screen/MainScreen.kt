package view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import presentation.processor.ViewModelController
import view.component.*
import view.utils.Colors
import view.utils.Sizes

@Composable
fun mainScreen(controller: ViewModelController) {
    DisposableEffect(Unit) {
        onDispose {
            controller.clear()
        }
    }

    val scrollState = rememberScrollState()
    Row(modifier = Modifier.fillMaxSize().background(Colors.main)) {
        Column(
            modifier = Modifier
                .width(Sizes.columnsWidth)
                .fillMaxHeight()
                .background(Colors.sidebar)
                .drawBehind {
                    drawLine(
                        color = Colors.divider,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = Sizes.skip.toPx()
                    )
                }
                .verticalScroll(scrollState)
                .padding(top = Sizes.maxIndent, start = Sizes.largeIndent, end = Sizes.largeIndent)
        ) {
            Text(
                "Функции",
                color = Colors.textMain,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = Sizes.indent, bottom = Sizes.bigIndent)
            )

            controller.integrals.forEach { integral ->
                integralItem(
                    label = integral.label,
                    isSelected = controller.currentIntegral.value == integral,
                    onClick = { controller.currentIntegral.value = integral }
                )
                Spacer(Modifier.height(Sizes.indent))
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f).fillMaxHeight().padding(Sizes.largeIndent),
        ) {
            Text(
                "Калькулятор",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Colors.textMain
            )

            Spacer(Modifier.height(Sizes.indent))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(Sizes.round),
                modifier = Modifier.fillMaxWidth().height(Sizes.maxHeight),
                border = androidx.compose.foundation.BorderStroke(Sizes.skip, Colors.divider)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    var left = controller.leftBorderValue()
                    var right = controller.rightBorderValue()

                    left = left.ifEmpty { "a" }
                    right = right.ifEmpty { "b" }

                    val integralTex = "\\int_{$left}^{$right} " +
                            "${controller.currentIntegral.value.label} \\, dx"
                    texDisplay(integralTex)
                }
            }

            Spacer(Modifier.height(Sizes.skip))

            solverSelector(controller)

            Spacer(Modifier.height(Sizes.skip))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Sizes.bigIndent)) {
                input("Левая граница", controller.leftBorder, Modifier.weight(1f))
                input("Правая граница", controller.rightBorder, Modifier.weight(1f))
                input("Точность", controller.rawEpsilon, Modifier.weight(1f))
            }

            Spacer(Modifier.height(Sizes.skip))

            SelectionContainer {
                resultArea(
                    message = controller.message.value,
                    isError = controller.error.value,
                    onClear = {controller.message.value = "" }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            calcButton("Рассчитать значение интеграла", onClick = { controller.execute() })
        }
    }
}
