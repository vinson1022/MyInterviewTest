package com.vinson.myinterviewtest.feature.main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.vinson.baseui.ui.component.*
import com.vinson.baseui.ui.theme.BoldTitle
import com.vinson.baseui.ui.theme.Text10
import com.vinson.datamodel.models.ImageResult
import com.vinson.datamodel.models.UiConfig
import com.vinson.myinterviewtest.feature.main.MainViewModel
import com.vinson.myinterviewtest.feature.main.component.QueryFieldWithHint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@Composable
fun MainContent(
    viewModel: MainViewModel
) {
    val confirmQueryKey = remember { mutableStateOf("") }
    var resultFlow by remember { mutableStateOf<Flow<PagingData<ImageResult>>>(flowOf()) }

    val uiConfig by viewModel.uiConfig.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getUiConfig()
    }

    LaunchedEffect(key1 = confirmQueryKey.value) {
        if (confirmQueryKey.value.isNotEmpty()) {
            viewModel.recordQuery(confirmQueryKey.value)
            resultFlow = viewModel.startQuery(confirmQueryKey.value)
        } else {
            resultFlow = flowOf()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Text10)
    ) {
        QueryFieldWithHint(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            queryKey = confirmQueryKey,
            queries = viewModel.queries
        )
        QueryResultsUi(uiConfig, resultFlow)
    }
}

@Composable
fun QueryResultsUi(
    uiConfig: UiConfig,
    resultFlow: Flow<PagingData<ImageResult>>
) {
    val results = resultFlow.collectAsLazyPagingItems()

    if (results.itemCount == 0) {
        EmptyResultUi()
    } else {
        StateUi(results)
        ImageResultsUi(uiConfig, results)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageResultsUi(
    uiConfig: UiConfig,
    results: LazyPagingItems<ImageResult>
) {
    var style by remember { mutableStateOf(LayoutStyle.fromValue(uiConfig.style)) }
    val switchRowConnection = object : SwitchRowConnection {

        override fun getTitle(): String = when (style) {
            LayoutStyle.Linear -> "Linear Layout"
            LayoutStyle.Grid -> "Grid Layout"
        }

        override fun getCheckState() = when (style) {
            LayoutStyle.Linear -> true
            LayoutStyle.Grid -> false
        }

        override fun switchedAction(checkState: Boolean) {
            style = if (checkState) LayoutStyle.Linear else LayoutStyle.Grid
        }
    }
    val switchRowState = rememberSwitchRowState(connection = switchRowConnection)
    val scrollState = rememberLazyListState()

    if (uiConfig.canDownload) {
        RequiresWriteExternalStoragePermission()
    }

    SwitchRow(
        state = switchRowState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
    when (style) {
        LayoutStyle.Linear -> {
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(results) {
                    it ?: return@items
                    ImageResultRow(result = it, uiConfig.canDownload)
                }
            }
        }
        LayoutStyle.Grid -> {
            LazyVerticalGrid(
                state = scrollState,
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 4.dp,
                    top = 16.dp,
                    end = 4.dp,
                    bottom = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(results.itemCount) { index ->
                        val imageResult = results[index] ?: return@items
                        ImageResultBox(imageResult, uiConfig.canDownload)
                    }
                },
            )
        }
    }

}

@Composable
fun EmptyResultUi() {
    Text(
        text = "This is empty state!\nYou can start searching!",
        style = BoldTitle,
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun StateUi(
    results: LazyPagingItems<ImageResult>
) {
    with(results.loadState) {
        when {
            refresh is LoadState.Loading ||
                    append is LoadState.Loading -> {
                LoadingView(modifier = Modifier.fillMaxSize())
            }
            refresh is LoadState.Error -> {
                val e = refresh as LoadState.Error
                ErrorItem(
                    message = e.error.localizedMessage!!,
                    modifier = Modifier.fillMaxSize(),
                    onClickRetry = { results.retry() }
                )
            }
            append is LoadState.Error -> {
                val e = append as LoadState.Error
                ErrorItem(
                    message = e.error.localizedMessage!!,
                    onClickRetry = { results.retry() }
                )
            }
        }
    }
}

enum class LayoutStyle {
    Linear, Grid;

    companion object {
        fun fromValue(value: String) = when (value) {
            "grid" -> Grid
            else -> Linear
        }
    }
}