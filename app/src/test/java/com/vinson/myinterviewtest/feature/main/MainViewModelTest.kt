package com.vinson.myinterviewtest.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vinson.myinterviewtest.BaseTest
import com.vinson.myinterviewtest.model.SearchImageRepository
import com.vinson.myinterviewtest.util.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest : BaseTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    override fun setUp() {
        super.setUp()
        mockkObject(SearchImageRepository)
        every { SearchImageRepository.getInstance() } returns mockk()

        mockAppPref()
    }

    @After
    override fun end() {
        super.end()
    }

    @Test
    fun testHistoryQueries_giveInitString_thenGetQueries() {
        @Tag(GIVEN)
        every { AppPref.rawHistoryQueries } returns "123, 456"

        @Tag(WHEN)
        val viewModel = MainViewModel()
        val queries = viewModel.queries


        @Tag(THEN)
        assertEquals(true, queries.size == 2)
        assertEquals(queries[0], "123")
        assertEquals(queries[1], "456")
    }

    @Test
    fun testHistoryQueries_giveInitString_whenAddKey_thenGetQueries() {
        @Tag(GIVEN)
        every { AppPref.rawHistoryQueries } returns "123, 456"

        @Tag(WHEN)
        val viewModel = MainViewModel()
        viewModel.recordQuery("789")
        val queries = viewModel.queries

        @Tag(THEN)
        assertEquals(true, queries.size == 3)
        assertEquals(queries[1], "123")
        assertEquals(queries[2], "456")
        assertEquals(queries[0], "789")
    }

    @Test
    fun testHistoryQueries_giveInitString_whenAddSameKey_thenGetQueries() {
        @Tag(GIVEN)
        every { AppPref.rawHistoryQueries } returns "123, 456"

        @Tag(WHEN)
        val viewModel = MainViewModel()
        viewModel.recordQuery("456")
        val queries = viewModel.queries

        @Tag(THEN)
        assertEquals(true, queries.size == 2)
        assertEquals(queries[0], "456")
        assertEquals(queries[1], "123")
    }

    @Test
    fun testHistoryQueries_giveFullInitString_whenAddKey_thenGetQueries() {
        @Tag(GIVEN)
        every { AppPref.rawHistoryQueries } returns "1, 2, 3, 4, 5"

        @Tag(WHEN)
        val viewModel = MainViewModel()
        viewModel.recordQuery("6")
        val queries = viewModel.queries

        @Tag(THEN)
        assertEquals(true, queries.size == 5)
        assertEquals(queries[0], "6")
        assertEquals(queries[1], "1")
        assertEquals(queries[2], "2")
    }
}