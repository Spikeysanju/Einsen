package dev.spikeysanju.einsen.view.about

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.components.TopBarWithBack
import dev.spikeysanju.einsen.navigation.MainActions
import dev.spikeysanju.einsen.ui.theme.bg
import dev.spikeysanju.einsen.ui.theme.myColors
import dev.spikeysanju.einsen.ui.theme.typography
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

@Composable
fun AboutScreen(viewModel: MainViewModel, actions: MainActions) {
    Scaffold(
        topBar = {
            TopBarWithBack(title = stringResource(id = R.string.text_about)) {
                actions.upPress
            }
        }
    ) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState, contentPadding = PaddingValues(16.dp)) {

            item {
                Canvas(
                    modifier = Modifier.size(130.dp),
                    onDraw = {
                        drawCircle(
                            brush = Brush.linearGradient(
                                colors = listOf(bg, bg)
                            ),
                            radius = 30F, style = Fill
                        )
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                TitleAndDescription(
                    title = stringResource(R.string.text_attribution_and_license),
                    description = stringResource(
                        R.string.text_license
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                TitleAndURL(
                    title = stringResource(R.string.text_visit),
                    url = stringResource(
                        R.string.text_repo_link
                    )
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
            color = myColors.text,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
            Text(text = description, style = typography.subtitle2, color = myColors.text)
        }
    }
}

@Composable
fun TitleAndURL(title: String, url: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = title,
            style = typography.subtitle1,
            color = myColors.text,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.disabled)) {
            ClickableText(
                text = AnnotatedString(
                    text = url, spanStyle = SpanStyle(
                        color = myColors.calm, fontFamily = FontFamily(
                            Font(R.font.avenir_medium, FontWeight.Medium)
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                ),
                onClick = {
                    // todo open repo in webview
                }
            )
        }
    }
}
