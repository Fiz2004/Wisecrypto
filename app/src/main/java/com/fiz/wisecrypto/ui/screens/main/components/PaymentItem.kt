package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.screens.main.profile.payment.models.Payment
import com.fiz.wisecrypto.ui.theme.hint

@Composable
fun PaymentItem(
    payment: Payment,
    border: Boolean = false,
    iconId: Int = 0,
    colorIcon: Color = Color.White,
    actionOnClick: () -> Unit = {},
) {
    var modifier = Modifier
        .fillMaxWidth()
        .background(
            color = MaterialTheme.colorScheme.onPrimary,
            shape = RoundedCornerShape(10.dp)
        )

    if (border)
        modifier = modifier.border(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.hint,
            shape = RoundedCornerShape(10.dp)
        )

    Row(
        modifier = modifier
            .padding(vertical = 12.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .size(48.dp),
            painter = painterResource(id = payment.icon),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = payment.name,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = payment.number,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (iconId != 0)
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { actionOnClick() },
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = colorIcon
            )
    }
}