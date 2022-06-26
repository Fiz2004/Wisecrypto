package com.fiz.wisecrypto.ui.screens.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.home.components.WatchListItem
import com.fiz.wisecrypto.ui.screens.main.home.components.YourActiveItem
import com.fiz.wisecrypto.ui.theme.hint

@Composable
fun HomeScreen(
    MainViewModel: MainViewModel = viewModel(),
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current

    val viewState = viewModel.viewState
    val mainViewState = MainViewModel.viewState
    val viewEffect = viewModel.viewEffect

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = RoundedCornerShape(20.dp)),
                    painter = painterResource(id = R.drawable.home_pic_avatar_test),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = stringResource(R.string.home_hello),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.hint
                    )
                    Text(
                        text = viewState.fullName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.home_ic_notification),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    painterResource(id = R.drawable.home_pic_total_background),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(112.dp),
                    contentDescription = null
                )
                Row(
                    modifier = Modifier.padding(
                        vertical = 26.dp,
                        horizontal = 24.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.home_total_portfolio),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "$56.98",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.home_ic_up_right),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "15,3%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

        }

        item {
            Row {
                Text(
                    text = stringResource(R.string.home_your_active),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.home_see_all),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    YourActiveItem(
                        icon = R.drawable.home_pic_bitcoin,
                        abbreviated = "BTC",
                        name = "Bitcoin",
                        portfolio = "\$26.46",
                        equivalent = "0.0012 BTC",
                        up = true,
                        value = "15,3%"
                    )
                }

                item {
                    YourActiveItem(
                        icon = R.drawable.home_pic_etherium,
                        abbreviated = "ETH",
                        name = "Etherium",
                        portfolio = "\$37.30",
                        equivalent = "0.009 BTC",
                        up = false,
                        value = "-2,1%"
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text(
                    text = stringResource(R.string.home_watchlist),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

        }

        item {
            WatchListItem(
                icon = R.drawable.home_pic_bitcoin,
                abbreviated = "BTC/BUSD",
                name = "Bitcoin",
                cost = "\$54,382.64",
                up = true,
                value = "15,3%"
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            WatchListItem(
                icon = R.drawable.home_pic_etherium,
                abbreviated = "ETH/BUSD",
                name = "Etherium",
                cost = "\$4,145.61",
                up = false,
                value = "-2,1%"
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            WatchListItem(
                icon = R.drawable.home_pic_litecoin,
                abbreviated = "LTC/BUSD",
                name = "Litecoin",
                cost = "\$207.3",
                up = false,
                value = "-1,1%"
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


