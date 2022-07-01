package com.fiz.wisecrypto.ui.screens.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.main.components.TopSpacer
import com.fiz.wisecrypto.ui.screens.main.home.main.HomeViewModel
import com.fiz.wisecrypto.ui.screens.main.profile.components.BalanceInfo
import com.fiz.wisecrypto.ui.screens.main.profile.components.UserInfoLarge
import com.fiz.wisecrypto.ui.theme.*

@Composable
fun ProfileScreen(
    MainViewModel: MainViewModel = viewModel(),
    viewModel: HomeViewModel = viewModel()
) {

    val context = LocalContext.current

    val viewState = viewModel.viewState
    val mainViewState = MainViewModel.viewState
    val viewEffect = viewModel.viewEffect

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {

        TopSpacer()

        UserInfoLarge()

        BalanceInfo()

        ProfileMenuItem(
            icon = R.drawable.profile_ic_list_transactions, color = LightGreen2,
            "Список транзакций", "Транзакции, Которые У Вас Есть"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Настройки",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {

            item {
                ProfileMenuItem(
                    icon = R.drawable.profile_ic_privacy, color = LightBlue2,
                    "Конфиденциальность", "Измените адрес электронной почты и пароль"
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileMenuItem(
                    icon = R.drawable.profile_ic_payment, color = LightPurple2,
                    "Платеж", "Обновить настройки оплаты"
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileMenuItem(
                    icon = R.drawable.profile_ic_notification, color = LightYellow2,
                    "Уведомления", "Изменение настроек уведомлений"
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ProfileMenuItem(
                    icon = R.drawable.profile_ic_exit, color = LightRed2,
                    "Выход", "Закройте приложение"
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

//        findTextField(
//            text = viewState.searchText,
//            onValueChange = {
//                viewModel.onEvent(MarketEvent.SearchTextChanged(it))
//            },
//            textHint = textHint
//        )
//        filterRow(viewState.selectedChipNumber, viewModel)
//        coinList(viewState.coins)
    }
}

@Composable
fun ProfileMenuItem(icon: Int, color: Color, title: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
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
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium2,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.drawable.profile_ic_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.hint
            )
        }
    }
}


