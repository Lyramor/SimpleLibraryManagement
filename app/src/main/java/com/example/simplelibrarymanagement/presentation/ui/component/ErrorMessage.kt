package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.simplelibrarymanagement.presentation.ui.theme.libraryShapes

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    type: MessageType = MessageType.Error
) {
    val (backgroundColor, borderColor, iconColor, icon) = when (type) {
        MessageType.Error -> Quadruple(
            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.error.copy(alpha = 0.3f),
            MaterialTheme.colorScheme.error,
            Icons.Default.Error
        )
        MessageType.Warning -> Quadruple(
            Color(0xFFFFF3CD),
            Color(0xFFFFE69C),
            Color(0xFFB45309),
            Icons.Default.Warning
        )
        MessageType.Info -> Quadruple(
            Color(0xFFD1ECF1),
            Color(0xFFBEE5EB),
            Color(0xFF0C5460),
            Icons.Default.Info
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.libraryShapes.CardSmall)
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = MaterialTheme.libraryShapes.CardSmall
            )
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = iconColor,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun InlineErrorMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(14.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun FormErrorMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
        )
    }
}

@Composable
fun NetworkErrorMessage(
    message: String = "Tidak dapat terhubung ke server. Periksa koneksi internet Anda.",
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.libraryShapes.CardMedium)
            .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.error.copy(alpha = 0.3f),
                shape = MaterialTheme.libraryShapes.CardMedium
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = MessageType.Error,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            onRetry?.let {
                Spacer(modifier = Modifier.size(12.dp))

                SmallButton(
                    text = "Coba Lagi",
                    onClick = onRetry,
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            }
        }
    }
}

enum class MessageType {
    Error,
    Warning,
    Info
}

// Helper data class for destructuring
private data class Quadruple<T1, T2, T3, T4>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4
)