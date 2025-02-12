package com.utad.mvvm_api_fragments.mainView.ui.bookmarkDetail

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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.utad.mvvm_api_fragments.R
import com.utad.mvvm_api_fragments.databinding.FragmentMovieBookMarkedDetailBinding
import com.utad.mvvm_api_fragments.mainView.ui.adapter.rvCategories.CategoryAdapter
import com.utad.mvvm_api_fragments.mainView.ui.detail.DetailMovieFragmentArgs
import com.utad.mvvm_api_fragments.mainView.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieBookMarkedDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieBookMarkedDetailBinding
    private val viewModel: MovieBookMarkDetailViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter
    private val args: DetailMovieFragmentArgs by navArgs()
    private val movieId by lazy { args.movieId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBookMarkedDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        initUI()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun initUI() {
        notifyViewModel()
        createRV()
        paintRV()
    }

    private fun notifyViewModel() {
        lifecycleScope.launch {
            viewModel.getDatabyId(movieId)
        }
    }

    private fun paintRV() {
        lifecycleScope.launch {
            // Solo observar cuando el Fragment está en STARTED o RESUMED
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    adapter.movieList = state.movie.genres!!
                    adapter.updateList(state.movie.genres!!)
                    binding.tvTitle.text = state.movie.title
                    binding.tvDescripcion.text = state.movie.overview
                    binding.tvDate.text = state.movie.releaseDate
                    Glide.with(binding.ivPoster.context)
                        .load("${Constants.BASE_URL_IMG}${state.movie.posterPath}")
                        .placeholder(R.drawable.icplaholdermovie)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivPoster)

                    binding.pbLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                }
            }
        }
    }

    private fun createRV() {
        adapter = CategoryAdapter()
        binding.rvCategories.adapter = adapter
    }


}