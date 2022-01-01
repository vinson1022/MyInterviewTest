package com.vinson.myinterviewtest.component

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.karumi.shot.ScreenshotTest
import com.vinson.baseui.ui.component.LoadingContent
import com.vinson.myinterviewtest.feature.main.MainActivity
import org.junit.Rule
import org.junit.Test

class LoadingUiTest : ScreenshotTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loadingUiScreen() {
        composeRule.setContent {
            LoadingContent()
        }

        compareScreenshot(composeRule.onRoot())
    }
}