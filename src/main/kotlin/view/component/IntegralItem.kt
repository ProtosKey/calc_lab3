package view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import view.utils.Colors
import view.utils.Sizes

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
            .height(Sizes.minHeight),
        shape = RoundedCornerShape(Sizes.round),
        color = if (isSelected) Color.White else Color.Transparent,
        border = if (isSelected) BorderStroke(Sizes.skip, Colors.divider) else null,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Sizes.bigIndent),
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
