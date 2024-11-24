package com.demo.moviedemo.core.paginator

import com.demo.moviedemo.core.utils.LoadingState
import com.demo.moviedemo.data.model.MovieIDResponse
import com.demo.moviedemo.data.utils.ApiResult
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Page<T>(
    @SerializedName("results")
    val results: List<T>,
)

class DataPaginator<Item>(
    private val initialKey: Long = 1,
    private inline val onLoadUpdate: suspend (LoadingState) -> Unit = {},
    private inline val onRefreshStatus: suspend (Boolean) -> Unit = {},
    private inline val onRequest: suspend (nextKey: Long) -> ApiResult<Item>,
    private inline val onSuccess: suspend (page: Item, newKey: Long) -> Unit,
    private inline val onError: suspend (errorType: String) -> Unit,
): Paginator<Item> {
    private var currentKey = initialKey
    private var isMakingRequest = false
    private var isReachedEnd = false

    override suspend fun loadNextItems() {
        if(isMakingRequest || isReachedEnd) return

        isMakingRequest = true
        onLoadUpdate(LoadingState.LOADING)

        when(val res = onRequest(currentKey)){
            is ApiResult.Success -> {
                val result = res.value as MovieIDResponse
                isReachedEnd = result.results.isEmpty()
                currentKey += 1
                onSuccess(res.value, currentKey)
                onLoadUpdate(LoadingState.LOADED)
            }
            is ApiResult.Error -> {
                onError("Error occurred")
                onLoadUpdate(LoadingState.ERROR)
            }
            else -> onLoadUpdate(LoadingState.IDLE)
        }
        isMakingRequest = false
        onRefreshStatus(false)
    }

    override suspend fun refresh() {
        reset()
        onRefreshStatus(true)
        loadNextItems()
    }

    override fun reset() {
        currentKey = initialKey
        isReachedEnd = false
    }

    override fun isFirstPage(): Boolean {
        return (currentKey - 1) == 1L
    }
}
