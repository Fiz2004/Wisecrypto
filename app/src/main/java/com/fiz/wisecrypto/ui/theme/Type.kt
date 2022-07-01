package com.fiz.wisecrypto.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.fiz.wisecrypto.R

val MulishRegular = FontFamily(
    Font(R.font.mulish_regular),
)
val MulishSemiBold = FontFamily(
    Font(R.font.mulish_semibold),
)
val MulishBold = FontFamily(
    Font(R.font.mulish_bold),
)
val MulishExtraBold = FontFamily(
    Font(R.font.mulish_extra_bold),
)
val MontserratMedium = FontFamily(
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

    headlineLarge = TextStyle(
        fontFamily = MulishBold,
        fontSize = 20.sp,
    ),

    headlineMedium = TextStyle(
        fontFamily = MulishBold,
        fontSize = 16.sp,
    ),

//    titleLarge = TextStyle(
//        fontFamily = MontserratMedium,
//        fontSize = 14.sp,
//    ),

    titleMedium = TextStyle(
        fontFamily = MulishSemiBold,
        fontSize = 14.sp,
    ),

    titleSmall = TextStyle(
        fontFamily = MulishBold,
        fontSize = 12.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = MulishRegular,
        fontSize = 16.sp,
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

@get:Composable
val Typography.displayLarge2: TextStyle
    get() = TextStyle(
        fontFamily = MulishBold,
        fontSize = 24.sp,
    )

@get:Composable
val Typography.titleMedium2: TextStyle
    get() = TextStyle(
        fontFamily = MontserratMedium,
        fontSize = 14.sp,
    )

@get:Composable
val Typography.textField: TextStyle
    get() = TextStyle(
        fontFamily = MulishRegular,
        fontSize = 14.sp,
    )

@get:Composable
val Typography.bodyMedium2: TextStyle
    get() = TextStyle(
        fontFamily = MulishRegular,
        fontSize = 12.sp,
    )

@get:Composable
val Typography.bodyLarge2: TextStyle
    get() = TextStyle(
        fontFamily = MulishExtraBold,
        fontSize = 16.sp,
    )
