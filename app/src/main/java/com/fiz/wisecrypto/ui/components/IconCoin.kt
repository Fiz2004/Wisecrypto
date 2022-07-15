package com.fiz.wisecrypto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun IconCoin(iconUrl:String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .clip(shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            modifier = Modifier
                .size(28.dp),
            imageModel = iconUrl,
            contentScale = ContentScale.Crop,
            placeHolder = painterResource(id = R.drawable.placeholder_loading),
            error = painterResource(id = R.drawable.placeholder_error),
            contentDescription = null
        )
    }
}