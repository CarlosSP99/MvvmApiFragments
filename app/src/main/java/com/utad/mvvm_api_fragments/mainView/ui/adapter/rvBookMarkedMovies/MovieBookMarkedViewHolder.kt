package com.utad.mvvm_api_fragments.mainView.ui.adapter.rvBookMarkedMovies

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.utad.mvvm_api_fragments.R
import com.utad.mvvm_api_fragments.databinding.ItemMoviebookmarkedBinding
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie
import com.utad.mvvm_api_fragments.mainView.util.Constants

class MovieBookMarkedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemMoviebookmarkedBinding.bind(view)


    fun render(item: SingleMovie,
               onClickListener: (SingleMovie) -> Unit,
               onClickRemoveListener: (SingleMovie) -> Unit) {
        Log.i("ESTA SALIENDOamigo", item.title)
        binding.tvTitle.text = item.title
        binding.ivPosterMovie.setImageDrawable(null)
        binding.cvMovie.setOnClickListener {
            onClickListener(item)
        }
        binding.ivBookMark.setOnClickListener {
            onClickRemoveListener(item)
        }
        Glide.with(binding.ivPosterMovie.context)
            .load("${Constants.BASE_URL_IMG}${item.posterPath}")
            .placeholder(R.drawable.icplaholdermovie)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivPosterMovie)


    }
}