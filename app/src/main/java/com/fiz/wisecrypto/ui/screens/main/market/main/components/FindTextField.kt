package com.fiz.wisecrypto.ui.screens.main.market.main.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.hint
import com.fiz.wisecrypto.ui.theme.textField
import com.fiz.wisecrypto.ui.theme.veryLightPrimary

@Composable
fun FindTextField(
    text: String,
    onValueChange: (String) -> Unit,
    textHint: String,
) {
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
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = painterResource(id = R.drawable.market_ic_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.veryLightPrimary
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
}