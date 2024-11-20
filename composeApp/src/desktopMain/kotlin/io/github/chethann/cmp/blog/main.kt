package io.github.chethann.cmp.blog

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CMPBlog",
    ) {
        App()
    }
}