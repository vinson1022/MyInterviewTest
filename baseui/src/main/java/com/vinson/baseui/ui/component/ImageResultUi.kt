package com.vinson.baseui.ui.component

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.flowlayout.FlowRow
import com.vinson.baseui.ui.theme.Body
import com.vinson.baseui.ui.theme.BoldCaption
import com.vinson.baseui.ui.theme.Caption
import com.vinson.baseui.ui.theme.Shapes
import com.vinson.baseui.ui.util.rememberPicturePainter
import com.vinson.datamodel.models.ImageResult
import java.lang.Integer.min

@Composable
fun ImageResultRow(
        result: ImageResult,
        canDownload: Boolean,
) {
    val activity = LocalContext.current as Activity

    ConstraintLayout(
            modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, Shapes.medium)
                    .clickable {
                        activity.openWeb(result.pageUrl)
                    },
    ) {
        // Create references for the composables to constrain
        val (picture, info, download) = createRefs()

        Image(
                painter = rememberPicturePainter(result.previewUrl),
                contentDescription = null,
                modifier = Modifier
                        .constrainAs(picture) {
                            height = Dimension.value(60.dp)
                            width = Dimension.value(60.dp)
                            top.linkTo(parent.top, margin = 4.dp)
                            start.linkTo(parent.start, margin = 4.dp)
                            bottom.linkTo(parent.bottom, margin = 4.dp)
                        },
                alignment = Alignment.Center
        )
        Column(
                modifier = Modifier
                        .constrainAs(info) {
                            width = Dimension.fillToConstraints
                            top.linkTo(parent.top, margin = 0.dp)
                            start.linkTo(picture.end, margin = 4.dp)
                            end.linkTo(download.start, margin = 0.dp)
                            bottom.linkTo(parent.bottom, margin = 0.dp)
                        }
        ) {
            UserRow(
                    name = result.userName,
                    avatar = result.userImageUrl,
                    modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 4.dp)
            )
            Text(
                    text = "Downloads: ${result.downloads}",
                    style = Caption,
                    modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
            )
            Text(
                    text = "Likes: ${result.likes}",
                    style = Caption,
                    modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
            )
            if (result.splitTags.isNotEmpty()) {
                val count = min(result.splitTags.size, 3)
                FlowRow(
                        modifier = Modifier
                                .wrapContentSize()
                                .padding(0.dp, 4.dp),
                        mainAxisSpacing = 4.dp,
                        crossAxisSpacing = 4.dp
                ) {
                    result.splitTags.subList(0, count).forEach {
                        Box(
                                modifier = Modifier
                                        .background(Color.Black, Shapes.medium)
                        ) {
                            Text(
                                    text = it,
                                    style = BoldCaption + TextStyle(Color.White),
                                    modifier = Modifier.padding(4.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
        if (canDownload) {
            DarkNormalButton(
                text = "Download",
                onClick = {
                    activity.startDownloadImage(result.largeImageUrl)
                },
                modifier = Modifier
                    .constrainAs(download) {
                        width = Dimension.value(140.dp)
                        top.linkTo(parent.top, margin = 4.dp)
                        start.linkTo(info.end, margin = 4.dp)
                        end.linkTo(parent.end, margin = 4.dp)
                        bottom.linkTo(parent.bottom, margin = 4.dp)
                    },
            )
        }
    }
}

@Composable
fun ImageResultBox(
        result: ImageResult,
        canDownload: Boolean,
) {
    val activity = LocalContext.current as Activity

    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, Shapes.medium)
                    .clickable {
                        activity.openWeb(result.pageUrl)
                    }
    ) {
        Image(
                painter = rememberPicturePainter(result.previewUrl),
                contentDescription = null,
                modifier = Modifier
                        .padding(8.dp)
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally)
        )
        UserRow(
                name = result.userName,
                avatar = result.userImageUrl,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
        )
        Text(
                text = "Downloads: ${result.downloads}",
                style = Body,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
        )
        if (canDownload) {
            DarkNormalButton(
                text = "Download",
                onClick = {
                    activity.startDownloadImage(result.largeImageUrl)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

fun Activity.startDownloadImage(url: String) {
    val uri = Uri.parse(url)
    val manager = getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager
    val request = DownloadManager.Request(uri)
    request.addRequestHeader("Accept", "application/jpg")
    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_DOWNLOADS,
        uri.lastPathSegment ?: System.currentTimeMillis().toString()
    )
    request.setNotificationVisibility(
        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
    )
    request.setTitle("Download from pixabay")

    manager?.enqueue(request)
}

fun Activity.openWeb(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

@Preview
@Composable
fun ImageResultBoxPreview() {
    val result = ImageResult(
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
    ImageResultBox(result, false)
}

@Preview
@Composable
fun ImageResultRowPreview() {
    val result = ImageResult(
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
    ImageResultRow(result, false)
}