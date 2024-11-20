package io.github.chethann.cmp.blog.ui.screen.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import io.github.chethann.cmp.blog.utils.AnnotationStringUtils.addLink

@Composable
fun AboutPage() {
    Column(modifier = Modifier.padding(16.dp)) {

        Box(modifier = Modifier) {
            Text("Tags", color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("About",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            buildAnnotatedString {
                append("A blog about Compose multiplatform written using compose multiplatform! Follow the author on ")
                addLink("twitter", "https://twitter.com/ChethanDev")
                append(" and ")
                addLink("github", "https://github.com/chethann")
                append(" for more updates.")
            }
        )
    }
}