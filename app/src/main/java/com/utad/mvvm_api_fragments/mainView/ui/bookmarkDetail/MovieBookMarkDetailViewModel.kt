package com.utad.mvvm_api_fragments.mainView.ui.bookmarkDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.mvvm_api_fragments.mainView.model.domain.Movie
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.model.room.toDomain
import com.utad.mvvm_api_fragments.mainView.room.repositoryRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieBookMarkDetailViewModel@Inject constructor(private val room: repositoryRoom): ViewModel() {

    private var _uiState = MutableStateFlow(uiStateDetailMovie())
    val uiState: StateFlow<uiStateDetailMovie> = _uiState.asStateFlow()



    fun getDatabyId(movieId: Int){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    movie = room.getMovieById(movieId).toDomain(),
                    isLoading = false)
            }
        }
    }


}


data class uiStateDetailMovie(
    var isLoading: Boolean = true,
    var movie: SingleMovie = SingleMovie(),

)