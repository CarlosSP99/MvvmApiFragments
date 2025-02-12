package com.utad.mvvm_api_fragments.mainView.ui.adapter.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.utad.mvvm_api_fragments.R
import com.utad.mvvm_api_fragments.databinding.ItemMovieBinding
import com.utad.mvvm_api_fragments.mainView.util.Constants
import com.utad.mvvm_api_fragments.mainView.model.domain.Movie
import java.text.DecimalFormat

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemMovieBinding.bind(view)


    fun render(item: Movie, onClickListener: (Movie) -> Unit) {
        binding.tvTitle.text = item.title
        val decimalFormat = DecimalFormat("#.#") // Formato con 1 decimal
        binding.tvNote.text = decimalFormat.format(item.voteAverage)
        binding.ivPosterMovie.setImageDrawable(null)

        Glide.with(binding.ivPosterMovie.context)
            .load("${Constants.BASE_URL_IMG}${item.posterPath}")
            .placeholder(R.drawable.icplaholdermovie)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivPosterMovie)

    binding.cvMovie.setOnClickListener {
        onClickListener(item)
    }


    }
}