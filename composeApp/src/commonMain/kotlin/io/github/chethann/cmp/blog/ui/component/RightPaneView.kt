package io.github.chethann.cmp.blog.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.chethann.cmp.blog.ui.dataProvider.ArticlePreviewListProvider

@Composable
fun RightPaneView(
    onArticleClick: (String) -> Unit,
    onTagClick: (String) -> Unit
) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(16.dp)) {

        Text(
            "Recent Articles:",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Column(modifier = Modifier.padding(start = 8.dp)) {
            ArticlePreviewListProvider.articles.take(10).forEach { article ->
                Text(
                    article.title,
                    modifier = Modifier.clickable {
                        onArticleClick(article.id)
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.outline,
                )

                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        Text(
            "Trending tags:",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(4.dp))

        Column (modifier = Modifier.padding(start = 8.dp)) {
            ArticlePreviewListProvider.articles.map { it.tags }.flatten().toSet().forEach { tag ->
                Text(
                    tag,
                    modifier = Modifier.clickable {
                        onTagClick(tag)
                    },
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}