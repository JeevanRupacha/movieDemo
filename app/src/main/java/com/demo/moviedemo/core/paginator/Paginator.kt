package com.demo.moviedemo.core.paginator

interface Paginator<Item> {
    suspend fun loadNextItems()
    suspend fun refresh()
    fun reset()
    fun isFirstPage(): Boolean
}