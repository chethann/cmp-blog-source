package io.github.chethann.cmp.blog

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform