package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.simplelibrarymanagement.R
import com.example.simplelibrarymanagement.presentation.ui.theme.SimpleLibraryManagementTheme
import com.example.simplelibrarymanagement.presentation.ui.theme.libraryColors

/**
 * Enum untuk merepresentasikan status buku.
 * @property displayName Nama status yang akan ditampilkan.
 * @property color Warna yang akan digunakan untuk status tersebut.
 */
enum class BookStatus(val displayName: String, val color: Color) {
    Available("Available", Color(0xFF388E3C)),
    Borrowed("Borrowed", Color(0xFFF57C00))
}

/**
 * Composable untuk menampilkan kartu buku yang informatif dan dapat diklik.
 *
 * @param title Judul buku.
 * @param author Penulis buku.
 * @param imageUrl URL gambar sampul buku.
 * @param status Status ketersediaan buku ([BookStatus]).
 * @param onClick Aksi yang dijalankan ketika kartu diklik.
 * @param modifier Modifier untuk kustomisasi.
 */
@Composable
fun CardBook(
    title: String,
    author: String,
    imageUrl: String,
    status: BookStatus,
    onClick: () -> Unit,
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
            // Book Cover Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_background) // Ganti dengan placeholder yang sesuai
                    .error(R.drawable.ic_launcher_background)       // Ganti dengan gambar error yang sesuai
                    .build(),
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 80.dp, height = 110.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Book Details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
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

/**
 * Composable kecil untuk menampilkan status buku dalam bentuk chip.
 */
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


@Preview(showBackground = true, name = "Card Book - Available")
@Composable
fun CardBookAvailablePreview() {
    SimpleLibraryManagementTheme {
        CardBook(
            title = "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
            author = "James Clear",
            imageUrl = "", // URL gambar bisa dikosongkan untuk preview
            status = BookStatus.Available,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Card Book - Borrowed")
@Composable
fun CardBookBorrowedPreview() {
    SimpleLibraryManagementTheme {
        CardBook(
            title = "Sapiens: A Brief History of Humankind",
            author = "Yuval Noah Harari",
            imageUrl = "", // URL gambar bisa dikosongkan untuk preview
            status = BookStatus.Borrowed,
            onClick = {}
        )
    }
}
