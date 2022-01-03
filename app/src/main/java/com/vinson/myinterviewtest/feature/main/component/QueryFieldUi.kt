package com.vinson.myinterviewtest.feature.main.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.PopupProperties
import com.vinson.baseui.ui.component.QueryEditText
import com.vinson.baseui.ui.theme.Body

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QueryFieldWithHint(
    modifier: Modifier,
    queryKey: MutableState<String>,
    queries: List<String>,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var expanded by remember { mutableStateOf(false) }
    val queryState = remember { mutableStateOf("") }

    Box(
        modifier = modifier
    ) {
        QueryEditText(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    expanded = it.hasFocus
                },
            onValueChange = {
                expanded = true
            },
            onValueConfirm = {
                queryKey.value = queryState.value
                expanded = false
            },
            onDone = {
                queryKey.value = queryState.value
                expanded = false
                keyboardController?.hide()
            },
            textState = queryState
        )
        DropdownMenu(
            expanded = true,
            onDismissRequest = { },
            properties = PopupProperties(false)
        ) {
            if (expanded && queries.isNotEmpty()) {
                queries.forEach {
                    HintUi(hint = it) {
                        queryState.value = it
                        queryKey.value = it
                        expanded = false
                    }
                }
            }
        }
    }
}

@Composable
fun HintUi(
    hint: String,
    clickable: () -> Unit
) {
    DropdownMenuItem(
        onClick = clickable
    ) {
        Text(
            text = hint,
            style = Body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}