package com.example.mvp_livedata_base_kotlin.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.extensions.injectPresenter
import com.example.mvp_livedata_base_kotlin.base.extensions.isVisible
import com.example.mvp_livedata_base_kotlin.feature.navigation.bottomnavigation.TabManager
import com.example.mvp_livedata_base_kotlin.feature.navigation.bottomnavigation.TabNavigable
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsFragment
import com.example.mvp_livedata_base_kotlin.views.insight.InsightFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
BottomNavigationView.OnNavigationItemSelectedListener, MainContract.View,
    TabNavigable {

    override val presenter by injectPresenter(this)

    private val tabManager by lazy { TabManager(this) }
    override val fundamentalsTabContainer: View
        get() = mainTabFundamentalsContainer

    override val insightTabContainer: View
        get() = mainTabInsightContainer

    override val bottomNavigationView: BottomNavigationView
        get() = mainBottomNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBottomNavigation.setOnNavigationItemSelectedListener(this)

        setupViews()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        tabManager.onBackPressed()
    }

    override fun finishActivity() {
        finish()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        tabManager.switchTab(menuItem.itemId)
        return true
    }

    override fun showLoading() {
        mainLoading.isVisible = true
    }

    override fun hideLoading() {
        mainLoading.isVisible = false
    }

    private fun setupViews() {
        supportFragmentManager.beginTransaction().let {
            addExtraFragments(it)
            it.commit()
        }
    }

    private fun addExtraFragments(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.add(R.id.mainTabFundamentalsContainer, FundamentalsFragment.newInstance())
        fragmentTransaction.add(R.id.mainTabInsightContainer, InsightFragment.newInstance())
    }

}
