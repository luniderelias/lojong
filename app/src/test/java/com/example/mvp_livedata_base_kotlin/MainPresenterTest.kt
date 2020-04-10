package com.example.mvp_livedata_base_kotlin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mvp_livedata_base_kotlin.data.ExampleData
import com.example.mvp_livedata_base_kotlin.data.ExampleRepositoryMock
import com.example.mvp_livedata_base_kotlin.views.main.MainContract
import com.example.mvp_livedata_base_kotlin.views.main.MainPresenter
import com.example.mvp_livedata_base_kotlin.util.testApp
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin

class MainPresenterTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repository = ExampleRepositoryMock()
    private val view = mock<MainContract.View>()

    private val mainPresenter by lazy {
        MainPresenter(view, repository)
    }

    private var responseObserver = mock<Observer<ExampleData>>()

    @Before
    fun setup() {
        startKoin(testApp)
        mainPresenter.response.observeForever(responseObserver)
    }

    @After
    fun after(){
        stopKoin()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test load success should show hello world`(): Unit = runBlockingTest {
        Dispatchers.setMain(Dispatchers.IO)
        mainPresenter.loadData()
        Thread.sleep(1200)

        verify(responseObserver).onChanged(mainPresenter.response.value)
        verify(view).handleMessageVisibility(false)
        verify(view).handleMessageVisibility(true)

        assert(mainPresenter.response.value!!.message == "Hello World!")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test load failed should show failed message`(): Unit = runBlockingTest {
        Dispatchers.setMain(Dispatchers.IO)
        mainPresenter.init()
        Thread.sleep(1200)
        mainPresenter.loadData()
        Thread.sleep(1200)

        verify(responseObserver).onChanged(mainPresenter.response.value)
        verify(view, times(2)).handleMessageVisibility(false)
        verify(view, times(2)).handleMessageVisibility(true)

        assert(mainPresenter.response.value!!.message == "Sorry, we could not find your message")
    }
}