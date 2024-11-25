package com.demo.moviedemo.features.movieDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.moviedemo.core.utils.LoadingState
import com.demo.moviedemo.data.model.MovieDetail
import com.demo.moviedemo.data.repository.TMDBRepository
import com.demo.moviedemo.data.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class MovieDetailState(
    val movie: MovieDetail? = null,
    val loadingState: LoadingState = LoadingState.IDLE
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tmdbRepository: TMDBRepository
) : ViewModel() {

    private val id = savedStateHandle.get<Long>("id")

    private val _state = MutableStateFlow(MovieDetailState())
    val state = _state.asStateFlow()

    private var coroutineScope = viewModelScope

    init {
        id?.let { getMovieDetail(it) }
    }

    fun getMovieDetail(
        id: Long,
    ) {
        coroutineScope.launch {
            _state.update {
                _state.value.copy(
                    loadingState = LoadingState.LOADING,
                )
            }
            when(val res = tmdbRepository.getMovieDetail(id)) {
                is ApiResult.Success -> {
                    _state.update {
                        _state.value.copy(
                            loadingState = LoadingState.LOADED,
                            movie = res.value
                        )
                    }

                    Timber.d("movie Detail = ${res.value}")
                }
                is ApiResult.Error -> {
                    Timber.e(res.toString())
                    _state.update {
                        _state.value.copy(
                            loadingState = LoadingState.ERROR,
                        )
                    }
                }
                else -> {
                    Timber.i("Api call ignored")
                    _state.update {
                        _state.value.copy(
                            loadingState = LoadingState.IDLE,
                        )
                    }
                }
            }
        }
    }

    fun setCoroutineScope(scope: CoroutineScope){
        this.coroutineScope = scope
    }
}