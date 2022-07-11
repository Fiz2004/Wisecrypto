package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R

@Composable
fun MainColumnWithoutBottomBar(
    textToolbar: String,
    onClickBackButton: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {
        TopSpacer()
        Toolbar(
            title = textToolbar,
            onClickBackButton = onClickBackButton
        )
        Spacer(modifier = Modifier.height(16.dp))
        content()
        NavigationBar()
    }
}

@Composable
fun MainColumnWithBottomBar(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {
        TopSpacer()
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}

@Composable
fun TopSpacer() {
    Spacer(
        modifier = Modifier
            .windowInsetsTopHeight(WindowInsets.statusBars)
    )
}

@Composable
fun Toolbar(
    title: String,
    onClickBackButton: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickBackButton() },
            painter = painterResource(id = R.drawable.notification_ic_arrow_back),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}