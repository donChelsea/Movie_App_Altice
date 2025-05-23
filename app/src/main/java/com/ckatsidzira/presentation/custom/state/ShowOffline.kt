package com.ckatsidzira.presentation.custom.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ckatsidzira.R

@Composable
fun ShowOffline(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.NetworkCheck,
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.padding_16))
                    .size(dimensionResource(R.dimen.size_40)),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.offline_screen),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = dimensionResource(R.dimen.text_size_22).value.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewOfflineScreen() {
    ShowOffline()
}

