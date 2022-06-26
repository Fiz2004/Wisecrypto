package com.fiz.wisecrypto.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.fiz.wisecrypto.R

// Set of Material typography styles to start with

val MulishRegular = FontFamily(
    Font(R.font.mulish_regular),
)
val MulishSemiBold = FontFamily(
    Font(R.font.mulish_semibold),
)
val MulishBold = FontFamily(
    Font(R.font.mulish_bold),
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_medium),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = MulishBold,
        fontSize = 32.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = MulishSemiBold,
        fontSize = 16.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = MulishSemiBold,
        fontSize = 14.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontSize = 14.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = MulishBold,
        fontSize = 20.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = MulishSemiBold,
        fontSize = 12.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = MulishSemiBold,
        fontSize = 10.sp,
    ),

    labelSmall = TextStyle(
        fontFamily = MulishBold,
        fontSize = 10.sp,
    ),


    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)