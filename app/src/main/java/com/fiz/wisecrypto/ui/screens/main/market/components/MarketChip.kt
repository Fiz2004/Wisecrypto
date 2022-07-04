package com.fiz.wisecrypto.ui.screens.main.market.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketChip(text: String = "", selected: Boolean, onClick: () -> Unit = {}) {
    ElevatedFilterChip(
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall
            )
        },
        shape = RoundedCornerShape(8.dp),
        selected = selected,
        onClick = onClick,
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.primary,
            selectedBorderColor = MaterialTheme.colorScheme.primary,
            borderWidth = 1.dp
        ),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.primary
        )
    )
}