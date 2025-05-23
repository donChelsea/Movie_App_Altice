package com.ckatsidzira.presentation.custom.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ckatsidzira.domain.model.TimeWindow
import com.ckatsidzira.presentation.util.toRegularCase

@Composable
fun ScreenSection(
    modifier: Modifier = Modifier,
    title: String,
    defaultSelectedItemIndex: Int = 0,
    onSelectedChanged: (String, Int) -> Unit = { _, _ -> }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(16.dp))

        FilterChipGroup(
            items = listOf(
                TimeWindow.DAY,
                TimeWindow.WEEK,
            ),
            defaultSelectedItemIndex = defaultSelectedItemIndex,
            onSelectedChanged = { name, index -> onSelectedChanged(name, index) }
        )
    }
}

@Composable
fun FilterChipGroup(
    modifier: Modifier = Modifier,
    items: List<TimeWindow>,
    defaultSelectedItemIndex: Int = 0,
    onSelectedChanged: (String, Int) -> Unit = { name, index -> }
) {
    LazyRow(modifier = modifier) {
        items(items.size) { index: Int ->
            FilterChip(
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(end = 6.dp),
                selected = items[defaultSelectedItemIndex] == items[index],
                onClick = {
                    onSelectedChanged(items[index].value, index)
                },
                label = { Text(items[index].name.toRegularCase()) },
                leadingIcon = if (items[defaultSelectedItemIndex] == items[index]) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "",
                            modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                        )
                    }
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSection() {
    ScreenSection(title = "Trending", onSelectedChanged = { name, index -> })
}
