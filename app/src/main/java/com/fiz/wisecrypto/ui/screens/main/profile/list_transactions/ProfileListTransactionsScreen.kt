package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.Toolbar
import com.fiz.wisecrypto.ui.screens.main.market.components.FilterRow
import com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.components.TransactionItem


@Composable
fun ProfileListTransactionsScreen(
    viewModel: ProfileListTransactionsViewModel = viewModel(),
    moveReturn: () -> Unit,
) {

    val context = LocalContext.current

    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    val transactionsFilters = listOf(
        stringResource(R.string.list_transactions_all),
        stringResource(R.string.list_transactions_balance),
        stringResource(R.string.list_transactions_buy),
        stringResource(R.string.list_transactions_sell)
    )

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                ProfileListTransactionsViewEffect.MoveReturn -> {
                    moveReturn()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(
            modifier = Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
        )

        Toolbar(
            title = stringResource(R.string.list_transactions_title),
            onClickBackButton = { viewModel.onEvent(ProfileListTransactionsEvent.BackButtonClicked) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        FilterRow(
            texts = transactionsFilters,
            indexSelected = viewState.selectedChipNumber,
            onClick = { number -> viewModel.onEvent(ProfileListTransactionsEvent.ChipClicked(number)) }
        )

        LazyColumn {

            viewState.transactions.forEach {
                item {
                    TransactionItem(transaction = it)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }

        Spacer(
            modifier = Modifier
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
        )
    }
}


