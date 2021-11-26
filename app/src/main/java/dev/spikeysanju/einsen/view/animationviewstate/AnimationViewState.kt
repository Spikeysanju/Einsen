/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package dev.spikeysanju.einsen.view.animationviewstate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.ui.theme.apptheme.AppTheme

@Composable
fun AnimationViewState(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    callToAction: String,
    screenState: ScreenState,
    actions: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (screenState) {
            ScreenState.ERROR -> {
                LottieAnimationPlaceHolder(
                    modifier,
                    title,
                    description,
                    callToAction,
                    actions,
                    R.raw.error_state
                )
            }
            ScreenState.EMPTY -> {
                LottieAnimationPlaceHolder(
                    modifier,
                    title,
                    description,
                    callToAction,
                    actions,
                    R.raw.empty_state
                )
            }
            ScreenState.LOADING -> {
                LottieAnimation(modifier, lottie = R.raw.loading_state)
            }
        }
    }
}

@Composable
fun LottieAnimationPlaceHolder(
    modifier: Modifier,
    title: String,
    description: String,
    callToAction: String,
    actions: () -> Unit,
    lottie: Int,
) {

    // lottie animation
    LottieAnimation(modifier, lottie)

    // title, description & CTA button
    Text(
        text = title,
        modifier = modifier.fillMaxWidth(),
        style = AppTheme.typography.h2,
        textAlign = TextAlign.Center,
        color = AppTheme.colors.text
    )
    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingMedium))
    Text(
        text = description,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = AppTheme.dimensions.paddingExtraLarge,
                end = AppTheme.dimensions.paddingExtraLarge
            ),
        style = AppTheme.typography.body,
        maxLines = 3,
        textAlign = TextAlign.Center,
        color = AppTheme.colors.text.copy(.7F)
    )
    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingExtraLarge))
    Button(
        onClick = { actions() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.white
        )
    ) {
        Text(text = callToAction, style = AppTheme.typography.button)
    }
}

@Composable
fun LottieAnimation(modifier: Modifier, lottie: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottie))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    LottieAnimation(
        modifier = modifier.size(300.dp),
        composition = composition,
        progress = progress
    )
}

enum class ScreenState {
    ERROR,
    EMPTY,
    LOADING
}
