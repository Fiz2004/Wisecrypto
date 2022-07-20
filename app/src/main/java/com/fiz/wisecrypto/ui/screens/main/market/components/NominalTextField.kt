package com.fiz.wisecrypto.ui.screens.main.market.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.TextFieldWithHeader

@Composable
fun NominalTextField(
    symbolCurrency: String,
    currencyForBuy: String,
    onValueChange: (String) -> Unit = {}
) {
    TextFieldWithHeader(
        textHeader = stringResource(
            id = R.string.cash_balance_title_nominal,
        ),
        text = stringResource(
            id = R.string.one_currency,
            symbolCurrency,
            currencyForBuy
        ),
        onValueChange = onValueChange,
        textHint = stringResource(
            id = R.string.one_currency,
            symbolCurrency,
            currencyForBuy
        )
    )
}