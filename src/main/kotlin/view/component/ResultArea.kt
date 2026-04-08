package view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import view.utils.Colors
import view.utils.Sizes

@Composable
fun resultArea(message: String, isError: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Sizes.indent)
    ) {
        Text(
            text = "Результат вычислений",
            color = Colors.textMain,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = Sizes.maxHeight)
                .background(
                    if (isError) Colors.sidebar
                    else Colors.sidebar,
                    RoundedCornerShape(Sizes.round)
                )
                .border(
                    width = Sizes.skip,
                    color = Color.Transparent,
                    // color = if (isError) Color(0xFFFA2D48).copy(alpha = 0.3f)
                    // else Colors.divider,
                    shape = RoundedCornerShape(Sizes.round)
                )
                .padding(Sizes.round),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                fontSize = 16.sp,
                lineHeight = 1.sp,
                textAlign = TextAlign.Center,
                fontWeight = if (isError) FontWeight.Bold else FontWeight.Normal,
                color = if (isError) Colors.accent else Colors.textMain,
            )
        }
    }
}
