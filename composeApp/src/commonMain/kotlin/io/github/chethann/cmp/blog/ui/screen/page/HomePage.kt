package io.github.chethann.cmp.blog.ui.screen.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import io.github.chethann.cmp.blog.ui.articles.CheckIfAppIsCompose
import io.github.chethann.cmp.blog.ui.articles.KtorNetworkCallMonitorArticle
import io.github.chethann.cmp.blog.ui.articles.MultiSectionCollapsable
import io.github.chethann.cmp.blog.ui.articles.ViewModelsComposeNavigation
import io.github.chethann.cmp.blog.ui.component.ArticlePreview
import io.github.chethann.cmp.blog.ui.dataProvider.ArticlePreviewListProvider
import io.github.chethann.cmp.blog.ui.model.article.ArticlePreviewData

@Composable
fun HomePage(navController: NavHostController = rememberNavController()) {

    // Add Header with current page level and search here
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    var isInDetailsView by remember { mutableStateOf(false) }
    var selectedArticle by remember { mutableStateOf<ArticlePreviewData?>(null) }

    // Trigger side effects when the destination changes
    LaunchedEffect(navBackStackEntry.value?.destination, navController.currentBackStackEntry?.arguments?.getString("id")) {
        isInDetailsView = navController.currentBackStackEntry?.destination?.route == "articleDetails/{id}"
        val id = navController.currentBackStackEntry?.arguments?.getString("id")
        selectedArticle = ArticlePreviewListProvider.getArticleById(id)
    }

    Column {
        HomePagePillsView(
            inDetailView = isInDetailsView,
            title = selectedArticle?.title,
            onHomeClick = {
                navController.navigate("articleList") {
                    popUpTo("articleList") { inclusive = true }
                }
            }
        )

        HomePageArticleView(navController)
    }
}

@Composable
fun HomePageArticleView(navController: NavHostController) {
    val articles = remember { ArticlePreviewListProvider.articles }
    NavHost(navController = navController, startDestination = "articleList") {
        composable("articleList") {
            ArticlePreviewList(
                articlePreviewDataList = articles,
                onClick = {
                    navController.navigate("articleDetails/${it.id}")
                }
            )
        }

        composable("articleDetails/{id}") {
            val id = navController.currentBackStackEntry?.arguments?.getString("id")
            if (id != null) {
                Box(modifier = Modifier.padding(16.dp)) {
                    ComposableForArticleId(id)
                }
            }
        }
    }
}

@Composable
fun ArticlePreviewList(articlePreviewDataList: List<ArticlePreviewData>, onClick: (ArticlePreviewData) -> Unit) {
    LazyColumn {
        items(articlePreviewDataList) { articlePreviewData ->
            ArticlePreview(articlePreviewData, onClick)
        }
    }
}

@Composable
fun HomePagePillsView(inDetailView: Boolean, title: String?, onHomeClick: () -> Unit) {
    if (inDetailView) {

            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                Text(
                    "Home",
                    modifier = Modifier
                        .clickable { onHomeClick() },
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(">", color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.width(8.dp))
                title?.let { Text(it, color = MaterialTheme.colorScheme.outline) }
            }

    } else {
        Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Text("Home", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun ComposableForArticleId(id: String) {
    when (id) {
        "40c03d9a-741f-45df-8649-b3f858b51930" -> { KtorNetworkCallMonitorArticle() }
        "7a382615-5f30-4957-bc58-faebb48a3b5b" -> { CheckIfAppIsCompose() }
        "3fedaf09-5434-458d-b024-df0b78240f94" -> { MultiSectionCollapsable() }
        "94a67a1f-4393-4460-9b53-23f9e347843a" -> { ViewModelsComposeNavigation() }
    }
}
