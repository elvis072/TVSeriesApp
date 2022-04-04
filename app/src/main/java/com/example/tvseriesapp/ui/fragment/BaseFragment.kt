package com.example.tvseriesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() where VB: ViewBinding {
    private var _binding: VB? = null

    protected val binding: VB
                get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater)
        return requireNotNull(_binding).root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}