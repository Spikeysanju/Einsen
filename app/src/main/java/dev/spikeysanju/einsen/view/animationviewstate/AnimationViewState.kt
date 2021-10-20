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
import dev.spikeysanju.einsen.ui.theme.einsenColors
import dev.spikeysanju.einsen.ui.theme.typography

@Composable
fun AnimationViewState(
    title: String,
    description: String,
    callToAction: String,
    screenState: ScreenState,
    actions: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (screenState) {
            ScreenState.ERROR -> {
                LottieAnimationPlaceHolder(
                    title,
                    description,
                    callToAction,
                    actions,
                    R.raw.error_state
                )
            }
            ScreenState.EMPTY -> {
                LottieAnimationPlaceHolder(
                    title,
                    description,
                    callToAction,
                    actions,
                    R.raw.empty_state
                )
            }
            ScreenState.LOADING -> {
                LottieAnimation(lottie = R.raw.loading_state)
            }
        }
    }
}

@Composable
fun LottieAnimationPlaceHolder(
    title: String,
    description: String,
    callToAction: String,
    actions: () -> Unit,
    lottie: Int,
) {

    // lottie animation
    LottieAnimation(lottie)

    // title, description & CTA button
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        style = typography.h6,
        textAlign = TextAlign.Center,
        color = einsenColors.black
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = description,
        modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp),
        style = typography.body2,
        maxLines = 3,
        textAlign = TextAlign.Center,
        color = einsenColors.black.copy(.7F)
    )
    Spacer(modifier = Modifier.height(24.dp))
    Button(
        onClick = { actions() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = einsenColors.black,
            contentColor = einsenColors.white
        )
    ) {
        Text(text = callToAction)
    }
}

@Composable
fun LottieAnimation(lottie: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottie))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    LottieAnimation(
        modifier = Modifier.size(300.dp),
        composition = composition,
        progress = progress
    )
}

enum class ScreenState {
    ERROR,
    EMPTY,
    LOADING
}
