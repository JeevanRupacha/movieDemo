package com.demo.moviedemo.features.moveList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.moviedemo.core.paginator.DataPaginator
import com.demo.moviedemo.core.utils.LoadingState
import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.data.repository.TMDBRepository
import com.demo.moviedemo.data.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class MovieListState(
    val loadingState: LoadingState = LoadingState.IDLE,
    val movies: List<MovieDetail> = emptyList()
)

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val tmdbRepository: TMDBRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListState())
    val state = _state.asStateFlow()

    private val _pageLoading = MutableStateFlow(LoadingState.IDLE)
    private val _movieDetailLoading = MutableStateFlow(LoadingState.IDLE)

    val isLoading = combine(_pageLoading, _movieDetailLoading){ s1, s2 ->
        s1 == LoadingState.LOADING || s2 == LoadingState.LOADING
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    private val _moviePaginator = DataPaginator(
        onLoadUpdate = { _pageLoading.emit(it) },
        onError = {
            Timber.d(it.toString())
        },
        onRequest = { nextKey ->
            tmdbRepository.getMovieIDList(
                page = nextKey.toInt()
            )
        },
        onSuccess = { page, newKey ->
            val movies = page.results
            movies.forEach {
                if(!it.adult) getMovieDetail(it.id)
            }
        }
    )

    init {
        viewModelScope.launch {
            _moviePaginator.loadNextItems()
        }
    }

    fun getMovieDetail(
        id: Long,
        scope: CoroutineScope = viewModelScope
    ) {
        scope.launch {
            _movieDetailLoading.emit(LoadingState.LOADING)
            when(val res = tmdbRepository.getMovieDetail(id)) {
                is ApiResult.Success -> {
                    val newMovies = _state.value.movies.toMutableList()
                    newMovies.add(res.value)

                    _state.update {
                        _state.value.copy(
                            loadingState = LoadingState.LOADED,
                            movies = newMovies
                        )
                    }
                    _movieDetailLoading.emit(LoadingState.LOADED)
                }
                is ApiResult.Error -> {
                    Timber.e(res.toString())
                    _movieDetailLoading.emit(LoadingState.ERROR)
                }
                else -> {
                    Timber.i("Api call ignored")
                    _movieDetailLoading.emit(LoadingState.IDLE)
                }
            }
        }
    }

    fun loadMoreMovies( scope: CoroutineScope = viewModelScope) {
        scope.launch {
            _moviePaginator.loadNextItems()
        }
    }
}