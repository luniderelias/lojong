package com.example.mvp_livedata_base_kotlin.base.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragmentTo(containerId: Int, fragment: Fragment, tag: String = "") {
    supportFragmentManager.beginTransaction().add(containerId, fragment, tag).commit()
}