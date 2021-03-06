package com.fiz.wisecrypto.ui.screens.main.profile.list_transactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.market.main.components.FilterRow
import com.fiz.wisecrypto.ui.screens.main.profile.list_transactions.components.TransactionList


@Composable
fun ProfileListTransactionsScreen(
    viewModel: ProfileListTransactionsViewModel = viewModel(),
    moveReturn: () -> Unit,
) {

    val transactionsFilters = listOf(
        stringResource(R.string.list_transactions_all),
        stringResource(R.string.list_transactions_balance),
        stringResource(R.string.list_transactions_buy),
        stringResource(R.string.list_transactions_sell)
    )

    ReactEffect(
        viewModel,
        moveReturn
    )

    val viewState = viewModel.viewState
    MainColumn(
        textToolbar = stringResource(R.string.list_transactions_title),
        onClickBackButton = { viewModel.onEvent(ProfileListTransactionsEvent.BackButtonClicked) }
    ) {
        FilterRow(
            texts = transactionsFilters,
            indexSelected = viewState.selectedChipNumber,
            onClick = { number -> viewModel.onEvent(ProfileListTransactionsEvent.ChipClicked(number)) }
        )
        TransactionList(viewState.transactions)
    }
}

@Composable
private fun ReactEffect(
    viewModel: ProfileListTransactionsViewModel = viewModel(),
    moveReturn: () -> Unit,
) {
    val viewEffect = viewModel.viewEffect
    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                ProfileListTransactionsViewEffect.MoveReturn -> moveReturn()
            }
        }
    }
}



