package dev.maxsiomin.prodhse.core.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

fun linkString(input: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(input)
        }
    }
}
