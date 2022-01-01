package com.vinson.myinterviewtest.feature.main

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.karumi.shot.ScreenshotTest
import com.vinson.baseui.ui.component.ImageResultBox
import com.vinson.baseui.ui.component.ImageResultRow
import com.vinson.datamodel.models.ImageResult
import com.vinson.myinterviewtest.feature.main.ui.MainContent
import org.junit.Rule
import org.junit.Test

class MainUiTest : ScreenshotTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainScreen() {
        composeRule.setContent {
            MainLayout()
        }

        compareScreenshot(composeRule.onRoot())
    }

    @Test
    fun ImageResultBoxUi() {
        composeRule.setContent {
            ImageResultBox(fakeImageResult, true)
        }

        compareScreenshot(composeRule.onRoot())
    }

    @Test
    fun ImageResultRowUi() {
        composeRule.setContent {
            ImageResultRow(fakeImageResult, true)
        }

        compareScreenshot(composeRule.onRoot())
    }

    val fakeImageResult = ImageResult(
        id = 2675672,
        pageUrl = "https://pixabay.com/photos/annotation-art-artistic-2675672/",
        type = "photo",
        _tags = "annotation, art, artistic",
        previewUrl = "https://cdn.pixabay.com/photo/2017/08/24/07/40/abstract-2675672_150.png",
        width = 150,
        height = 146,
        largeImageUrl = "https://pixabay.com/get/gf660b2fb5b7a84ddf92bd5f4fea7a147bdb6e15e0fe4c852a3cc12efe28152ba31b23558edb437bbcea6e9509250dfe451ec101ec4f5e60d211089ea390e0f2d_1280.png",
        views = 151151,
        downloads = 105261,
        likes = 410,
        userId = 6190330,
        userName = "gorartser",
        userImageUrl = "https://cdn.pixabay.com/user/2017/08/24/22-01-16-223_250x250.jpg"
    )
}