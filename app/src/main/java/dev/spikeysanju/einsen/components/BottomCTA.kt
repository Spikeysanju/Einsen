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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R
import dev.spikeysanju.einsen.ui.theme.apptheme.AppTheme

/**
 * This component helps to perform action for each task like Edit, Delete & Share task of this app.
 * @param title
 * @param icon
 * @param color
 * @param onEdit
 * @param onDelete
 * @param onShare
 * @param onButtonChange
 */
@Composable
fun BottomCTA(
    modifier: Modifier = Modifier,
    title: String,
    icon: Painter,
    color: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onShare: () -> Unit,
    onButtonChange: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(AppTheme.colors.card),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier.padding(
                top = AppTheme.dimensions.paddingLarge,
                bottom = AppTheme.dimensions.paddingLarge,
                start = AppTheme.dimensions.paddingXL,
                end = AppTheme.dimensions.paddingXL
            ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionIcons(onEdit = { onEdit() }, onDelete = { onDelete() }, onShare = { onShare() })
            Spacer(modifier = modifier.width(AppTheme.dimensions.paddingLarge))

            Row(modifier = modifier.fillMaxWidth(), Arrangement.End) {
                PrimaryButtonWithIcon(
                    title = title,
                    onclick = {
                        onButtonChange()
                    },
                    icon = icon, color = color
                )
            }
        }
    }
}

/**
 * This component helps to perform action for each task like Edit, Delete & Share task of this app.
 * @param onEdit
 * @param onDelete
 * @param onShare
 */
@Composable
fun ActionIcons(
    modifier: Modifier = Modifier,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onShare: () -> Unit
) {
    Row(modifier = modifier.wrapContentWidth(), horizontalArrangement = Arrangement.SpaceAround) {

        IconButton(onClick = { onEdit.invoke() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(R.string.text_edit_button),
                tint = AppTheme.colors.primary
            )
        }

        IconButton(onClick = { onDelete.invoke() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = stringResource(R.string.text_delete_button),
                tint = AppTheme.colors.primary
            )
        }

        IconButton(onClick = { onShare.invoke() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = stringResource(R.string.text_share_button),
                tint = AppTheme.colors.primary
            )
        }
    }
}

@Preview(name = "Bottom CTA", group = "Button")
@Composable
fun BottomCTA() {

    Column {

        BottomCTA(
            title = "Complete",
            icon = painterResource(id = R.drawable.ic_check),
            color = AppTheme.colors.black,
            onEdit = { },
            onDelete = { },
            onShare = { }
        ) {
        }

        Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingMedium))

        ActionIcons(
            onEdit = {
                // on edit action goes here
            },
            onDelete = {
                // on delete action goes here
            }
        ) {
            // on share action goes here
        }
    }
}
