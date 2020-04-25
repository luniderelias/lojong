package com.example.mvp_livedata_base_kotlin.views.insight

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment
import com.example.mvp_livedata_base_kotlin.views.insight.articles.ArticlesFragment
import com.example.mvp_livedata_base_kotlin.views.insight.quotes.QuotesFragment
import com.example.mvp_livedata_base_kotlin.views.insight.videos.VideosFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_insight.*
import kotlinx.android.synthetic.main.insight_toolbar.*

private const val NUM_PAGES = 3

class InsightFragment : BaseFragment() {
    override val resLayout = R.layout.fragment_insight
    companion object {
        fun newInstance() = InsightFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insightTabsContainer.adapter = ScreenSlidePagerAdapter(activity!!)
        TabLayoutMediator(insightTabLayout, insightTabsContainer) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String{
        return when(position){
            0 -> getString(R.string.videos)
            1 -> getString(R.string.articles)
            2 -> getString(R.string.quotes)
            else -> ""
        }
    }
}

class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment{
        return when(position){
            0 -> VideosFragment()
            1 -> ArticlesFragment()
            2 -> QuotesFragment()
            else -> VideosFragment()
        }
    }
}