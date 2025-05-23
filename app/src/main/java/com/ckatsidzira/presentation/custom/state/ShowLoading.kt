package com.ckatsidzira.presentation.custom.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ckatsidzira.presentation.custom.animation.ProgressAnimation

@Composable
fun ShowLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        ProgressAnimation()
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLoadingScreen() {
    ShowLoading()
}