package com.utad.mvvm_api_fragments.mainView.ui.adapter.rvCategories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.utad.mvvm_api_fragments.databinding.ItemCategoryBinding
import com.utad.mvvm_api_fragments.mainView.model.network.Genre

class CategoryViewHolder(view: View): RecyclerView.ViewHolder(view)  {
    val binding = ItemCategoryBinding.bind(view)

    fun render(item: Genre){
        binding.tvCategory.text = item.name
    }
}