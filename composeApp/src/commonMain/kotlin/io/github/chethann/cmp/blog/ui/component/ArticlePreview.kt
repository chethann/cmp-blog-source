package io.github.chethann.cmp.blog.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.chethann.cmp.blog.ui.model.article.ArticlePreviewData

@Composable
fun ArticlePreview(articlePreviewData: ArticlePreviewData, onClick: (ArticlePreviewData) -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onClick(articlePreviewData) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Header Text
            Text(
                text = articlePreviewData.title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Description
            Text(
                text = articlePreviewData.description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            /*BoxWithConstraints {
                AdaptiveExampleOne(
                    AdaptiveDisplayDataOne(images = images)
                )
            }*/
        }
    }
}