package com.example.mvp_livedata_base_kotlin.views.fundamentals

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment
import com.example.mvp_livedata_base_kotlin.base.extensions.injectPresenter
import com.example.mvp_livedata_base_kotlin.base.extensions.shouldShowView
import com.example.mvp_livedata_base_kotlin.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fundamentals_toolbar.view.*

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
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar = fundamentalsToolbar
        activity?.let {
            val mainActivity = it as MainActivity
            mainActivity.setSupportActionBar(toolbar as Toolbar)
            mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

            toolbar.tsTxtTitle.text = getString(R.string.fundamentals)
        }
    }

    private fun setupPresenter() {
        initPresenter()
        observeData()
    }

    override fun initPresenter() {
        presenter.init()
    }

    override fun onResume() {
        super.onResume()
        activity?.let { (it as MainActivity).changeActionBar() }
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