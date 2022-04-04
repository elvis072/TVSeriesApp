package com.example.tvseriesapp.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB, M>(protected val binding: VB)
    : RecyclerView.ViewHolder(binding.root) where VB: ViewBinding {
    abstract fun bindTo(model: M?)
}