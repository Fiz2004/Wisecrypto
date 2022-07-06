package com.fiz.wisecrypto.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.hint
import com.fiz.wisecrypto.ui.theme.textField
import com.fiz.wisecrypto.ui.theme.titleMedium2
import com.fiz.wisecrypto.ui.theme.veryLightPrimary

@Composable
fun TextFieldWithHeader(
    textHeader: String,
    text: String,
    onValueChange: (String) -> Unit,
    textHint: String,
    password: Boolean = false,
) {
    var transform by remember { mutableStateOf(true) }

    Spacer(modifier = Modifier.height(16.dp))

    HeaderTextField(textHeader)

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.textField,
        placeholder = {
            Text(
                text = textHint,
                style = MaterialTheme.typography.textField,
                color = MaterialTheme.colorScheme.hint
            )
        },
        trailingIcon = {
            if (password)
                IconButton(onClick = { transform = !transform }) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp),
                        painter = painterResource(id = R.drawable.components_password_show),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
        },
        visualTransformation = if (password)
            if (transform)
                PasswordVisualTransformation()
            else
                VisualTransformation.None
        else
            VisualTransformation.None,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.veryLightPrimary
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
        style = MaterialTheme.typography.titleMedium2
    )
}