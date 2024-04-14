package dev.maxsiomin.common.presentation.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

/** Bottom corner radius is 0dp, top corner radius is [topCornerRadius] */
class TopRoundedCornerShape(private val topCornerRadius: Dp) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val topCornerRadiusPx = with(density) { topCornerRadius.toPx() }

        val path = Path().apply {
            // Define the path for the top left corner
            moveTo(0f, topCornerRadiusPx)
            quadraticBezierTo(0f, 0f, topCornerRadiusPx, 0f)

            // Line to the top right corner, with a rounded corner
            lineTo(size.width - topCornerRadiusPx, 0f)
            quadraticBezierTo(size.width, 0f, size.width, topCornerRadiusPx)

            // Line to the bottom right corner, straight
            lineTo(size.width, size.height)

            // Line to the bottom left corner, straight
            lineTo(0f, size.height)

            // Close the path by connecting back to the start point
            close()
        }
        return Outline.Generic(path)
    }

}