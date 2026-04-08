package view.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.scilab.forge.jlatexmath.TeXConstants
import org.scilab.forge.jlatexmath.TeXFormula
import view.utils.Colors
import java.awt.RenderingHints
import java.awt.image.BufferedImage

@Composable
fun texDisplay(
    tex: String,
    modifier: Modifier = Modifier,
    color: Color = Colors.textMain,
    size: Float = 60f,
) {
    val imageBitmap = remember(tex) {
        try {
            val formula = TeXFormula(tex)
            val icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, size)

            val image = BufferedImage(icon.iconWidth, icon.iconHeight, BufferedImage.TYPE_INT_ARGB)
            val graphics = image.createGraphics()

            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

            icon.paintIcon(null, graphics, 0, 0)
            graphics.dispose()

            image.toComposeImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    if (imageBitmap != null) {
        androidx.compose.foundation.Image(
            modifier = modifier,
            bitmap = imageBitmap,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color)
        )
    }
}
