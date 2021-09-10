package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
fun EmptyScreen(actions: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // lottie animation
        EmptyStateAnimator()

        // title, description & CTA button
        Text(
            text = stringResource(R.string.text_no_task_title),
            modifier = Modifier.fillMaxWidth(),
            style = typography.h6,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.text_no_task_description),
            modifier = Modifier.fillMaxWidth(),
            style = typography.body2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary.copy(.7f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { actions() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = einsenColors.black,
                contentColor = einsenColors.white
            )
        ) {
            Text(text = stringResource(R.string.text_add_a_task))
        }
    }
}

@Composable
fun EmptyStateAnimator() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_state))
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
