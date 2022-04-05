package com.faceme.faceme.model

data class EventsFeed (
    val videoFeed: String? = null,
    val identifiedUsers: List<String> = emptyList()
) {

}
