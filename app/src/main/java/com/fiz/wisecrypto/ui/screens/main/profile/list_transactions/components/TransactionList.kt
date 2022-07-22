package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.domain.models.transaction.Transaction
import com.fiz.wisecrypto.ui.screens.main.components.TransactionItem


@Composable
fun TransactionList(transactions: List<Transaction>) {
    LazyColumn {

        transactions.forEach {
            item {
                TransactionItem(transaction = it)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
}

