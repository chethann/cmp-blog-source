package io.github.chethann.cmp.blog.ui.model.article

data class ArticlePreviewData(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val tags: List<String>
)