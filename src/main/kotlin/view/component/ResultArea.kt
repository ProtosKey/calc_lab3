package view.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import view.utils.Colors
import view.utils.Sizes

@Composable
fun resultArea(message: String, isError: Boolean, isLoading: Boolean, onClear: () -> Unit) {
    val clipboardManager = LocalClipboardManager.current

    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )

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
                    Colors.main,
                    RoundedCornerShape(Sizes.round)
                )
                .border(
                    border = BorderStroke(
                        Sizes.skip,
                        Colors.divider
                    ),
                    shape = RoundedCornerShape(Sizes.round),
                )
                .padding(Sizes.round),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Sizes.largeIndent)
                ) {
                    /*
                    CircularProgressIndicator(
                        modifier = Modifier.size(Sizes.circle),
                        color = Colors.accent,
                        strokeWidth = Sizes.indent
                    )
                     */
                    Text(
                        text = "Вычисляем интегралы...",
                        fontSize = 16.sp,
                        color = Colors.textMain,
                        modifier = Modifier.graphicsLayer(alpha = alpha)
                    )
                }
            } else {
                Text(
                    text = message,
                    modifier = Modifier.padding(end = Sizes.maxHeight),
                    fontSize = 16.sp,
                    lineHeight = .5.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = if (isError) FontWeight.Bold else FontWeight.Normal,
                    color = if (isError) Colors.accent else Colors.textMain,
                )

                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(-Sizes.maxIndent)
                ) {
                    IconButton(
                        onClick = {
                            clipboardManager.setText(
                                AnnotatedString(
                                    message.split("Ответ:").last().trim()
                                        .split("Колчество итераций:").first().trim()
                                )
                            )
                        },
                        modifier = Modifier.size(Sizes.middleHeight)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ContentCopy,
                            contentDescription = "Копировать",
                            tint = Colors.textSecondary,
                        )
                    }

                    IconButton(
                        onClick = onClear,
                        modifier = Modifier.size(Sizes.middleHeight)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = "Удалить",
                            tint = Colors.textSecondary,
                        )
                    }
                }
            }
        }
    }
}
