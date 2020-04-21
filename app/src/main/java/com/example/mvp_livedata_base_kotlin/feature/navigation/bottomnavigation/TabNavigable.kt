package com.example.mvp_livedata_base_kotlin.feature.navigation.bottomnavigation

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

interface TabNavigable {
    val fundamentalsTabContainer: View
    val insightTabContainer: View

    val bottomNavigationView : BottomNavigationView

    fun finishActivity()
}