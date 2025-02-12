package com.utad.mvvm_api_fragments.mainView.ui.adapter.rvBookMarkedMovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utad.mvvm_api_fragments.R
import com.utad.mvvm_api_fragments.mainView.model.domain.SingleMovie

class MovieBookMarkedAdapter(
        var movieList: List<SingleMovie> = emptyList(),
        private val onClickListener: (SingleMovie) -> Unit,
        val onClickRemoveListener: (SingleMovie) -> Unit

): RecyclerView.Adapter<MovieBookMarkedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieBookMarkedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_moviebookmarked, parent, false)
        return MovieBookMarkedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieBookMarkedViewHolder, position: Int) {
        val item = movieList[position]
        holder.render(item, onClickListener, onClickRemoveListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateList(newList: List<SingleMovie>) {
        val diffCallback = MovieSingleDiffCallback(movieList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movieList = newList
        diffResult.dispatchUpdatesTo(this)
    }

}

class MovieSingleDiffCallback(
    private val oldList: List<SingleMovie>,
    private val newList: List<SingleMovie>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}