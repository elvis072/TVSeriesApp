package com.example.tvseriesapp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tvseriesapp.domain.model.Episode
import com.example.tvseriesapp.ui.adapter.viewholder.TvShowEpisodeViewHolder

class TvShowEpisodesAdapter(private val itemClickListener: (seasonId: Int, episodeId: Int) -> Unit)
    : ListAdapter<Episode, TvShowEpisodeViewHolder>(Episode.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowEpisodeViewHolder {
        return TvShowEpisodeViewHolder(parent, itemClickListener)
    }

    override fun onBindViewHolder(holder: TvShowEpisodeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
    }
}