package com.utad.mvvm_api_fragments.mainView.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.utad.mvvm_api_fragments.databinding.FragmentBookMarkedMoviesBinding
import com.utad.mvvm_api_fragments.mainView.ui.adapter.rvBookMarkedMovies.MovieBookMarkedAdapter
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BookMarkedMoviesFragment : Fragment() {

    private lateinit var binding: FragmentBookMarkedMoviesBinding
    private val viewModel: BookMarkedMoviesViewModel by viewModels()
    private lateinit var movieBookMarkedAdapter: MovieBookMarkedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookMarkedMoviesBinding.inflate(inflater, container, false)

        initUI()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }


        return binding.root
    }

    private fun initUI() {
        createRV()
        paintRV()
    }

    private fun paintRV() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.uiState.collect {state->
                movieBookMarkedAdapter.movieList = state.movieBookMarkeds
                movieBookMarkedAdapter.updateList(state.movieBookMarkeds)
                // no entiendo porq no funciona el updatelist y me toca usar esto
                movieBookMarkedAdapter.notifyDataSetChanged()
            }

            }
        }
    }

    private fun createRV() {
        val layout = GridLayoutManager(requireContext(), 2)
        movieBookMarkedAdapter= MovieBookMarkedAdapter(
            onClickListener = {navigateToDetailView(it.id)},
            onClickRemoveListener = {removeMovie(it)})
        binding.rvMoviesBookMarked.apply {
            adapter = movieBookMarkedAdapter
            layoutManager = layout
        }
    }

    fun removeMovie(movie: SingleMovie){
        lifecycleScope.launch {
            viewModel.removeMovie(movie)
            displayMSG("Pelicula borrada de favoritos")
            repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.uiState.collect{
                movieBookMarkedAdapter.movieList = it.movieBookMarkeds
                movieBookMarkedAdapter.updateList(it.movieBookMarkeds)
            }
            }
        }
    }

    fun navigateToDetailView(movieId: Int){
        findNavController().navigate(BookMarkedMoviesFragmentDirections.actionBookMarkedMoviesFragmentToMovieBookMarkedDetailFragment(movieId))
    }
    private fun displayMSG(msg: String){
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }
}