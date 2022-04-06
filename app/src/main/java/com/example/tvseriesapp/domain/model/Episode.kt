package com.example.tvseriesapp.domain.model

import androidx.recyclerview.widget.DiffUtil

data class Episode(
    val id: Int,
    val name: String,
    val image: String,
    val number: Int,
    val rating: Float,
    val runtime: Int,
    val season: Int,
    val summary: String
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }
}