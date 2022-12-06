package com.example.tvseriesapp.ui.fragment

import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.tvseriesapp.R
import com.example.tvseriesapp.common.ViewUtil
import com.example.tvseriesapp.databinding.FragmentTvShowEpisodeDetailBinding
import com.example.tvseriesapp.ui.viewmodel.TvShowEpisodeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowEpisodeDetailFragment : BaseFragment<FragmentTvShowEpisodeDetailBinding>(FragmentTvShowEpisodeDetailBinding::inflate) {
    val viewModel by viewModels<TvShowEpisodeDetailViewModel>()

    override fun setUp() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        binding.progressCircular.isVisible = state.isLoading
                        onCompleteRefresh()

                        state.episode?.let {
                            binding.poster.load(it.image) {
                                crossfade(true)
                                context?.let { ctx ->
                                    placeholder(ViewUtil.cretePlaceholder(ctx))
                                }
                            }

                            binding.title.text = it.name
                            binding.number.text = getString(R.string.tv_show_episode, it.number)
                            binding.season.text = getString(R.string.tv_show_season, it.season)
                            binding.rating.text = getString(R.string.tv_show_rating, it.rating)
                            binding.runtime.text = getString(R.string.tv_show_runtime, it.runtime)
                            binding.summary.text =
                                HtmlCompat.fromHtml(it.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        }
                    }
                }

                launch { onRefresh() }
            }
        }
    }

    override fun onRefresh() {
        viewModel.refresh()
    }
}