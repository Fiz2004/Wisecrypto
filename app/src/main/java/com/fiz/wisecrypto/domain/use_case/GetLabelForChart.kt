package com.fiz.wisecrypto.domain.use_case

import com.fiz.wisecrypto.domain.models.History
import com.fiz.wisecrypto.ui.screens.main.market.detail.PeriodFilterChip
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLabelForChart @Inject constructor(){

    operator fun invoke(
        prices: List<History>,
        indexPeriodFilterChip: PeriodFilterChip,
        monthNames:List<String>,
        daysNames:List<String>
    ): List<String> {
        return when (indexPeriodFilterChip) {
            PeriodFilterChip.Day -> getLabelsForDay(prices)
            PeriodFilterChip.Week -> getLabelsForWeek(prices,daysNames)
            PeriodFilterChip.Month -> getLabelsForMonth(prices)
            PeriodFilterChip.Year -> getLabelsForYear(prices,monthNames)
        }
    }

    private fun getLabelsForDay(prices: List<History>): MutableList<String> {
        val result = mutableListOf<String>()
        for (index in prices.lastIndex downTo 0 step prices.lastIndex / 6) {
            val value = prices[index]
            var hour = value.time.hour
            val minute = when (value.time.minute) {
                in 0..14 -> 0
                in 14..44 -> 30
                else -> {
                    hour += 1
                    0
                }
            }
            val label = hour.toString().padStart(2, '0') + "." +
                    minute.toString().padStart(2, '0')
            result.add(label)
        }
        result.reverse()
        return result
    }

    private fun getLabelsForWeek(prices: List<History>,daysNames:List<String>): MutableList<String> {
        val result = mutableListOf<String>()
        for (index in prices.lastIndex downTo 0 step prices.lastIndex / 6) {
            val value = prices[index]
            val dayOfWeek = value.time.dayOfWeek.value
            val label = daysNames[dayOfWeek - 1]
            result.add(label)
        }
        result.reverse()
        return result
    }

    private fun getLabelsForMonth(prices: List<History>): MutableList<String> {
        val result = mutableListOf<String>()
        for (index in prices.lastIndex downTo 0 step prices.lastIndex / 6) {
            val value = prices[index]
            val month = value.time.dayOfMonth
            val label = month.toString()
            result.add(label)
        }
        result.reverse()
        return result
    }

    private fun getLabelsForYear(prices: List<History>,monthNames:List<String>): MutableList<String> {
        val result = mutableListOf<String>()
        for (index in prices.lastIndex downTo 0 step prices.lastIndex / 6) {
            val value = prices[index]
            val month = value.time.month.value
            val label = monthNames[month - 1]
            result.add(label)
        }
        result.reverse()
        return result
    }
}