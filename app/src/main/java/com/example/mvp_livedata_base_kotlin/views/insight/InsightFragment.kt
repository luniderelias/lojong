package com.example.mvp_livedata_base_kotlin.views.insight

import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment

class InsightFragment : BaseFragment() {
    override val resLayout = R.layout.fragment_insight

    companion object {
        fun newInstance() = InsightFragment()
    }
}