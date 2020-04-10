package com.example.mvp_livedata_base_kotlin.views.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment
import com.example.mvp_livedata_base_kotlin.base.extensions.injectPresenter
import com.example.mvp_livedata_base_kotlin.base.extensions.shouldShowView
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : BaseFragment(), MainContract.View {

    override val presenter by injectPresenter(this)
    override val resLayout = R.layout.fragment_main

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupPresenter()
    }

    override fun setupViews(view: View) {
        super.setupViews(view)
        view.appCompatImageView.setOnClickListener { onReload() }
    }

    private fun setupPresenter() {
        initPresenter()
        observeData()
    }

    override fun initPresenter() {
        presenter.init()
    }

    override fun observeData() {
        presenter.observeForExampleData().observe(this@MainFragment,
            Observer { response ->
                response?.let {
                    view?.messageTextView?.text = it.message
                }
            }
        )
    }

    override fun onReload() {
        presenter.loadData()
    }

    override fun handleMessageVisibility(shouldShow: Boolean) {
        view?.messageTextView?.visibility = shouldShow.shouldShowView
        handleLoadingVisibility(!shouldShow)
    }

    override fun handleLoadingVisibility(shouldShow: Boolean) {
        view?.progressBar?.visibility = shouldShow.shouldShowView
    }
}