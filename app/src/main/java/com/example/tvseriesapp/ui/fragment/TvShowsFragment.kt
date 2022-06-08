package com.example.tvseriesapp.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvseriesapp.R
import com.example.tvseriesapp.common.Constants
import com.example.tvseriesapp.databinding.FragmentTvShowsBinding
import com.example.tvseriesapp.ui.adapter.TvShowsAdapter
import com.example.tvseriesapp.ui.viewmodel.TvShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowsFragment : BaseFragment<FragmentTvShowsBinding>(FragmentTvShowsBinding::inflate) {
    private val viewModel by viewModels<TvShowViewModel>()
    private lateinit var tvShowsAdapter: TvShowsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setUp() {
        tvShowsAdapter = TvShowsAdapter { id ->
            findNavController().navigate(
                R.id.action_tvShowsFragment_to_tvShowDetailFragment,
                bundleOf(Constants.TV_SHOW_ID_KEY to id)
            )
        }

        binding.recycleView.apply {
            adapter = tvShowsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getShows("")
                        .collectLatest {
                            tvShowsAdapter.submitData(it)
                        }
                }

                launch {
                    tvShowsAdapter.loadStateFlow.collectLatest { state ->
//                        binding.refresh.isRefreshing = false
                        binding.progressCircular.isVisible = state.refresh is LoadState.Loading
                    }
                }

                launch {
//                    binding.refresh.setOnRefreshListener {
//                        viewLifecycleOwner.lifecycleScope.launch {
//                            viewModel.getShows("")
//                                .collectLatest {
//                                    adapter.submitData(it)
//                                }
//                        }
//                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_navigation_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        setUpSearchView(menu)
    }

    private fun setUpSearchView(menu: Menu) {
        val search = menu.findItem(R.id.menu_search)
        val searchView = (search?.actionView as SearchView).apply {
            queryHint = getString(R.string.search)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (isDetached || !isVisible)
                    return false

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getShows(query)
                        .flowWithLifecycle(lifecycle)
                        .collectLatest {
                            tvShowsAdapter.submitData(it)
                        }
                }
                return true
            }
        })
    }
}