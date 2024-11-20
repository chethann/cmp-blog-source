package io.github.chethann.cmp.blog.ui.articles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.chethann.cmp.blog.utils.AnnotationStringUtils.addBold
import io.github.chethann.cmp.blog.utils.AnnotationStringUtils.addBoldAndItalic
import io.github.chethann.cmp.blog.utils.AnnotationStringUtils.addLink

@Composable
fun CheckIfAppIsCompose() {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {

        // Header Text
        Text(
            text = "How to check if a screen using Compose UI on Android\n",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = buildAnnotatedString {
                append("As I was scrolling throw my twitter feed, I saw this ")
                addLink("tweet", "https://x.com/VasiliyZukanov/status/1856648593672740928")
                append(" from ")
                addLink("Vasiliy Zukanov", "https://x.com/VasiliyZukanov")
                append(". He was curious if Wolt’s Android app was build using Jetpack Compose.")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            model = "https://chethann.github.io/assets/img/2024-11-14-check-if-compose/tweet.jpg",
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "I shared a trick I use to check if any screen is build using Compose or native views. This involves enabling the developer option and turn on “Show layout bounds” option and observer the bounds closly. This trick can come in handly if you want to know if any screen in any app is built using Compose or it uses native views.")

        // One more tweet image here

        Text(text = "Just sharing the trick here, it works as on November 2024. Not sure if it will be true for future versions of Compose.")

        Text(
            text = buildAnnotatedString {
                append("Basically, when we enable ")
                addBoldAndItalic("“Show layout bounds”")
                append(" option from developer setting, it shows bounds a little differently for Native Views and compose ones.")
            }
        )

        Text(
            text = buildAnnotatedString {
                append("Layout bounds of a screen built using Compose look like below. You can see that there is ")
                addBold("no thick blue corners ")
                append("in the bounds and the")
                addBold("borders around text and image are blue")
                append(" in color.")
            }
        )

        AsyncImage(
            model = "https://chethann.github.io/assets/img/2024-11-14-check-if-compose/pincode.jpg",
            contentDescription = "pincode image",
            modifier = Modifier
                .fillMaxWidth()
        )

        Text("Layout bounds of a screen built using navtive views look like below. You can see that there is thick blue corners in the bounds and the borders around text and image are red in color.")

        AsyncImage(
            model = "https://chethann.github.io/assets/img/2024-11-14-check-if-compose/phonepe.jpg",
            contentDescription = "phonepe image",
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            buildAnnotatedString {
                addBold("Bonus: ")
                append("If you don’t see any borders around the UI elements, then the screen is most likely a web view. Ex: Amazon")
            }
        )

        AsyncImage(
            model = "https://chethann.github.io/assets/img/2024-11-14-check-if-compose/amazon.jpg",
            contentDescription = "amazon image",
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            buildAnnotatedString {
                append("As react native uses the native views, the layout bounds in case of react native would be similar to that of native views.")
            }
        )

        Text(
            buildAnnotatedString {
                append("For flutter apps, there is no layout bounds by default (It can be explictly enabled by developer for testing though) similar to that of webview.")
            }
        )
    }
}