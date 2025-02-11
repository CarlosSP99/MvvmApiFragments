package com.utad.mvvm_api_fragments.mainView.ui.mainView

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.utad.mvvm_api_fragments.R
import com.utad.mvvm_api_fragments.databinding.FragmentMainViewBinding
import com.utad.mvvm_api_fragments.mainView.domain.rv.MovieAdapter
import com.utad.mvvm_api_fragments.mainView.model.domain.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainViewFragment : Fragment() {

    private lateinit var binding: FragmentMainViewBinding
    private val viewModel: MainViewViewModel by viewModels()
    private lateinit var adapterNowPlaying: MovieAdapter
    private lateinit var adapterPopular: MovieAdapter
    private lateinit var adapterUpcoming: MovieAdapter
    private lateinit var adapterTopRated: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainViewBinding.inflate(inflater, container, false)

        initUI()

        binding.ivMainLogo.setOnClickListener {
            findNavController().navigate(MainViewFragmentDirections.actionMainViewFragmentToLogInFragment())
        }

        return binding.root
    }

    private fun initUI() {
        createRV()
        modifyRV()
        blockBack()
    }

    private fun blockBack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(requireContext(), "No puedes volver al sing up así", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun modifyRV() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                binding.pbLoading.visibility = if (it.isLoading) View.VISIBLE else View.GONE

                adapterNowPlaying.movieList = it.moviesNowPlaying
                adapterTopRated.updateList(it.moviesNowPlaying)

                adapterPopular.movieList = it.moviesPopular
                adapterTopRated.updateList(it.moviesPopular)

                adapterUpcoming.movieList = it.moviesUpcoming
                adapterTopRated.updateList(it.moviesUpcoming)

                adapterTopRated.movieList = it.moviesTopRated
                adapterTopRated.updateList(it.moviesTopRated)
            }
        }
    }

    private fun navigateToDetail(movie: Movie){
        findNavController().navigate(MainViewFragmentDirections.actionMainViewFragmentToDetailMovieFragment(movie.id))
    }

    private fun createRV() {
        adapterNowPlaying= MovieAdapter(movieList = emptyList(), onClickListener = {navigateToDetail(it)})
        binding.rvNowPlaying.adapter = adapterNowPlaying

        adapterPopular= MovieAdapter(movieList = emptyList(), onClickListener = {navigateToDetail(it)})
        binding.rvPopular.adapter = adapterPopular

        adapterUpcoming= MovieAdapter(movieList = emptyList(), onClickListener = {navigateToDetail(it)})
        binding.rvNowComing.adapter = adapterUpcoming

        adapterTopRated= MovieAdapter(movieList = emptyList(), onClickListener = {navigateToDetail(it)})
        binding.rvTopRated.adapter = adapterTopRated

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivMenu = view.findViewById<AppCompatImageView>(R.id.ivMenuLogo)

        ivMenu.setOnClickListener { showPopupMenu(it) }
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

}