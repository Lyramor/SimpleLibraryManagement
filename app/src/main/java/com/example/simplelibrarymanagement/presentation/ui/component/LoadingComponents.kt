package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplelibrarymanagement.presentation.ui.theme.LibraryTheme

@Composable
fun LoadingScreen(
    message: String = "Loading...",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SmallLoadingIndicator(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 24.dp
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 2.dp
    )
}

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surface.copy(alpha = alpha),
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
        MaterialTheme.colorScheme.surface.copy(alpha = alpha)
    )

    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset.Zero,
                    end = Offset(x = 300f, y = 300f)
                )
            )
    )
}

@Composable
fun BookItemSkeleton(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Book cover skeleton
            ShimmerEffect(
                modifier = Modifier
                    .size(width = 60.dp, height = 80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Title skeleton
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                )

                // Author skeleton
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                )

                // Status skeleton
                ShimmerEffect(
                    modifier = Modifier
                        .width(80.dp)
                        .height(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}

@Composable
fun BookListSkeleton(
    itemCount: Int = 5,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
    ) {
        items(itemCount) {
            BookItemSkeleton()
        }
    }
}

@Composable
fun EmptyState(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    actionButton: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            actionButton?.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingComponentsPreview() {
    LibraryTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Loading Screen:",
                style = MaterialTheme.typography.titleMedium
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                LoadingScreen()
            }

            Text(
                text = "Book Skeleton:",
                style = MaterialTheme.typography.titleMedium
            )

            BookItemSkeleton()

            Text(
                text = "Empty State:",
                style = MaterialTheme.typography.titleMedium
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                EmptyState(
                    title = "No Books Found",
                    description = "There are no books in your library yet. Add some books to get started.",
                    actionButton = {
                        CustomButton(
                            text = "Add Book",
                            onClick = { },
                            fullWidth = false
                        )
                    }
                )
            }
        }
    }
}