package view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import presentation.processor.ViewModelController
import view.component.*
import view.utils.Colors

@Composable
fun mainScreen(controller: ViewModelController) {
    Row(modifier = Modifier.fillMaxSize().background(Colors.main)) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .fillMaxHeight()
                .background(Colors.sidebar)
                .drawBehind {
                    drawLine(
                        color = Colors.divider,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                .padding(top = 20.dp, start = 12.dp, end = 12.dp)
        ) {
            Text(
                "Интегралы",
                color = Colors.textMain,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )

            controller.integrals.forEach { integral ->
                integralItem(
                    label = integral.label,
                    isSelected = controller.currentIntegral.value == integral,
                    onClick = { controller.currentIntegral.value = integral }
                )
                Spacer(Modifier.height(4.dp))
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f).fillMaxHeight().padding(12.dp),
        ) {
            Text(
                "Калькулятор",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Colors.textMain
            )

            Spacer(Modifier.height(4.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(100.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Colors.divider)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    var left = controller.leftBorderValue()
                    var right = controller.rightBorderValue()

                    left = left.ifEmpty { "A" }
                    right = right.ifEmpty { "B" }

                    val integralTex = "\\int_{$left}^{$right} " +
                            "${controller.currentIntegral.value.label} \\, dx"
                    texDisplay(integralTex)
                }
            }

            Spacer(Modifier.height(1.dp))

            solverSelector(controller)

            Spacer(Modifier.height(1.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                input("Граница A", controller.leftBorder, Modifier.weight(1f))
                input("Граница B", controller.rightBorder, Modifier.weight(1f))
                input("Точность", controller.rawEpsilon, Modifier.weight(1f))
            }

            Spacer(Modifier.height(1.dp))

            resultArea(
                message = controller.message.value,
                isError = controller.error.value
            )

            Spacer(modifier = Modifier.weight(1f))

            calcButton("Рассчитать значение интеграла", onClick = { controller.execute() })
        }
    }
}
