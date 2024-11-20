package io.github.chethann.cmp.blog.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BulletPoints(items: List<String>) {
    Column(modifier = Modifier.padding(16.dp)) {
        items.forEach { item ->
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                // Bullet symbol
                Text(
                    text = "•", // Unicode bullet character
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
                // Text for the bullet point
                Text(
                    text = item,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}
