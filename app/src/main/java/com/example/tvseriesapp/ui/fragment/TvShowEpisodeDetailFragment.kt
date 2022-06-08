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
                    viewModel.state
                        .collectLatest { state ->
//                            binding.refresh.isRefreshing = false
                            binding.progressCircular.isVisible = state.isLoading

                            state.episode?.image?.let {
                                binding.poster.load(it) {
                                    crossfade(true)
                                    context?.let {  ctx ->
                                        placeholder(ViewUtil.cretePlaceholder(ctx))
                                    }
                                }
                            }

                            state.episode?.name?.let {
                                binding.title.text = it
                            }

                            state.episode?.number?.let {
                                binding.number.text = getString(R.string.tv_show_episode, it)
                            }

                            state.episode?.season?.let {
                                binding.season.text = getString(R.string.tv_show_season, it)
                            }

                            state.episode?.rating?.let {
                                binding.rating.text = getString(R.string.tv_show_rating, it)
                            }

                            state.episode?.runtime?.let {
                                binding.runtime.text = getString(R.string.tv_show_runtime, it)
                            }

                            state.episode?.summary?.let {
                                binding.summary.text =
                                    HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
                            }
                        }
                }

//                launch {
//                    binding.refresh.setOnRefreshListener {
//                        viewModel.refresh()
//                    }
//                }
            }
        }
    }
}