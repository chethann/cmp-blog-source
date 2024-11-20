package io.github.chethann.cmp.blog

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.chethann.cmp.blog.theme.AppTheme
import io.github.chethann.cmp.blog.ui.model.NavPageType
import io.github.chethann.cmp.blog.ui.screen.BlogScreenScaffold
import io.github.chethann.cmp.blog.ui.screen.page.AboutPage
import io.github.chethann.cmp.blog.ui.screen.page.ArchivesPage
import io.github.chethann.cmp.blog.ui.screen.page.HomePage
import io.github.chethann.cmp.blog.ui.screen.page.TagsPage

@Composable
fun App() {
    val isSystemInDarkMode = isSystemInDarkTheme() || true
    var isDarkTheme by remember { mutableStateOf(isSystemInDarkMode) }
    AppTheme(
        useDarkTheme = isDarkTheme
    ) {
        AppScreen(
            onDarkThemeToggle = { isDarkTheme = !isDarkTheme }
        )
    }
}

@Composable
fun AppScreen(onDarkThemeToggle: () -> Unit) {
    val homePageNavController = rememberNavController()
    val tagsPageNavController = rememberNavController()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    var selectedPage by remember { mutableStateOf(NavPageType.HOME) }

    BlogScreenScaffold(
        windowSizeClass = windowSizeClass,
        home = { HomePage(homePageNavController) },
        tags = { TagsPage(
            tagsPageNavController,
            onArticleClick = {
                selectedPage = NavPageType.HOME
                homePageNavController.navigate("articleDetails/${it}")
            }
        ) },
        archives = { ArchivesPage() },
        about = { AboutPage() },
        onArticleClick = {
            homePageNavController.navigate("articleDetails/${it}")
        },
        onTagClick = {
            tagsPageNavController.navigate("tagsDetails/${it}")
        },
        onSelectedPageUpdateRequest = {
            selectedPage = it
        },
        selectedPage = selectedPage,
        onDarkThemeToggle = onDarkThemeToggle
    )
}