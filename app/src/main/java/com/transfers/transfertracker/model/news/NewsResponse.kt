package com.transfers.transfertracker.model.news

data class NewsResponse(
    val nextPage: Int?,
    val results: List<News>?,
    val status: String?,
    val totalResults: Int?
)