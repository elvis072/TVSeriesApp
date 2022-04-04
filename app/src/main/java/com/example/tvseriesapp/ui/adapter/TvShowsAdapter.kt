package com.example.tvseriesapp.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.ui.adapter.viewholder.TvShowViewHolder

class TvShowsAdapter(diffCallback: DiffUtil.ItemCallback<TvShow>)
    : PagingDataAdapter<TvShow, TvShowViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
    }
}