package com.demo.moviedemo.core.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val screenXPadding: Dp = 20.dp,
    val screenTopPaddingLg: Dp = 14.dp,
    val screenTopPaddingMd: Dp = 8.dp,
    val screenTopPaddingSm: Dp = 4.dp,
    val paddingSm: Dp = 8.dp,
    val paddingMd: Dp = 12.dp,
    val paddingLg: Dp = 16.dp,
    val paddingXlg: Dp = 20.dp,
    val cornerRadiusSm: Dp = 8.dp,
    val cornerRadiusMd: Dp = 12.dp,
    val cornerRadiusLg: Dp = 16.dp,
    val cornerRadiusXlg: Dp = 20.dp,
    val bottomButtonContainerHeight: Dp = 72.dp,
    val dividerLineStartPaddingLg: Dp = 64.dp,
    val dividerLineStartPaddingMd: Dp = 56.dp,
    val dividerLineStartPaddingSm: Dp = 52.dp
)

val LocalDimension = compositionLocalOf { Dimensions() }