package com.fiz.wisecrypto.ui.screens.main.home.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi
import com.fiz.wisecrypto.ui.theme.hint
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun YourActiveDetail(active: ActiveUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Ваши активы",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        YourActiveItemDetailInfo(active)
    }
}

@Composable
fun YourActiveItemDetailInfo(active: ActiveUi) {

    val color = if (active.pricePortfolioIncreased)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(32.dp),
                    imageModel = active.icon,
                    contentScale = ContentScale.Crop,
                    placeHolder = painterResource(id = R.drawable.placeholder_loading),
                    error = painterResource(id = R.drawable.placeholder_error),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = active.abbreviated,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = active.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = active.equivalent,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.3.dp,
            color = MaterialTheme.colorScheme.hint
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = active.portfolio,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = active.changeValue,
                style = MaterialTheme.typography.titleSmall,
                color = color
            )
        }
    }
}