package com.fiz.wisecrypto.ui.screens.main.home.notification.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.domain.models.notification.Notification
import com.fiz.wisecrypto.domain.models.notification.StatusOperation
import com.fiz.wisecrypto.domain.models.notification.StatusPortfolio
import com.fiz.wisecrypto.domain.models.notification.TypeNotification
import com.fiz.wisecrypto.ui.theme.*
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun NotificationsList(notifications: List<Notification>) {
    val height = LocalConfiguration.current.screenHeightDp
    LazyColumn(
        modifier = Modifier.height(height.dp)
    ) {
        notifications.forEach { notification ->
            item {
                NotificationItem(notification)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun NotificationItem(
    notification: Notification
) {

    val icon = when (notification.type) {
        is TypeNotification.Portfolio ->
            when (notification.type.status) {
                StatusPortfolio.Increased -> R.drawable.notification_pic_chart_increase
                StatusPortfolio.Decreased -> R.drawable.notification_pic_chart_decrease
            }

        is TypeNotification.Balance -> {
            when (notification.type.status) {
                StatusOperation.Success -> R.drawable.notification_ic_ok
                StatusOperation.Process -> R.drawable.notification_ic_time
                StatusOperation.Fail -> R.drawable.notification_ic_cancel
            }
        }
        is TypeNotification.Transaction -> {
            when (notification.type.status) {
                StatusOperation.Success -> R.drawable.notification_ic_ok
                StatusOperation.Process -> R.drawable.notification_ic_time
                StatusOperation.Fail -> R.drawable.notification_ic_cancel
            }
        }
    }

    val color = when (notification.type) {
        is TypeNotification.Portfolio ->
            when (notification.type.status) {
                StatusPortfolio.Increased -> LightGreen2
                StatusPortfolio.Decreased -> LightRed2
            }
        is TypeNotification.Balance -> {
            when (notification.type.status) {
                StatusOperation.Success -> LightGreen2
                StatusOperation.Process -> LightYellow2
                StatusOperation.Fail -> LightRed2
            }
        }
        is TypeNotification.Transaction -> {
            when (notification.type.status) {
                StatusOperation.Success -> LightGreen2
                StatusOperation.Process -> LightYellow2
                StatusOperation.Fail -> LightRed2
            }
        }
    }

    val titleNotification = when (notification.type) {
        is TypeNotification.Portfolio ->
            when (notification.type.status) {
                StatusPortfolio.Increased -> R.string.notification_title_portfolio_increase
                StatusPortfolio.Decreased -> R.string.notification_title_portfolio_decrease
            }

        is TypeNotification.Balance -> {
            when (notification.type.status) {
                StatusOperation.Success -> R.string.notification_title_transaction_success
                StatusOperation.Process -> R.string.notification_title_balance_process
                StatusOperation.Fail -> R.string.notification_title_balance_fail
            }
        }
        is TypeNotification.Transaction -> {
            when (notification.type.status) {
                StatusOperation.Success -> R.string.notification_title_transaction_success
                StatusOperation.Process -> R.string.notification_title_balance_process
                StatusOperation.Fail -> R.string.notification_title_balance_fail
            }
        }
    }

    val textNotification = when (notification.type) {
        is TypeNotification.Portfolio ->
            when (notification.type.status) {
                StatusPortfolio.Increased -> stringResource(
                    R.string.notification_text_portfolio_increase,
                    notification.type.coin,
                    "${notification.type.percent}%"
                )
                StatusPortfolio.Decreased -> stringResource(
                    R.string.notification_text_portfolio_decrease,
                    notification.type.coin,
                    "${notification.type.percent}%"
                )
            }

        is TypeNotification.Balance -> {
            when (notification.type.status) {
                StatusOperation.Success -> stringResource(
                    R.string.notification_text_balance_success,
                    notification.type.value
                )
                StatusOperation.Process -> stringResource(
                    R.string.notification_text_balance_process,
                    notification.type.value
                )
                StatusOperation.Fail -> stringResource(
                    R.string.notification_text_balance_fail,
                    notification.type.value
                )
            }
        }
        is TypeNotification.Transaction -> {
            when (notification.type.status) {
                StatusOperation.Success -> stringResource(
                    R.string.notification_text_transaction_success,
                    notification.type.value,
                    notification.type.coin
                )
                StatusOperation.Process -> stringResource(
                    R.string.notification_text_transaction_success,
                    notification.type.value,
                    notification.type.coin
                )
                StatusOperation.Fail -> stringResource(
                    R.string.notification_text_transaction_success,
                    notification.type.value,
                    notification.type.coin
                )
            }
        }
    }

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
                text = stringResource(id = titleNotification),
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