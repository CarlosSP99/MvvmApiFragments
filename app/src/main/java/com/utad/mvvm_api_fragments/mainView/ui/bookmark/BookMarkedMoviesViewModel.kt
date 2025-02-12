package com.utad.mvvm_api_fragments.mainView.ui.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.model.domain.toRoom
import com.utad.mvvm_api_fragments.mainView.model.repository.repositoryRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkedMoviesViewModel@Inject constructor(private val room: repositoryRoom) :ViewModel(){

    private val _uiState = MutableStateFlow(uiStateMovieBookMark())
    val uiState: StateFlow<uiStateMovieBookMark> get() = _uiState

   init {
       getMoviesBookMarked()
       Log.d("dasdadsadadsa", _uiState.value.movieBookMarkeds.toString())
   }

    fun getMoviesBookMarked(){
        viewModelScope.launch {
                _uiState.update { currentState ->
                    currentState.copy(
                        movieBookMarkeds = room.getMoviesBookMarked(),
                        isLoading = true
                    )
            }
        }
    }

    fun removeMovie(movie: SingleMovie) {
        viewModelScope.launch {
            room.removeMovie(movie.toRoom())
            getMoviesBookMarked()
        }
    }

}

data class uiStateMovieBookMark(
    var movieBookMarkeds: List<SingleMovie> = emptyList(),
    var isLoading: Boolean = false
)