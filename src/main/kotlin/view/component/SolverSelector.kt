package view.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.processor.ViewModelController
import view.utils.Colors

@Composable
fun solverSelector(controller: ViewModelController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Метод вычислительного решения",
            color = Colors.textMain,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(16.dp),
            color = Colors.sidebar
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                controller.solvers.forEach { solverType ->
                    val isSelected = controller.currentSolver.value == solverType

                    Surface(
                        onClick = { controller.currentSolver.value = solverType },
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) Colors.main else Color.Transparent,
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(
                            1.dp,
                            Colors.divider
                        ) else null,
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = solverType.label,
                                color = Colors.textMain,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}
