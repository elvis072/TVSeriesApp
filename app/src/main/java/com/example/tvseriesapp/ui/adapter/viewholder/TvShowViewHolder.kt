package com.example.tvseriesapp.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.example.tvseriesapp.R
import com.example.tvseriesapp.common.ViewUtil
import com.example.tvseriesapp.databinding.TvShowViewHolderBinding
import com.example.tvseriesapp.domain.model.TvShow

class TvShowViewHolder(parent: ViewGroup, itemClickListener:(id: Int) -> Unit) : BaseViewHolder<TvShowViewHolderBinding, TvShow>(
    TvShowViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    var model: TvShow? = null
    init {
        itemView.setOnClickListener {
            model?.let { show ->
                itemClickListener(show.id)
            }
        }
    }
    override fun bindTo(model: TvShow?) {
        this.model = model
        binding.title.text = model?.name
        binding.poster.load(model?.image) {
            crossfade(true)
            placeholder(ViewUtil.cretePlaceholder(binding.root.context))
        }
        binding.rating.text = binding.root.context.getString(R.string.tv_show_rating, model?.rating)
        binding.premiered.text = model?.premiered?.split('-')?.first()
        binding.language.text = model?.language
        binding.runtime.text = binding.root.context.getString(R.string.tv_show_runtime, model?.runtime)
    }
}