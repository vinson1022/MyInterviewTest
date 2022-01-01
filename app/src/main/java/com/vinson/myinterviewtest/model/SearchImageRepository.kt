package com.vinson.myinterviewtest.model

import androidx.paging.Pager
import androidx.paging.PagingConfig

class SearchImageRepository private constructor() {

    private val dataSource = SearchImageDataSource()

    fun queryImageResults(queryKey: String) = Pager(config = PagingConfig(5)) {
        dataSource.getSearchImageSource(queryKey)
    }

    companion object {
        private var INSTANCE: SearchImageRepository? = null

        @JvmStatic
        fun getInstance() = INSTANCE ?: synchronized(SearchImageRepository::class.java) {
            INSTANCE ?: SearchImageRepository().also { INSTANCE = it }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}