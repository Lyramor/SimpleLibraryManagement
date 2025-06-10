package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.simplelibrarymanagement.R
import com.example.simplelibrarymanagement.domain.model.Category

enum class BookStatus(val displayName: String, val color: Color) {
    Available("Available", Color(0xFF388E3C)),
    Borrowed("Borrowed", Color(0xFFF57C00))
}

@Composable
fun CardBook(
    title: String,
    author: String,
    imageUrl: String,
    status: BookStatus,
    category: Category?,
    onClick: () -> Unit,
    onCategoryClick: (Category) -> Unit, // DIUBAH: Parameter untuk aksi klik kategori
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 80.dp, height = 110.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                category?.let { cat ->
                    Text(
                        text = cat.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(bottom = 2.dp)
                            // DIUBAH: Membuat teks kategori bisa di-klik
                            .clickable { onCategoryClick(cat) }
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "by $author",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                BookStatusChip(status = status)
            }
        }
    }
}

@Composable
private fun BookStatusChip(status: BookStatus) {
    Surface(
        color = status.color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = status.displayName,
            color = status.color,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
