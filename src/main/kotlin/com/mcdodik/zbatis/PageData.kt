package com.mcdodik.zbatis

data class PageData<T>(
    val key: String = "page_data",
    val totalCount: Int?,
    val limit: Int?,
    val offset: Int?,
    val data: List<T>
)
