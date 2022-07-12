package com.fiz.wisecrypto.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun WiseCryptoButton(
    text: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {}
) {
    Spacer(modifier = Modifier.height(24.dp))

    Button(
        modifier = modifier
            .height(42.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(4.dp),
        onClick = onClick
    ) {
        Text(
            text = stringResource(text)
        )
    }

    Spacer(modifier = Modifier.height(24.dp))
}