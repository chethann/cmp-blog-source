package io.github.chethann.cmp.blog.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

object AnnotationStringUtils {
    fun AnnotatedString.Builder.addLink(text: String, url: String) {
        pushLink(LinkAnnotation.Url(url))
        withStyle(style = SpanStyle(
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        )
        ) {
            append(text)
        }
        pop()
    }

    fun AnnotatedString.Builder.addBold(text: String) {
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Bold
        )
        ) {
            append(text)
        }
    }

    fun AnnotatedString.Builder.addItalic(text: String) {
        withStyle(style = SpanStyle(
            fontStyle = FontStyle.Italic
        )
        ) {
            append(text)
        }
    }

    fun AnnotatedString.Builder.addBoldAndItalic(text: String) {
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )
        ) {
            append(text)
        }
    }
}