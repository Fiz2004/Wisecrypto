package com.fiz.wisecrypto.util

import android.content.Context
import android.widget.Toast
import com.fiz.wisecrypto.R

fun String.toDoubleOrNull(): Double? {
    return try {
        this.replace(
            ",",
            "."
        ).toDouble()
    } catch (e: Exception) {
        null
    }
}

fun showError(
    context: Context,
    message: String?
) {
    val text = context.getString(
        when (message) {
            null -> R.string.error_network_default
            "HTTP 429 " -> R.string.error_many_query
            ERROR_TEXT_FIELD -> R.string.error_edit_value_coin
            ERROR_SELL -> R.string.error_sell
            else -> R.string.error_network
        }, message
    )
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

const val ERROR_TEXT_FIELD = "ERROR_TEXT_FIELD"
const val ERROR_SELL = "ERROR_SELL"