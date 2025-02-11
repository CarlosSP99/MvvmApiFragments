package com.utad.mvvm_api_fragments.mainView.ui.singUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.mvvm_api_fragments.mainView.model.room.User
import com.utad.mvvm_api_fragments.mainView.room.repositoryRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel@Inject constructor(private val repositoryRoom: repositoryRoom): ViewModel() {


    fun insertUser(
        user: User,
        navigateToLoginView: () -> Unit,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            // compruebo el email y si no está le creo
            val emailCheck = repositoryRoom.checkEmail(user.email)
            if (emailCheck == null) {
                repositoryRoom.registerUser(user)
                // vuelvo a comprobar el email, si
                // está  navego hacia el login
                val accountCreated = repositoryRoom.checkEmail(user.email)
                if (accountCreated != null) {
                    navigateToLoginView()
                    onResult(ResultRegister.SUCCESS.state)
                }
            } else {
                onResult(ResultRegister.ERROR.state)
            }
        }
    }

}


enum class ResultRegister(val state: String){
    SUCCESS("Cuenta creada con éxito"),
    ERROR("Ha habido un error")
}