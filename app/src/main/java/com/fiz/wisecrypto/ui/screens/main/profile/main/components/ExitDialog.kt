package com.fiz.wisecrypto.ui.screens.main.profile.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.Red

@Composable
fun ExitDialog(
    openDialog: Boolean,
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit,

    ) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onClickDismiss,
            icon = {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(id = R.drawable.profile_ic_info),
                    contentDescription = null
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.profile_confirm_exit),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                Button(
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red
                    ),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    onClick = onClickConfirm
                ) {
                    Text(
                        text = stringResource(R.string.profile_exit),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            dismissButton = {
                Button(
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(width = 1.dp, color = Red),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    onClick = onClickDismiss
                ) {
                    Text(
                        text = stringResource(R.string.profile_cancel),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Red
                    )
                }
            }
        )
    }
}