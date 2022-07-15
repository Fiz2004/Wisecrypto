package com.fiz.wisecrypto.ui.screens.main.market.detail.components

import android.graphics.DashPathEffect
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.domain.models.History
import com.fiz.wisecrypto.ui.screens.main.home.components.BigRelativeLabel
import com.fiz.wisecrypto.ui.screens.main.market.detail.MarketDetailViewModel
import com.fiz.wisecrypto.ui.theme.hint
import com.fiz.wisecrypto.ui.theme.titleMedium3

@Composable
fun Statistics(
    viewModel: MarketDetailViewModel,
    priceForOne: String,
    symbol: String,
    increased: Boolean,
    changePercentagePricePortfolio: String,
    indexPeriod: Int,
    valueCurrentPriceHistory: List<History> = emptyList(),
    labels: List<String> = emptyList(),
    onClickPeriod: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        TitleStatistics(priceForOne, symbol, increased, changePercentagePricePortfolio)
        Spacer(modifier = Modifier.height(16.dp))
        Chart(viewModel, valueCurrentPriceHistory, labels)
        Spacer(modifier = Modifier.height(16.dp))
        FilterPeriod(indexPeriod, onClickPeriod)
    }
}

@Composable
private fun TitleStatistics(
    priceForOne: String, symbol: String, increased: Boolean, changePercentagePricePortfolio: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = priceForOne,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "/ 1 ${symbol.uppercase()}",
                style = MaterialTheme.typography.titleMedium3,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BigRelativeLabel(
            inverse = false, increased = increased, value = changePercentagePricePortfolio
        )
    }
}

@Composable
private fun Chart(
    viewModel: MarketDetailViewModel,
    valueCurrentPriceHistory: List<History> = emptyList(),
    labels: List<String> = emptyList(),
    countLine: Int = 7,
    usedLine: Int = 5,
    graphColor: Color = Color(0x99_00_CB_6A),
    transparentGraphColor: Color = graphColor.copy(alpha = 0.1f),
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    lineColor: Color = MaterialTheme.colorScheme.hint,
    maxColor: Color = MaterialTheme.colorScheme.tertiary
) {
    if (valueCurrentPriceHistory.isEmpty()) return

    val density = LocalDensity.current

    val textPaint = remember(density) {
        Paint().apply {
            color = textColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    val linePaint = remember(density) {
        Paint().apply {
            color = lineColor.toArgb()
            pathEffect =
                DashPathEffect(floatArrayOf(8f * density.density, 8f * density.density), 0f)
        }
    }

    // Отступ снизу в пикселях для точки начала рисования графика
    val spacingY = 23f * density.density

    // Точка в пикселях по горизонтали где получается максимальное значение, вычисляется позже
    var xMax by remember { mutableStateOf(-1f) }
    // Точка в пикселях по вертикали где получается максимальное значение, вычисляется позже
    var yMax by remember { mutableStateOf(-1f) }

    val upperValue = remember(valueCurrentPriceHistory) {
        (valueCurrentPriceHistory.maxOfOrNull { it.value }) ?: 0.0
    }
    val upperIndex = remember(valueCurrentPriceHistory) {
        valueCurrentPriceHistory.indexOfFirst { it.value == upperValue }
    }

    val lowerValue = remember(valueCurrentPriceHistory) {
        valueCurrentPriceHistory.minOfOrNull { it.value } ?: 0.0
    }

    Box {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(191.dp)
        ) {
            printLabels(labels, density, textPaint)

            val stepY = (size.height - spacingY) / countLine.toFloat()
            printDottedLine(stepY, countLine, linePaint)

            var lastX = 0f
            val spaceOneStepX = size.width / valueCurrentPriceHistory.size

            val strokePath = Path().apply {
                val height = usedLine * stepY
                for (i in valueCurrentPriceHistory.indices) {
                    val info = valueCurrentPriceHistory[i]
                    val nextInfo =
                        valueCurrentPriceHistory.getOrNull(i + 1) ?: valueCurrentPriceHistory.last()
                    val leftRatio = (info.value - lowerValue) / (upperValue - lowerValue)
                    val rightRatio = (nextInfo.value - lowerValue) / (upperValue - lowerValue)

                    val x1 = i * spaceOneStepX
                    val y1 = size.height - stepY - spacingY - (leftRatio * height).toFloat()
                    val x2 = (i + 1) * spaceOneStepX
                    val y2 = size.height - stepY - spacingY - (rightRatio * height).toFloat()
                    if (i == 0) {
                        moveTo(x1, y1)
                    }
                    lastX = (x1 + x2) / 2f
                    quadraticBezierTo(
                        x1, y1, lastX, (y1 + y2) / 2f
                    )
                }
            }
            val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
                lineTo(lastX, size.height - spacingY)
                lineTo(0f, size.height - spacingY)
                close()
            }
            drawPath(
                path = fillPath, brush = Brush.verticalGradient(
                    colors = listOf(
                        graphColor,
                        transparentGraphColor,
                    ), endY = size.height - spacingY
                )
            )
            drawPath(
                path = strokePath, color = Color(0xFF_5EDE99), style = Stroke(
                    width = 3.dp.toPx(), cap = StrokeCap.Round
                )
            )

            xMax = upperIndex * spaceOneStepX
            yMax = size.height - stepY - spacingY - usedLine * stepY

            val radiusPx = 6f * density.density
            drawCircle(
                color = maxColor, radiusPx, Offset(xMax, yMax)
            )

        }

        if (xMax != -1f && yMax != -1f)

            Box(
                modifier = Modifier
                    .offset(
                        x = (xMax / density.density - 30).dp, (yMax / density.density - 30).dp
                    )
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    .padding(4.dp),
            ) {
                Text(
                    text = viewModel.getUpperValue(upperValue),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
    }
}

private fun DrawScope.printDottedLine(
    stepY: Float,
    countLine: Int,
    linePaint: Paint
) {
    (0..countLine).forEach { i ->
        drawContext.canvas.nativeCanvas.apply {
            val y = i * stepY
            drawLine(
                0f, y, size.width, y, linePaint
            )
        }
    }
}

private fun DrawScope.printLabels(
    labels: List<String>,
    density: Density,
    textPaint: Paint
) {
    // Отступ слева в пикселях для центра центра текста левой метки
    val spacingX = 16f * density.density

    val spaceForText = (size.width - 2 * spacingX) / (labels.size - 1)

    val centrTextY = 8f * density.density
    labels.forEachIndexed { index, label ->
        drawContext.canvas.nativeCanvas.apply {
            val x = spacingX + index * spaceForText
            val y = size.height - centrTextY
            drawText(label, x, y, textPaint)
        }
    }
}

@Composable
private fun FilterPeriod(indexPeriod: Int, onClickPeriod: (Int) -> Unit) {

    val textIds = listOf(
        R.string.detail_period_day,
        R.string.detail_period_week,
        R.string.detail_period_month,
        R.string.detail_period_year
    )

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        textIds.forEachIndexed { index, textId ->
            PeriodChip(modifier = Modifier.weight(1f),
                text = stringResource(textId),
                selected = indexPeriod == index,
                onClick = { onClickPeriod(index) })
        }
    }
}