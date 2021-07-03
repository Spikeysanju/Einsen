package dev.spikeysanju.einsen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.spikeysanju.einsen.R

@Composable
fun BottomCTA(
    buttonState: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onShare: () -> Unit,
    onButtonChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ActionIcons(onEdit = { onEdit() }, onDelete = { onDelete() }, onShare = { onShare() })
        Spacer(modifier = Modifier.width(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), Arrangement.End) {

            // check if task is already completed
            val buttonTitle = when (buttonState) {
                true -> stringResource(R.string.text_incomplete)
                false -> stringResource(R.string.text_complete)
            }

            PrimaryButtonWithIcon(buttonTitle, onclick = {
                onButtonChange(!buttonState)
            })
        }
    }
}

@Composable
fun ActionIcons(onEdit: () -> Unit, onDelete: () -> Unit, onShare: () -> Unit) {
    Row(modifier = Modifier.wrapContentWidth(), horizontalArrangement = Arrangement.SpaceAround) {

        IconButton(onClick = { onEdit.invoke() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(R.string.text_edit_button),
                tint = MaterialTheme.colors.onPrimary
            )
        }

        IconButton(onClick = { onDelete.invoke() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = stringResource(R.string.text_delete_button),
                tint = MaterialTheme.colors.onPrimary
            )
        }

        IconButton(onClick = { onShare.invoke() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = stringResource(R.string.text_share_button),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}