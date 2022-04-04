package com.example.tvseriesapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvseriesapp.R
import com.example.tvseriesapp.databinding.FragmentTvShowsBinding
import com.example.tvseriesapp.ui.adapter.TvShowsAdapter
import com.example.tvseriesapp.ui.adapter.diffutil.TvShowDiffCallback
import com.example.tvseriesapp.ui.viewmodel.TvShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TvShowsFragment : BaseFragment<FragmentTvShowsBinding>(FragmentTvShowsBinding::inflate) {
    private val viewModel by viewModels<TvShowViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TvShowsAdapter(TvShowDiffCallback)
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.tvShows.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}