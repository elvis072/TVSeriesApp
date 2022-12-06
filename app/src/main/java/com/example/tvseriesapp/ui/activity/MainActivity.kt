package com.example.tvseriesapp.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import com.example.tvseriesapp.common.ViewUtil.getAttribute
import com.example.tvseriesapp.databinding.ActivityMainBinding
import com.example.tvseriesapp.ui.RefreshListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), RefreshListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.topAppBar)

        binding.refresh.setOnRefreshListener { onRefresh() }
        binding.refresh.setColorSchemeColors(getAttribute(com.google.android.material.R.attr.colorPrimary))
        binding.refresh.setProgressBackgroundColorSchemeColor(getAttribute(com.google.android.material.R.attr.colorOnSecondary))
    }

    override fun onRefresh() {
        val navHostFragment = binding.navHostFragment.getFragment<Fragment?>()
        val fragment = navHostFragment?.childFragmentManager?.fragments?.first()

        if (fragment is RefreshListener)
            fragment.onRefresh()
    }

    override fun onCompleteRefresh() {
        binding.refresh.isRefreshing = false
    }
}