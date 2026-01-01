package com.example.demosample.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.demo.news.R


//Create Own Font Family
/*
    fontFamily = AppFontFamily.appFontFamily,
    fontWeight = FontWeight.Medium //here rubik_medium will be used
                FontWeight.Normal //rubik_regular will be used
 */
object AppFontFamily{
    val appFontFamily = FontFamily(
        Font(R.font.rubik_regular,FontWeight.Normal),//regular font
        Font(R.font.rubik_medium,FontWeight.Medium),//medium font
        Font(R.font.rubik_bold,FontWeight.Bold),//Bold font
    )
}

object AppTypography{

    /*
        create own custom style for text
        mostly we need textSize 10sp to 18 sp
        and font mostly medium or regular so we create only both fonts
        if need we can we can use labelMedium10.copy() and override size and font
        we are not set color here because color set according theme
    */

    /*
       use in code like this MaterialTheme.typography.labelMedium12
       if u need bold then use
       MaterialTheme.typography.labelMedium12.copy(
       ...
       fontWeight = FontWeight.Bold
       )
    */

    val Typography.labelMedium10:TextStyle
        get() = TextStyle(
            fontSize = 10.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Medium
        )

    val Typography.labelRegular10:TextStyle
        get() = TextStyle(
            fontSize = 10.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Normal
        )

    val Typography.labelMedium12:TextStyle
        get() = TextStyle(
            fontSize = 12.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Medium
        )

    val Typography.labelRegular12:TextStyle
        get() = TextStyle(
            fontSize = 12.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Normal
        )

    val Typography.labelMedium14:TextStyle
        get() = TextStyle(
            fontSize = 14.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Medium
        )

    val Typography.labelRegular14:TextStyle
        get() = TextStyle(
            fontSize = 14.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Normal
        )

    val Typography.labelMedium16:TextStyle
        get() = TextStyle(
            fontSize = 14.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Medium
        )

    val Typography.labelRegular16:TextStyle
        get() = TextStyle(
            fontSize = 14.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Normal
        )

    val Typography.labelMedium18:TextStyle
        get() = TextStyle(
            fontSize = 18.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Normal
        )

    val Typography.labelRegular18:TextStyle
        get() = TextStyle(
            fontSize = 18.sp,
            fontFamily = AppFontFamily.appFontFamily,
            fontWeight = FontWeight.Normal
        )

    // Set of Material typography styles to start with
    // we are nto use Predefined because we need same size but different font
    // create own custom
    val Typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
    ).apply {
        labelRegular10
        labelRegular12
        labelRegular14
        labelRegular16
        labelRegular18
        labelMedium10
        labelMedium12
        labelMedium14
        labelMedium16
        labelMedium18
    }
}