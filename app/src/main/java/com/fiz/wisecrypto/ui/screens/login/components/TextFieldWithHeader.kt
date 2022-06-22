package com.fiz.wisecrypto.ui.screens.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.theme.veryLightPrimary

@Composable
fun TextFieldWithHeader(
    textHeader: String,
    text: String,
    onValueChange: (String) -> Unit,
    textHint: String,
    isEmail: Boolean = false
) {
    val colorsBorders = if (isEmail)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.veryLightPrimary


    Spacer(modifier = Modifier.height(16.dp))

    HeaderTextField(textHeader)

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = textHint,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorsBorders,
            unfocusedBorderColor = colorsBorders
        )
    )
}

@Composable
fun HeaderTextField(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleLarge
    )
}