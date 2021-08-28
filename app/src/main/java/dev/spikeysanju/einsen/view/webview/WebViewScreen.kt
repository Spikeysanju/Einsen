package dev.spikeysanju.einsen.view.webview

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.myColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun WebViewScreen(
    viewModel: MainViewModel,
    title: String,
    url: String,
    actions: MainActions
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = title,
                style = typography.h6,
                textAlign = TextAlign.Start,
                color = myColors.black,
                modifier = Modifier.padding(start = 16.dp)
            )
        }, navigationIcon = {
            IconButton(onClick = { actions.upPress.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back_button),
                    tint = myColors.black
                )
            }

        }, backgroundColor = myColors.background, elevation = 0.dp)

    }) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            WebPagePreview(url = url)
        }
    }
}


@Composable
fun WebPagePreview(url: String) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return false
                }
            }
            loadUrl(url)
        }
    })
}

