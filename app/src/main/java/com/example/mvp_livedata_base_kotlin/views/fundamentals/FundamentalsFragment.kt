package com.example.mvp_livedata_base_kotlin.views.fundamentals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment
import com.example.mvp_livedata_base_kotlin.base.extensions.injectPresenter
import com.example.mvp_livedata_base_kotlin.base.extensions.shouldShowView
import kotlinx.android.synthetic.main.fragment_main.view.*

class FundamentalsFragment : BaseFragment(), FundamentalsContract.View {

    override val presenter by injectPresenter(this)
    override val resLayout = R.layout.fragment_main

    companion object {
        fun newInstance() = FundamentalsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupPresenter()
    }

    private fun setupPresenter() {
        initPresenter()
        observeData()
    }

    override fun initPresenter() {
        presenter.init()
    }

    override fun observeData() {
        presenter.observeForExampleData().observe(viewLifecycleOwner,
            Observer { response ->
                response?.let {
                }
            }
        )
    }

    override fun onReload() {
        presenter.loadData()
    }

    override fun handleMessageVisibility(shouldShow: Boolean) {
        handleLoadingVisibility(!shouldShow)
    }

    override fun handleLoadingVisibility(shouldShow: Boolean) {
        view?.progressBar?.visibility = shouldShow.shouldShowView
    }
}