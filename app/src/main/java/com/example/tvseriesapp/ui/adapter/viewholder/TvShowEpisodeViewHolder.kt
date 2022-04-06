package com.example.tvseriesapp.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tvseriesapp.R
import com.example.tvseriesapp.databinding.TvShowEpisodeViewHolderBinding
import com.example.tvseriesapp.domain.model.Episode

class TvShowEpisodeViewHolder(
    parent: ViewGroup,
    itemClickListener: (seasonId: Int, episodeId: Int) -> Unit
    ) : BaseViewHolder<TvShowEpisodeViewHolderBinding, Episode>(
        TvShowEpisodeViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
{
    var model: Episode? = null
    init {
        itemView.setOnClickListener {
            model?.let { episode ->
                itemClickListener(episode.season, episode.number)
            }
        }
    }
    override fun bindTo(model: Episode?) {
        this.model = model
        binding.name.text = model?.name
        binding.number.text = binding.root.context.getString(R.string.tv_show_episode, model?.number)
    }
}