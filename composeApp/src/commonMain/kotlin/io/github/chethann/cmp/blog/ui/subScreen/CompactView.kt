package io.github.chethann.cmp.blog.ui.subScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.chethann.cmp.blog.ui.model.NavPageType
import io.github.chethann.cmp.blog.ui.model.NavigationItem

@Composable
fun CompactView(
    navItems: List<NavigationItem>,
    selectedPage: NavPageType,
    onSelect: (NavigationItem) -> Unit,
    content: @Composable () -> Unit) {

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEach { item ->
                    NavigationBarItem(
                        selected = selectedPage == item.navPageType,
                        onClick = { onSelect(item) },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}