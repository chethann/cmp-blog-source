package io.github.chethann.cmp.blog.ui.dataProvider

import io.github.chethann.cmp.blog.ui.model.article.ArticlePreviewData
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object ArticlePreviewListProvider {
    @OptIn(ExperimentalUuidApi::class)
    fun provideDummyArticlePreviewList(): List<ArticlePreviewData> {
        return (1..20).map {
            ArticlePreviewData(
                id = Uuid.random().toString(),
                title = "This is my blog post title",
                date = "2024 Nov 16",
                description = "This is my blog post description. This is my blog post description.  This is my blog post description.  This is my blog post description.  This is my blog post description. ",
                tags = listOf("compose", "cmp", "kmp")
            )
        }
    }

    val articles = listOf(
        ArticlePreviewData(
            id = "7a382615-5f30-4957-bc58-faebb48a3b5b",
            title = "How to check if a screen using Compose UI on Android",
            date = "Nov 14, 2024",
            description = "As I was scrolling throw my twitter feed, I saw this tweet from Vasiliy Zukanov. He was curious if Wolt’s Android app was build using Jetpack Compose.",
            tags = listOf("android", "ui", "compose")
        ),

        ArticlePreviewData(
            id = "40c03d9a-741f-45df-8649-b3f858b51930",
            title = "Monitoring and visualising Ktor client network calls",
            date = "Nov 11, 2024",
            description = "When we try any new technology one of the key factors in convincing the team to adopt it is to have a good tooling and dev experience. The dev experience needs to be as close as possible to the current experience that if not better.",
            tags = listOf("kmp", "cmp", "ktor", "kotlin multiplatform", "compose", "room")
        ),

        ArticlePreviewData(
            id = "3fedaf09-5434-458d-b024-df0b78240f94",
            title = "Multi-Section collapsable List in Jetpack Compose",
            date = "Nov 9, 2024",
            description = "Our application is built using Jetpack Compose and this article explains how we can build a performant multi-section list. LazyList has a limitation that it can’t contain other lists of undefined height which can scroll in the same direction.",
            tags = listOf("android", "ui", "compose", "performance", "lazy list")
        ),

        ArticlePreviewData(
            id = "94a67a1f-4393-4460-9b53-23f9e347843a",
            title = "ViewModels with hilt and compose navigation",
            date = "Nov 14, 2024",
            description = "As per the official documentation, hilt is the recommended solution for dependency injection in Android apps, and works seamlessly with Compose. Hilt also integrates with the Navigation Compose library and gives a developer-friendly API to create ViewModels in Compose projects. One can also check Compose Hilt and Navigation",
            tags = listOf("android", "viewmodel", "compose", "hilt", "navigation")
        )
    )

    fun getArticleById(id: String?): ArticlePreviewData? {
        return articles.find { it.id == id }
    }
}