package com.example.tvseriesapp.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.ui.adapter.viewholder.TvShowViewHolder

class TvShowsAdapter(private val itemCLickListener: (id: Int) -> Unit)
    : PagingDataAdapter<TvShow, TvShowViewHolder>(TvShow.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(parent, itemCLickListener)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
    }
}