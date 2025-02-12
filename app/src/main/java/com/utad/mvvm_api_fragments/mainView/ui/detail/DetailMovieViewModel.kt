package com.utad.mvvm_api_fragments.mainView.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.mvvm_api_fragments.mainView.model.repository.MovieRepository
import com.utad.mvvm_api_fragments.mainView.model.domain.Movie
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.model.domain.toRoom
import com.utad.mvvm_api_fragments.mainView.model.repository.repositoryRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel@Inject constructor(
    private val repository: MovieRepository,
    private val roomRepository: repositoryRoom
): ViewModel() {

    private var _uiState = MutableStateFlow(uiStateDetailMovie())
    val uiState: StateFlow<uiStateDetailMovie> = _uiState


    fun getMovieDetails(movieId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update{ currentState ->
                    currentState.copy(
                        movie = repository.getMovieDetails(movieId),
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun updateGenres(item: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    relatedMovies = repository.getMoviesWithSimilarGenre(item)
                )
            }
        }
    }

    fun bookMarkMovie(movie: SingleMovie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                roomRepository.insertMovie(movie.toRoom())
            }
        }
    }

    fun checkIfbookMarkMovie(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val res = roomRepository.checkMovie(id)
                if (res != null) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            bookMarked = true
                        )
                    }
                }
            }
        }
    }

    fun unbookMarkMovie(movie: SingleMovie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                roomRepository.removeMovie(movie.toRoom())
            }
        }
    }


}

data class uiStateDetailMovie(
    var isLoading: Boolean = true,
    var movie: SingleMovie = SingleMovie(),
    var relatedMovies: List<Movie>? = emptyList(),
    var bookMarked: Boolean = false
)