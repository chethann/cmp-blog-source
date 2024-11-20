package io.github.chethann.cmp.blog.ui.subScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import cmpblog.composeapp.generated.resources.Res
import cmpblog.composeapp.generated.resources.tags
import coil3.compose.AsyncImage
import io.github.chethann.cmp.blog.ui.model.NavPageType
import io.github.chethann.cmp.blog.ui.model.NavigationItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun ExpandedView(
    navItems: List<NavigationItem>,
    selectedPage: NavPageType,
    onSelect: (NavigationItem) -> Unit,
    onDarkThemeToggle: () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {

            Column(modifier = Modifier
                .widthIn(min = 200.dp)
                //.verticalScroll(rememberScrollState())
                .fillMaxWidth(0.175f)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                //.fillMaxHeight()
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = "https://avatars.githubusercontent.com/u/2727154?v=4",
                    contentDescription = "avatar",
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(100))
                        .border(1.dp, MaterialTheme.colorScheme.surfaceDim, RoundedCornerShape(100)),
                    onError = {
                        println(it.result.throwable.message)
                    }
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Chethan N", style = MaterialTheme.typography.headlineSmall )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("A blog on compose built using compose!", fontStyle = FontStyle.Italic, color = MaterialTheme.colorScheme.outline)
                }

                NavigationRail(
                    modifier = Modifier.padding(16.dp).weight(1f),
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    navItems.forEach { item ->
                        NavigationDrawerItem(
                            selected = selectedPage == item.navPageType,
                            onClick = { onSelect(item) },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }

                Text("Toggle Mode", modifier = Modifier.padding(16.dp).clickable { onDarkThemeToggle() })
            }

            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                content()
            }
        }
    }
}