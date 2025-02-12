package com.utad.mvvm_api_fragments.mainView.ui.detail

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
import com.utad.mvvm_api_fragments.databinding.FragmentDetailMovieBinding
import com.utad.mvvm_api_fragments.mainView.ui.adapter.rv.MovieAdapter
import com.utad.mvvm_api_fragments.mainView.ui.adapter.rvCategories.CategoryAdapter
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private lateinit var binding: FragmentDetailMovieBinding
    private val viewModel: DetailMovieViewModel by viewModels()
    private val args: DetailMovieFragmentArgs by navArgs()
    private val movieId by lazy { args.movieId }
    private lateinit var adapterRelatedMovies: MovieAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var bookMarked=false
    // donde guardaré la pelicula
    private var movie: SingleMovie = SingleMovie()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)

        initUI()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivBookMark.setOnClickListener {
            bookMarked=!bookMarked
            if (bookMarked) {
                viewModel.bookMarkMovie(movie)
                displayMSG(msg = "La pelicula ${movie.title} ha sido añadida a favoritos")
                binding.ivBookMark.setImageResource(R.drawable.ic_bookmark_full)
            } else{
                viewModel.unbookMarkMovie(movie)
                displayMSG(msg = "La pelicula ${movie.title} ha sido eliminada de favoritos")
                binding.ivBookMark.setImageResource(R.drawable.ic_bookmark)
            }
        }

        return binding.root
    }

    private fun initUI() {
        fetchData()
        createRV()
        paintUI()
    }

    private fun createRV() {
        relatedMoviesRV()
        categoryRV()
    }

    private fun categoryRV() {
        categoryAdapter = CategoryAdapter()

        binding.rvCategories.apply {
            adapter = categoryAdapter
        }
    }

    private fun relatedMoviesRV() {
        adapterRelatedMovies = MovieAdapter(onClickListener = {})
        binding.rvRelated.adapter = adapterRelatedMovies
    }

    private fun fetchData() {
        lifecycleScope.launch {
            viewModel.getMovieDetails(movieId)
        }
    }

    private fun paintUI() {
        lifecycleScope.launch {
            // Solo observar cuando el Fragment está en STARTED o RESUMED
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect{
                if(it.movie!=null){
                    binding.tvTitle.text = it.movie.title
                    binding.tvDescripcion.text = it.movie.overview
                    binding.tvDate.text = it.movie.releaseDate

                    Glide.with(binding.ivPoster.context)
                        .load("${Constants.BASE_URL_IMG}${it.movie.posterPath}")
                        .placeholder(R.drawable.icplaholdermovie)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivPoster)

                    if(!it.movie.genres.isNullOrEmpty()){
                        val genre = it.movie.genres!![0].id
                        viewModel.updateGenres(genre)
                    }

                    it.movie.genres?.let { genreList -> categoryAdapter.updateList(genreList) }

                    adapterRelatedMovies.updateList(it.relatedMovies!!)

                    binding.pbLoading.visibility = if (it.isLoading) View.VISIBLE else View.GONE

                    viewModel.checkIfbookMarkMovie(it.movie.id)


                    if (it.bookMarked){
                        binding.ivBookMark.setImageResource(R.drawable.ic_bookmark_full)
                        bookMarked=true
                    }else{
                        binding.ivBookMark.setImageResource(R.drawable.ic_bookmark)
                        bookMarked=false
                    }
                    movie=it.movie
                }
            }
        }
    }
    }

    private fun displayMSG(msg: String){
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

}