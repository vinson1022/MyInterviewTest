package com.vinson.myinterviewtest.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vinson.apimodule.api.PixabayApi
import com.vinson.datamodel.base.Result
import com.vinson.datamodel.models.ImageResult
import com.vinson.myinterviewtest.util.getResult

class SearchImageDataSource {

    private val api = PixabayApi.create()

    fun getSearchImageSource(queryKey: String) = object : PagingSource<Int, ImageResult>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResult> {
            return try {
                val nextPage = params.key ?: 1
                val response = api.queryHits(nextPage, queryKey)

                when (val result = response.getResult()) {
                    is Result.Success -> {
                        val isOverFlow = 20 * nextPage > response.body()?.totalHit ?: 0
                        LoadResult.Page(
                                data = result.data,
                                prevKey = null,
                                nextKey = if (isOverFlow) null else nextPage.plus(1)
                        )
                    }
                    is Result.Error -> {
                        LoadResult.Error(result.exception)
                    }
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, ImageResult>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                val anchorPage = state.closestPageToPosition(anchorPosition)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
        }
    }
}