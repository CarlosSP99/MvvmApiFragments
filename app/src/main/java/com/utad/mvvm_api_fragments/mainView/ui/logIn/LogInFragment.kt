package com.utad.mvvm_api_fragments.mainView.ui.logIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utad.mvvm_api_fragments.R
import com.utad.mvvm_api_fragments.databinding.FragmentLogInBinding
import com.utad.mvvm_api_fragments.databinding.FragmentSingUpBinding
import com.utad.mvvm_api_fragments.mainView.ui.mainView.MainViewFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val viewModel: LogInViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)

        initUI()

        binding.btnLogIn.setOnClickListener {
            logIntoTheApp()
        }
        binding.btnSingUp.setOnClickListener {
            navigateToSingUp()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initUI() {
        checkFields()
        blockBack()
    }

    private fun blockBack() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            })
    }


    private fun checkFields() {
        val fields = listOf(
            binding.tieUser,
            binding.tiePassword
        )
        fields.forEach {
            it.addTextChangedListener {
                checkFieldsIsNotEmpty()
            }
        }
    }

    private fun checkFieldsIsNotEmpty() {
        val user = binding.tieUser.text.toString()
        val password = binding.tiePassword.text.toString()
        if (user.isNotEmpty() && password.isNotEmpty()) {
            binding.btnLogIn.isEnabled = true
        } else {
            binding.btnLogIn.isEnabled = false
        }
    }

    private fun logIntoTheApp() {
        val user = binding.tieUser.text.toString()
        val password = binding.tiePassword.text.toString()
        viewModel.login(
            user,
            password,
            { navigateToMainView() },
            { displayMessage(it) })
    }

    private fun displayMessage(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToMainView() {
        findNavController().navigate(R.id.action_logInFragment_to_mainViewFragment)
    }

    private fun navigateToSingUp() {
        findNavController().navigate(R.id.action_logInFragment_to_singUpFragment)
    }


}