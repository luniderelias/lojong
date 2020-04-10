package com.example.mvp_livedata_base_kotlin.views.fundamentals

import androidx.lifecycle.MutableLiveData
import com.example.mvp_livedata_base_kotlin.base.extensions.launchIO
import com.example.mvp_livedata_base_kotlin.data.ExampleData
import com.example.mvp_livedata_base_kotlin.data.ExampleRepositoryMock

class FundamentalsPresenter(
    override var view: FundamentalsContract.View,
    private val repository: ExampleRepositoryMock
) : FundamentalsContract.Presenter {

    var response = MutableLiveData<ExampleData>()

    override fun init() {
        loadData()
    }

    override fun loadData() {
        view.handleMessageVisibility(shouldShow = false)
        launchIO {
            response.postValue(repository.getExample())
            view.handleMessageVisibility(shouldShow = true)
        }
    }

    override fun observeForExampleData(): MutableLiveData<ExampleData> {
        return response
    }
}