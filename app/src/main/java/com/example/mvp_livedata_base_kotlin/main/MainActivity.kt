package com.example.mvp_livedata_base_kotlin.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.extensions.addFragmentTo

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = createFragment()
        addFragmentTo(R.id.container, fragment)
    }

    private fun createFragment(): MainFragment {
        return MainFragment.newInstance()
    }
}
