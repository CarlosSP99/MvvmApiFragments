package com.utad.mvvm_api_fragments.mainView.ui.mainView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.mvvm_api_fragments.mainView.model.repository.MovieRepository
import com.utad.mvvm_api_fragments.mainView.model.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewViewModel@Inject constructor(private val repository: MovieRepository): ViewModel() {

    private var _uiState = MutableStateFlow(uiStateMainView())
    val uiState: StateFlow<uiStateMainView> = _uiState

    init{
        getNowPlayingMovies(1)
    }

     fun getNowPlayingMovies(page: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _uiState.update { currentState ->
                    currentState.copy(
                        moviesNowPlaying = repository.getNowPlayingMovies(page),
                        moviesPopular = repository.getPopularMovies(page),
                        moviesTopRated = repository.getTopRatedMovies(page),
                        moviesUpcoming = repository.getUpcomingMovies(page),
                        isLoading = false
                    )
                }
            }
        }
    }



}

data class uiStateMainView(
    var isLoading: Boolean = true,
    var moviesNowPlaying: List<Movie> = emptyList(),
    var moviesPopular: List<Movie> = emptyList(),
    var moviesTopRated: List<Movie> = emptyList(),
    var moviesUpcoming: List<Movie> = emptyList()

)