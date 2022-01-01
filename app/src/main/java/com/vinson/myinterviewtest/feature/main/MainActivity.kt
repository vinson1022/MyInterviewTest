package com.vinson.myinterviewtest.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vinson.baseui.ui.component.Dialog
import com.vinson.baseui.ui.component.rememberDialogState
import com.vinson.baseui.ui.theme.Text10
import com.vinson.myinterviewtest.feature.main.ui.MainContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout()
        }
    }
}

@Composable
fun MainLayout() {

    val viewModel = viewModel(MainViewModel::class.java)

    val isLoading by viewModel.isLoading.collectAsState()
    val errorException by viewModel.errorException.collectAsState()

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Text10, darkIcons = true)

    val state = rememberDialogState()
    LaunchedEffect(key1 = errorException) {
        state.isOpen = true
    }

    Surface(
            modifier = Modifier.fillMaxSize()
    ) {
        MainContent(
                viewModel = viewModel
        )
        if (errorException != null) {
            Dialog(
                    title = "Error",
                    description = errorException?.message ?: "",
                    positiveText = "OK",
                    state = state
            )
        }
    }
}