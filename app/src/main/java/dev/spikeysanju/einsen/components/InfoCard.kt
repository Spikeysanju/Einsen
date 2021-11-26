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

package dev.spikeysanju.einsen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.ui.theme.apptheme.AppTheme

/**
 * This component helps to show Importance & Priority in CardView.
 * @param title
 * @param value
 * @param modifier
 */
@Composable
fun InfoCard(title: String, value: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = AppTheme.colors.card)
            .padding(
                AppTheme.dimensions.paddingLarge
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentWidth()
        ) {

            // concat string: Result : 4.5/5.0
            val builder = StringBuilder()
            builder.append(value)
                .append("/")
                .append(stringResource(R.string.text_four))

            Text(
                text = builder.toString(),
                modifier = Modifier.fillMaxWidth(),
                color = AppTheme.colors.text,
                style = AppTheme.typography.subtitle,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingSmall))

            Text(
                text = title.uppercase(),
                modifier = Modifier.fillMaxWidth(),
                color = AppTheme.colors.text.copy(.7f),
                style = AppTheme.typography.overline,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(name = "Info card", group = "Card")
@Composable
fun InfoCardPreview() {
    Column {
        InfoCard(title = "Urgency", value = "2", modifier = Modifier)
        Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingMedium))
        InfoCard(title = "Importance", value = "2", modifier = Modifier)
    }
}
