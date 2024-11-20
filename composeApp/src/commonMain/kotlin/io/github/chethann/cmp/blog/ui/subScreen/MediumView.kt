package io.github.chethann.cmp.blog.ui.subScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.chethann.cmp.blog.ui.model.NavPageType
import io.github.chethann.cmp.blog.ui.model.NavigationItem

@Composable
fun MediumView(
    navItems: List<NavigationItem>,
    selectedPage: NavPageType,
    onSelect: (NavigationItem) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(modifier = Modifier
                .width(84.dp)
                .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {

                AsyncImage(
                    model = "https://avatars.githubusercontent.com/u/2727154?v=4",
                    contentDescription = "avatar",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(1000.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(1000.dp)),
                    onError = {
                        println(it.result.throwable.message)
                    }
                )

                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    navItems.forEach { item ->
                        NavigationRailItem(
                            selected = selectedPage == item.navPageType,
                            onClick = { onSelect(item) },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                content()
            }
        }
    }
}