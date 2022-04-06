package com.example.tvseriesapp.domain.model

import androidx.recyclerview.widget.DiffUtil

data class TvShow(
    val id: Int,
    val name: String,
    val premiered: String,
    val image: String,
    val language: String,
    val rating: Float,
    val runtime: Int
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }
}