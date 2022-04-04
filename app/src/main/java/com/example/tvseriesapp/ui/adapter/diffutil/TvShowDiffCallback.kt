package com.example.tvseriesapp.ui.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.tvseriesapp.domain.model.TvShow

object TvShowDiffCallback : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem == newItem
    }
}