package com.fiz.wisecrypto.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun Title(text:Int,vararg arg:String) {
    Row {
        Text(
            text = stringResource(text,*arg),
            style = MaterialTheme.typography.titleMedium
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}