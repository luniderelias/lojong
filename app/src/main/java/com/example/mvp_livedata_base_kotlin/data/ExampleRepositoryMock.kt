package com.example.mvp_livedata_base_kotlin.data

class ExampleRepositoryMock : ExampleRepository {

    private var flag = false

    override suspend fun getExample(): ExampleData {
        Thread.sleep(1000)
        flag = !flag
        return if (flag)
            ExampleData(message = "Hello World!")
        else
            ExampleData(message = "Sorry, we could not find your message")
    }

}