package id.go.jabarprov.dbmpr.common.domain.entity

import java.util.*

data class News(
    val id: Int,
    val title: String,
    val content: String,
    val imageUrl: String? = null,
    val shortDescription: String? = null,
    val slug: String,
    val publishedAt: Calendar? = null,
    val publishedBy: String? = null
)
