package com.vinson.myinterviewtest.feature.main

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.mutableStateListOf
import com.vinson.baseui.ui.util.BaseLoadingComposeViewModel
import com.vinson.datamodel.models.UiConfig
import com.vinson.myinterviewtest.model.SearchImageRepository
import com.vinson.myinterviewtest.model.UiConfigRepository
import com.vinson.myinterviewtest.util.AppPref
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: BaseLoadingComposeViewModel() {

    @VisibleForTesting
    val repository = SearchImageRepository.getInstance()

    @VisibleForTesting
    val uiConfigRepository = UiConfigRepository.getInstance()

    private val _uiConfig = MutableStateFlow(UiConfig.DefaultUiConfig)
    val uiConfig: StateFlow<UiConfig>
        get() = _uiConfig

    private val historyQueries = HistoryQueries(AppPref.rawHistoryQueries)
    val queries = historyQueries.queries

    fun startQuery(key: String) = repository.queryImageResults(key).flow

    fun getUiConfig() {
        sendApi {
            uiConfigRepository.getLayoutConfig().handleResult {
                _uiConfig.emit(it)
            }
        }
    }

    fun recordQuery(key: String) {
        historyQueries.addKey(key)
    }

    override fun onCleared() {
        super.onCleared()
        historyQueries.saveKey()
    }

    class HistoryQueries(initQueries: String) {

        val queries = mutableStateListOf<String>()

        init {
            queries.addAll(initQueries.split(", ").filter { it.isNotEmpty() })
        }

        fun addKey(key: String) {
            val idx = queries.indexOf(key)
            if (idx != -1) {
                queries.removeAt(idx)
                queries.add(0, key)
                return
            }

            queries.add(0, key)
            if (queries.size > 5) {
                queries.removeLast()
            }
        }

        fun saveKey() {
            AppPref.rawHistoryQueries = queries.joinToString(", ")
        }
    }
}