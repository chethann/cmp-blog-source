package io.github.chethann.cmp.blog.ui.screen.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.chethann.cmp.blog.ui.dataProvider.ArticlePreviewListProvider

@Composable
fun TagsPage(navController: NavHostController = rememberNavController(), onArticleClick: (String) -> Unit) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    var isInDetailsView by remember { mutableStateOf(false) }

    var selectedTag by remember { mutableStateOf<String?>(null) }

    // Trigger side effects when the destination changes
    LaunchedEffect(navBackStackEntry.value?.destination, navController.currentBackStackEntry?.arguments?.getString("tag")) {
        isInDetailsView = navController.currentBackStackEntry?.destination?.route == "tagsDetails/{tag}"
        selectedTag = navController.currentBackStackEntry?.arguments?.getString("tag")
    }


    Column(modifier = Modifier.padding(16.dp)) {

        TagsPagePillsView(
            inDetailView = isInDetailsView,
            title = selectedTag,
            onTagsClick = {
                navController.navigate("tagsList") {
                    popUpTo("tagsList") { inclusive = true }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Tags",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))

        NavHost(navController = navController, startDestination = "tagsList") {
            composable("tagsList") {
                TagsListView {
                    navController.navigate("tagsDetails/$it")
                }
            }

            composable("tagsDetails/{tag}") {
                val tag = navController.currentBackStackEntry?.arguments?.getString("tag")
                if (tag != null) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        TagArticlesListView(tag, onArticleClick = onArticleClick)
                    }
                }
            }
        }
    }
}

@Composable
fun TagsPagePillsView(inDetailView: Boolean, title: String?, onTagsClick: () -> Unit) {
    if (inDetailView) {

        Row(modifier = Modifier) {
            Text(
                "Tags",
                modifier = Modifier
                    .clickable { onTagsClick() },
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(">", color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.width(8.dp))
            title?.let { Text(it, color = MaterialTheme.colorScheme.outline) }
        }

    } else {
        Box(modifier = Modifier) {
            Text("Tags", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun TagsListView(onTagClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
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

@Composable
fun TagArticlesListView(tag: String, onArticleClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 16.dp).verticalScroll(rememberScrollState())) {
        ArticlePreviewListProvider.articles.filter { it.tags.contains(tag) }.forEach { article ->
            Text(
                article.title,
                modifier = Modifier.clickable {
                    onArticleClick(article.id)
                },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}