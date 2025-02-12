package com.utad.mvvm_api_fragments.mainView.ui.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utad.mvvm_api_fragments.R
import com.utad.mvvm_api_fragments.mainView.model.domain.Movie

class MovieAdapter(
    var movieList: List<Movie> = emptyList(),
    private val onClickListener: (Movie) -> Unit
    ): RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateList(newList: List<Movie>) {
        val diffCallback = ProductionDiffCallback(movieList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movieList = newList
        diffResult.dispatchUpdatesTo(this)
    }

}

class ProductionDiffCallback(
    private val oldList: List<Movie>,
    private val newList: List<Movie>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.    size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}