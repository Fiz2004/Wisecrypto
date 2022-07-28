package com.fiz.wisecrypto.ui.screens.main.navigate.graphs

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.res.stringArrayResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.market.add_balance.MarketAddBalanceScreen
import com.fiz.wisecrypto.ui.screens.main.market.add_balance.MarketAddBalanceViewModel
import com.fiz.wisecrypto.ui.screens.main.market.buy.MarketBuyScreen
import com.fiz.wisecrypto.ui.screens.main.market.buy.MarketBuyViewModel
import com.fiz.wisecrypto.ui.screens.main.market.cash_balance.MarketCashBalanceScreen
import com.fiz.wisecrypto.ui.screens.main.market.cash_balance.MarketCashBalanceViewModel
import com.fiz.wisecrypto.ui.screens.main.market.detail.MarketDetailScreen
import com.fiz.wisecrypto.ui.screens.main.market.detail.MarketDetailViewModel
import com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_add.MarketDetailTransactionAddScreen
import com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_add.MarketDetailTransactionAddViewModel
import com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_cash.MarketDetailTransactionCashScreen
import com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_cash.MarketDetailTransactionCashViewModel
import com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_sell.MarketDetailTransactionSellScreen
import com.fiz.wisecrypto.ui.screens.main.market.detail_transaction_sell.MarketDetailTransactionSellViewModel
import com.fiz.wisecrypto.ui.screens.main.market.main.MarketMainScreen
import com.fiz.wisecrypto.ui.screens.main.market.main.MarketViewModel
import com.fiz.wisecrypto.ui.screens.main.market.sell.MarketSellScreen
import com.fiz.wisecrypto.ui.screens.main.market.sell.MarketSellViewModel
import com.fiz.wisecrypto.ui.screens.main.navigate.names.NamesMainScreen
import com.fiz.wisecrypto.ui.screens.main.navigate.names.NamesMarketScreen

fun NavGraphBuilder.marketGraph(navController: NavHostController, state: LazyListState) {
    navigation(
        startDestination = NamesMarketScreen.MarketMain.name,
        route = NamesMainScreen.Market.name
    ) {

        composable(NamesMarketScreen.MarketMain.name) {
            val viewModel = hiltViewModel<MarketViewModel>()

            MarketMainScreen(
                viewModel,
                state = state,
                moveMarketDetailScreen = { id -> navController.navigate(NamesMarketScreen.Detail.name + "/$id") }
            )
        }

        composable(NamesMarketScreen.Detail.name + "/{id}") {
            val viewModel = hiltViewModel<MarketDetailViewModel>()
            val id = it.arguments?.getString("id") ?: return@composable
            viewModel.idCoin = id
            viewModel.monthNames = stringArrayResource(id = R.array.month_3chars).toList()
            viewModel.daysNames = stringArrayResource(id = R.array.day_of_week_2chars).toList()

            MarketDetailScreen(
                viewModel,
                moveReturnScreen = { navController.popBackStack() },
                moveMarketSellScreen = { navController.navigate(NamesMarketScreen.Sell.name + "/$id") },
                moveMarketBuyScreen = { navController.navigate(NamesMarketScreen.Buy.name + "/$id") }
            )
        }

        composable(NamesMarketScreen.Sell.name + "/{id}") {
            val viewModel = hiltViewModel<MarketSellViewModel>()
            val id = it.arguments?.getString("id") ?: return@composable
            viewModel.idCoin = id

            MarketSellScreen(
                viewModel,
                moveReturn = { navController.popBackStack() },
                moveMarketDetailTransactionSellScreen = { idCoin, userCoinForSell, priceCurrency ->
                    navController.navigate(
                        NamesMarketScreen.DetailTransactionSell.name + "/$idCoin" + "/$userCoinForSell" + "/$priceCurrency"
                    )
                },
            )
        }

        composable(NamesMarketScreen.DetailTransactionSell.name + "/{idCoin}" + "/{userCoinForSell}" + "/{priceCurrency}") {
            val viewModel = hiltViewModel<MarketDetailTransactionSellViewModel>()
            val idCoin = it.arguments?.getString("idCoin") ?: return@composable
            val userCoinForSell =
                it.arguments?.getString("userCoinForSell")?.toDouble() ?: return@composable
            val priceCurrency =
                it.arguments?.getString("priceCurrency")?.toDouble() ?: return@composable

            MarketDetailTransactionSellScreen(
                viewModel,
                idCoin,
                userCoinForSell,
                priceCurrency,
                moveReturn = { navController.popBackStack() },
            )
        }

        composable(NamesMarketScreen.Buy.name + "/{id}") {
            val viewModel = hiltViewModel<MarketBuyViewModel>()
            val id = it.arguments?.getString("id") ?: return@composable
            viewModel.idCoin = id

            MarketBuyScreen(
                viewModel,
                moveReturn = { navController.popBackStack() },
                moveMarketAddBalanceScreen = { navController.navigate(NamesMarketScreen.AddBalance.name) }
            )
        }

        composable(NamesMarketScreen.AddBalance.name) {
            val viewModel = hiltViewModel<MarketAddBalanceViewModel>()

            MarketAddBalanceScreen(
                viewModel,
                moveReturn = { navController.popBackStack() },
                moveMarketDetailTransactionAddScreen = { currency, commission ->
                    navController.navigate(
                        NamesMarketScreen.DetailTransactionAdd.name + "/$currency" + "/$commission"
                    )
                },
            )
        }

        composable(NamesMarketScreen.DetailTransactionAdd.name + "/{currency}" + "/{commission}") {
            val viewModel = hiltViewModel<MarketDetailTransactionAddViewModel>()
            val currency = it.arguments?.getString("currency")?.toDouble() ?: return@composable
            val commission = it.arguments?.getString("commission")?.toDouble() ?: return@composable

            MarketDetailTransactionAddScreen(
                viewModel,
                currency,
                commission,
                moveReturn = { navController.popBackStack() },
            )
        }

        composable(NamesMarketScreen.CashBalance.name) {
            val viewModel = hiltViewModel<MarketCashBalanceViewModel>()

            MarketCashBalanceScreen(
                viewModel,
                moveReturn = { navController.popBackStack() },
                moveMarketDetailTransactionCashScreen = { currency, commission ->
                    navController.navigate(
                        NamesMarketScreen.DetailTransactionCash.name + "/$currency" + "/$commission"
                    )
                },
            )
        }

        composable(NamesMarketScreen.DetailTransactionCash.name + "/{currency}" + "/{commission}") {
            val viewModel = hiltViewModel<MarketDetailTransactionCashViewModel>()
            val currency = it.arguments?.getString("currency")?.toDouble() ?: return@composable
            val commission = it.arguments?.getString("commission")?.toDouble() ?: return@composable

            MarketDetailTransactionCashScreen(
                viewModel,
                currency,
                commission,
                moveReturn = { navController.popBackStack() },
            )
        }

    }
}