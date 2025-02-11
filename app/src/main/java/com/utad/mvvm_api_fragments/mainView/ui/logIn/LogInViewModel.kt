package com.utad.mvvm_api_fragments.mainView.ui.logIn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.mvvm_api_fragments.mainView.room.repositoryRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel@Inject constructor(private val repositoryRoom: repositoryRoom): ViewModel() {

    fun login(user: String,
              password: String,
              navigateToMainView: () -> Unit,
              onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = repositoryRoom.login(user, password)
            if (result!=null){
                navigateToMainView()
                onResult("Bienvenido")
            } else {
                onResult("Comprueba las credenciales")
            }
            }
        }
    }
