package com.fiz.wisecrypto.ui.screens.main.market.detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.theme.hint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodChip(
    modifier: Modifier = Modifier,
    text: String = "", selected: Boolean, onClick: () -> Unit = {}
) {
    FilterChip(
        modifier = modifier,
        label = {
            Text(
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = text,
                style = MaterialTheme.typography.labelSmall
            )
        },
        border = null,
        shape = RoundedCornerShape(8.dp),
        selected = selected,
        onClick = onClick,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.hint
        )
    )
}