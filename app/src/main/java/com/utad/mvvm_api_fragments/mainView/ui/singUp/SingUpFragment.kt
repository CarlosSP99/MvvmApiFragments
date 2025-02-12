package com.utad.mvvm_api_fragments.mainView.ui.singUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utad.mvvm_api_fragments.R
import com.utad.mvvm_api_fragments.databinding.FragmentSingUpBinding
import com.utad.mvvm_api_fragments.mainView.model.room.User
import com.utad.mvvm_api_fragments.mainView.ui.mainView.MainViewFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingUpFragment : Fragment() {

    private lateinit var binding: FragmentSingUpBinding
    private val viewModel: SingUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingUpBinding.inflate(inflater, container, false)

        initUI()

        binding.btnSingUp.setOnClickListener {
            createAcc()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun createAcc() {
        val name = binding.tieNombre.text.toString()
        val password = binding.tiePassword.text.toString()
        val rpassword = binding.tiePasswordRepeat.text.toString()
        val email = binding.tieEmail.text.toString()
        val user = User(
            firstName = name,
            password = password,
            email = email
        )
        if (password == rpassword) {
            viewModel.insertUser(user, { navigateToLogin() }, { msg -> displayMessage(msg) })
        } else {
            displayMessage("Las contraseñas no coinciden")
        }
    }

    private fun initUI() {
        checkFields()

    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_opciones_saved -> {
                    findNavController().navigate(MainViewFragmentDirections.actionMainViewFragmentToBookMarkedMoviesFragment())
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun checkFields() {
        val fields = listOf(
            binding.tieEmail,
            binding.tiePassword,
            binding.tiePasswordRepeat,
            binding.tieNombre
        )
        fields.forEach {
            it.addTextChangedListener {
                checkFieldsIsNotEmpty()
            }
        }
    }

    private fun checkFieldsIsNotEmpty() {
        val mail = binding.tieEmail.text.toString()
        val password = binding.tiePassword.text.toString()
        val passwordRepeat = binding.tiePasswordRepeat.text.toString()
        val nombre = binding.tieNombre.text.toString()
        if (mail.isNotEmpty() && password.isNotEmpty() && passwordRepeat.isNotEmpty() && nombre.isNotEmpty()) {
            binding.btnSingUp.isEnabled = true
        } else {
            binding.btnSingUp.isEnabled = false

        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_singUpFragment_to_logInFragment)
    }

    private fun displayMessage(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

}