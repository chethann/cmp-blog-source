package io.github.chethann.cmp.blog.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import io.github.chethann.cmp.blog.ui.component.RightPaneView
import io.github.chethann.cmp.blog.ui.model.NavPageType
import io.github.chethann.cmp.blog.ui.model.NavigationItem
import io.github.chethann.cmp.blog.ui.subScreen.CompactView
import io.github.chethann.cmp.blog.ui.subScreen.ExpandedView
import io.github.chethann.cmp.blog.ui.subScreen.MediumView

/**
 * The blog view would contain 3 sections.
 * 1) Navigation Rail / Drawer / Bottom Bar based on screen size
 * 2) Main Content View
 * 3) A supporting view - only visible in large screens
 */
@Composable
fun BlogScreenScaffold(
    windowSizeClass: WindowSizeClass,
    home: @Composable () -> Unit,
    tags: @Composable () -> Unit,
    about: @Composable () -> Unit,
    archives: @Composable () -> Unit,
    onArticleClick: (String) -> Unit,
    onTagClick: (String) -> Unit,
    onDarkThemeToggle: () -> Unit,
    selectedPage: NavPageType,
    onSelectedPageUpdateRequest: (NavPageType) -> Unit
) {
    val navItems = remember { getNavigationItems() }

    val content = when(selectedPage) {
        NavPageType.HOME -> home
        NavPageType.TAGS -> tags
        NavPageType.ARCHIVES -> archives
        NavPageType.ABOUT -> about
    }

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            CompactView(
                navItems = navItems,
                selectedPage = selectedPage,
                onSelect = { onSelectedPageUpdateRequest(it.navPageType) },
                content = content
            )
        }

        WindowWidthSizeClass.MEDIUM -> {
            MediumView(

                navItems = navItems,
                selectedPage = selectedPage,
                onSelect = { onSelectedPageUpdateRequest(it.navPageType) },
                content = content
            )
        }

        else -> {

            ExpandedView(
                navItems = navItems,
                selectedPage = selectedPage,
                onSelect = { onSelectedPageUpdateRequest(it.navPageType) },
                onDarkThemeToggle,
                content = {
                    BoxWithConstraints {
                        if (maxWidth > 1200.dp) {
                            Row(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(modifier = Modifier.fillMaxWidth(0.75f)) {
                                    content()
                                }
                                Box {
                                    RightPaneView(
                                        onArticleClick = {
                                            onSelectedPageUpdateRequest(NavPageType.HOME)
                                            onArticleClick(it)
                                        },
                                        onTagClick = {
                                            onSelectedPageUpdateRequest(NavPageType.TAGS)
                                            onTagClick(it)
                                        }
                                    )
                                }
                            }
                        } else {
                            content()
                        }
                    }
                }
            )
        }
    }

}

fun getNavigationItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(NavPageType.HOME, "HOME", Icons.Default.Home),
        NavigationItem(NavPageType.TAGS,"TAGS", Icons.Default.Menu),
        //NavigationItem(NavPageType.ARCHIVES,"ARCHIVES", Icons.AutoMirrored.Filled.List),
        NavigationItem(NavPageType.ABOUT,"ABOUT", Icons.Default.Info)
    )
}

