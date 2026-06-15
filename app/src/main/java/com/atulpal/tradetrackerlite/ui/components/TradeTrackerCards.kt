package com.atulpal.tradetrackerlite.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atulpal.tradetrackerlite.ui.theme.DataLarge

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun BentoSummaryCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    subValue: String? = null,
    subValueColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    icon: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = modifier.height(128.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                icon?.invoke()
            }
            Column {
                Text(
                    text = value,
                    style = DataLarge.copy(fontSize = if (value.length > 8) 24.sp else 32.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                subValue?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = subValueColor
                    )
                }
            }
        }
    }
}
