package com.example.mvp_livedata_base_kotlin.feature.navigation.bottomnavigation

import android.os.Bundle
import android.view.View
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.extensions.isVisible
import com.example.mvp_livedata_base_kotlin.base.extensions.whenNull

class TabManager(private val tabNavigable: TabNavigable) {
    private var currentTabId: Int = R.id.navigation_fundamentals
    private var tabHistory = TabHistory().apply { push(R.id.navigation_fundamentals) }

    private val fundamentalsTabContainer: View by lazy { tabNavigable.fundamentalsTabContainer }
    private val insightTabContainer: View by lazy { tabNavigable.insightTabContainer }

    companion object {
        private const val KEY_TAB_HISTORY = "key_tab_history"
    }

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(KEY_TAB_HISTORY, tabHistory)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            tabHistory = it.getSerializable(KEY_TAB_HISTORY) as TabHistory

            switchTab(tabNavigable.bottomNavigationView.selectedItemId, false)
        }
    }

    fun onBackPressed() {
        switchTab(if (tabHistory.size > 0) tabHistory.popPrevious() else null, false)
    }

    fun switchTab(tabId: Int?, addToHistory: Boolean = true) {
        tabId?.let {
            currentTabId = tabId

            if (addToHistory) {
                tabHistory.push(tabId)
            }

            when (tabId) {
                R.id.navigation_fundamentals ->
                    invisibleTabContainerExcept(fundamentalsTabContainer)

                R.id.navigation_insight ->
                    invisibleTabContainerExcept(insightTabContainer)

                else -> tabNavigable.finishActivity()
            }

            tabNavigable.bottomNavigationView.menu.findItem(tabId)?.isChecked = true
        }.whenNull {
            tabNavigable.finishActivity()
        }
    }

    private fun invisibleTabContainerExcept(container: View) {
        fundamentalsTabContainer.isVisible = container == fundamentalsTabContainer
        insightTabContainer.isVisible = container == insightTabContainer
    }
}