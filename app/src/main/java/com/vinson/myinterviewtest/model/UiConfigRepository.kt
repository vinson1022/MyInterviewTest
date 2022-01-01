package com.vinson.myinterviewtest.model

class UiConfigRepository private constructor() {

    private val dataSource = UiConfigDataSource()

    suspend fun getLayoutConfig() = dataSource.getLayoutConfig()

    companion object {
        private var INSTANCE: UiConfigRepository? = null

        @JvmStatic
        fun getInstance() = INSTANCE ?: synchronized(UiConfigRepository::class.java) {
            INSTANCE ?: UiConfigRepository().also { INSTANCE = it }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}