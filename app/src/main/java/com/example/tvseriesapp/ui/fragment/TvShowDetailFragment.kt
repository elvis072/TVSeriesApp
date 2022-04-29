package com.example.tvseriesapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.tvseriesapp.R
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.common.ViewUtil
import com.example.tvseriesapp.databinding.FragmentTvShowDetailBinding
import com.example.tvseriesapp.ui.adapter.TvShowEpisodesAdapter
import com.example.tvseriesapp.ui.adapter.itemdecoration.HeaderItemDecoration
import com.example.tvseriesapp.ui.viewmodel.TvShowDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowDetailFragment: BaseFragment<FragmentTvShowDetailBinding>(FragmentTvShowDetailBinding::inflate) {
    val viewModel by viewModels<TvShowDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refresh.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.refresh()
                }
            }
        }

        setShowDetailData()
        setShowEpisodesData()
    }

    private fun setShowDetailData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showDetailState.collectLatest { state ->
                    binding.refresh.isRefreshing = false
                    binding.progressCircular.isVisible = state.isLoading

                    state.showDetail?.image?.let {
                        binding.poster.load(it) {
                            crossfade(true)
                            context?.let {  ctx ->
                                placeholder(ViewUtil.cretePlaceholder(ctx))
                            }
                        }
                    }

                    state.showDetail?.name?.let {
                        binding.title.text = it
                    }

                    state.showDetail?.rating?.let {
                        binding.rating.text = getString(R.string.tv_show_rating, it)
                    }

                    state.showDetail?.language?.let {
                        binding.language.text = it
                    }

                    state.showDetail?.runtime?.let {
                        binding.runtime.text = getString(R.string.tv_show_runtime, it)
                    }

                    state.showDetail?.genres?.let {
                        binding.genres.text = it.joinToString("  ")
                    }

                    state.showDetail?.days?.joinToString()?.let {
                        binding.days.text = HtmlCompat.fromHtml(
                            getString(R.string.tv_show_days, it),
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )
                    }

                    state.showDetail?.time?.let {
                        binding.time.text = HtmlCompat.fromHtml(
                            getString(R.string.tv_show_time, it),
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )
                    }

                    state.showDetail?.summary?.let {
                        binding.summary.text =
                            HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    }
                }
            }
        }
    }

    private fun setShowEpisodesData() {
        val episodesAdapter = TvShowEpisodesAdapter { seasonId, episodeId ->
            arguments?.getInt(Constants.TV_SHOW_ID_KEY)?.let { showId ->
                findNavController().navigate(
                    R.id.action_tvShowDetailFragment_to_tvShowEpisodeDetailFragment,
                    bundleOf(
                        Constants.TV_SHOW_ID_KEY to showId,
                        Constants.TV_SHOW_SEASON_ID_KEY to seasonId,
                        Constants.TV_SHOW_EPISODE_KEY to episodeId
                    )
                )
            }
        }

        binding.episodeList.apply {
            adapter = episodesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            // item decorators
            addItemDecoration(HeaderItemDecoration { position ->
                if (episodesAdapter.currentList.isEmpty() || position < 0 || position >= episodesAdapter.currentList.size)
                    return@HeaderItemDecoration ""
                getString(R.string.tv_show_season, episodesAdapter.currentList[position].season)
            })
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showEpisodesState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest { state ->
                    binding.episodesProgressCircular.isVisible = state.isLoading
                    episodesAdapter.submitList(state.episodes)
                }
        }
    }
}