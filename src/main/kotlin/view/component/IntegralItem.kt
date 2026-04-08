package view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import view.utils.Colors

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun integralItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color.White else Color.Transparent,
        border = if (isSelected) BorderStroke(1.dp, Colors.divider) else null,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            texDisplay(
                tex = label,
                size = 32f,
                color = Colors.textMain
            )
        }
    }
}
