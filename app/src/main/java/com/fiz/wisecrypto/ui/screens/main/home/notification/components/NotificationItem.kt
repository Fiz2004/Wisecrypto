package com.fiz.wisecrypto.ui.screens.main.home.notification.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.home.notification.models.Notification
import com.fiz.wisecrypto.ui.screens.main.home.notification.models.StatusNotification
import com.fiz.wisecrypto.ui.theme.*
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun NotificationItem(
    notification: Notification
) {

    val icon = when (notification.status) {
        StatusNotification.Chart -> R.drawable.notification_ic_chart
        StatusNotification.Success -> R.drawable.notification_ic_ok
        StatusNotification.Process -> R.drawable.notification_ic_time
        StatusNotification.Fail -> R.drawable.notification_ic_cancel
    }

    val color = when (notification.status) {
        StatusNotification.Chart -> LightGreen2
        StatusNotification.Success -> LightGreen2
        StatusNotification.Process -> LightYellow2
        StatusNotification.Fail -> LightRed2
    }

    val titleNotification = notification.textTitle
    val textNotification = notification.textDescription

    val data =
        DateTimeFormatter.ofPattern("dd MMMM yyyy (HH:mm)")
            .format(notification.data)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = color),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = titleNotification,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = textNotification,
                style = MaterialTheme.typography.bodyMedium2,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data,
                style = MaterialTheme.typography.bodyMedium2,
                color = MaterialTheme.colorScheme.hint
            )
        }
    }
}