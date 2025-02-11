package com.utad.mvvm_api_fragments.mainView.domain.rvCategories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utad.mvvm_api_fragments.R
import com.utad.mvvm_api_fragments.mainView.domain.rv.MovieViewHolder
import com.utad.mvvm_api_fragments.mainView.model.domain.Movie
import com.utad.mvvm_api_fragments.mainView.model.network.Genre

class CategoryAdapter(
    var movieList: List<Genre> = emptyList(),
): RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = movieList[position]
        return holder.render(item)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateList(newList: List<Genre>) {
        val diffCallback = CategoryDiffCallback(movieList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movieList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}

class CategoryDiffCallback(
    private val oldList: List<Genre>,
    private val newList: List<Genre>
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