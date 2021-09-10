package dev.spikeysanju.einsen.view.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AboutScreen(viewModel: MainViewModel, actions: MainActions) {

    // remember current URL
    var url by remember {
        mutableStateOf("")
    }
    var title by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_about),
                        style = typography.h6,
                        textAlign = TextAlign.Start,
                        color = einsenColors.black,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { actions.upPress.invoke() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back_button),
                            tint = einsenColors.black
                        )
                    }
                },
                backgroundColor = einsenColors.background, elevation = 0.dp
            )
        }

    ) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState, contentPadding = PaddingValues(16.dp)) {

            item {

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.einsen_logo),
                        contentDescription = stringResource(
                            R.string.text_einsen_logo
                        ),
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(44.dp))
                TitleAndDescription(
                    title = stringResource(R.string.text_attribution_and_license),
                    description = stringResource(
                        R.string.text_license
                    )
                )
            }

            item {
                url = stringResource(id = R.string.text_repo_link)
                title = stringResource(id = R.string.text_visit)

                Spacer(modifier = Modifier.height(24.dp))
                TitleAndURL(
                    title = stringResource(R.string.text_visit),
                    url = url,
                    onClick = {
                        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                        actions.gotoWebView(title, encodedUrl)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                TitleAndDescription(
                    title = stringResource(R.string.text_author),
                    description = stringResource(
                        R.string.text_author_name
                    )
                )
            }
        }
    }
}

@Composable
fun TitleAndDescription(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = title,
            style = typography.subtitle1,
            color = einsenColors.black,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
            Text(text = description, style = typography.subtitle2, color = einsenColors.black)
        }
    }
}

@Composable
fun TitleAndURL(title: String, url: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = title,
            style = typography.subtitle1,
            color = einsenColors.black,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
            Text(
                text = AnnotatedString(
                    text = url,
                    spanStyle = SpanStyle(
                        color = einsenColors.calm,
                        fontFamily = FontFamily(
                            Font(R.font.avenir_medium, FontWeight.Medium)
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                ),
                modifier = Modifier.clickable(
                    onClick = {
                        onClick()
                    }
                )
            )
        }
    }
}
