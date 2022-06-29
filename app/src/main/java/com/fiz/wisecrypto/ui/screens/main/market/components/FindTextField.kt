package com.fiz.wisecrypto.ui.screens.main.market.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyScopeMarker
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.hint
import com.fiz.wisecrypto.ui.theme.textField
import com.fiz.wisecrypto.ui.theme.veryLightPrimary

@LazyScopeMarker
fun LazyListScope.findTextField(
    text: String,
    onValueChange: (String) -> Unit,
    textHint: String,
) {
    item {
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
        Spacer(modifier = Modifier.height(16.dp))
    }
}