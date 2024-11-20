package io.github.chethann.cmp.blog.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeSnippetView(code: String) {
    SelectionContainer {
        Text(
            text = code,
            style = TextStyle(
                fontFamily = FontFamily.Monospace, // Monospace font for code
                fontSize = 14.sp, // Font size
                lineHeight = 20.sp // Line height for better readability
            ),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    RoundedCornerShape(4.dp)
                ) // Border color
                .background(MaterialTheme.colorScheme.surface) // Background color
                .padding(16.dp) // Padding inside the border
        )
    }
}