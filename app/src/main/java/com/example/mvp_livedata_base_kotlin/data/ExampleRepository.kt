package com.example.mvp_livedata_base_kotlin.data

interface ExampleRepository {
    suspend fun getExample(): ExampleData
}