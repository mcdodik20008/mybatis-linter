package com.mcdodik.zbatis

data class PageData<T>(
    val key: String = "page_data",
    val totalCount: Int?,
    val limit: Int?,
    val offset: Int?,
    val data: List<T>
) {
    companion object {
        fun <T> getEmptyPageData(limit: Int?, offset: Int?): PageData<T> {
            val totalCount = offset?.let { if (offset > 0) null else 0 }
            return PageData(totalCount = totalCount, limit = limit, offset = offset, data = emptyList())
        }
    }
}
