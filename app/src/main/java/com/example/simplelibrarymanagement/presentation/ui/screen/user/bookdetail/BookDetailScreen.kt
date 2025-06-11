package com.example.simplelibrarymanagement.presentation.ui.screen.user.bookdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.simplelibrarymanagement.R
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.presentation.ui.component.CustomButton
import com.example.simplelibrarymanagement.presentation.ui.component.ErrorMessage
import com.example.simplelibrarymanagement.presentation.ui.component.LoadingScreen
import com.example.simplelibrarymanagement.presentation.ui.theme.libraryColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Detail Buku") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent, // Membuat top bar transparan
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        // Menggunakan Box untuk menumpuk elemen dan membuat tombol tetap di bawah
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding()) // Hanya padding bawah dari scaffold
            ) {
                when {
                    uiState.isLoading && uiState.book == null -> LoadingScreen()
                    uiState.book != null -> {
                        val book = uiState.book!!
                        // Konten yang bisa di-scroll
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 90.dp) // Beri ruang untuk tombol di bawah
                        ) {
                            BookHeader(book = book)
                            BookInfoSection(book = book)
                        }

                        // Tombol yang menempel di bawah
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(horizontal = 16.dp, vertical = 24.dp)
                        ) {
                            CustomButton(
                                text = if (uiState.isBookBorrowed || !book.isAvailable) "Sudah Dipinjam" else "Pinjam Buku",
                                onClick = {
                                    if (!uiState.isBookBorrowed && book.isAvailable) {
                                        viewModel.borrowBook()
                                    }
                                },
                                enabled = !uiState.isBookBorrowed && book.isAvailable,
                                isLoading = uiState.isLoading
                            )
                        }
                    }
                    else -> ErrorMessage(message = "Buku tidak ditemukan.")
                }
            }
        }
    )
}

@Composable
fun BookHeader(book: Book) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        // Gambar latar belakang dengan efek blur atau gradient
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Gradient overlay untuk membuat teks lebih mudah dibaca
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.5f), Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Gambar sampul buku di tengah
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .crossfade(true)
                .build(),
            contentDescription = book.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.Center)
                .height(220.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

@Composable
fun BookInfoSection(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Judul dan Penulis
        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "oleh ${book.author}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Info Detail dalam Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            InfoChip(
                icon = Icons.Default.Bookmark,
                label = "Kategori",
                value = book.category?.name ?: "N/A"
            )
            InfoChip(
                icon = Icons.Default.CalendarToday,
                label = "Tahun",
                value = book.year?.toString() ?: "N/A"
            )
            InfoChip(
                icon = Icons.Default.CheckCircle,
                label = "Status",
                value = if (book.isAvailable) "Tersedia" else "Dipinjam",
                highlightColor = if (book.isAvailable) MaterialTheme.libraryColors.available else MaterialTheme.libraryColors.borrowed
            )
        }
        Divider(modifier = Modifier.padding(vertical = 24.dp))

        // Sinopsis / Deskripsi
        Text(
            text = "Sinopsis",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.description,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun InfoChip(
    icon: ImageVector,
    label: String,
    value: String,
    highlightColor: Color? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = highlightColor ?: MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = highlightColor ?: MaterialTheme.colorScheme.onSurface
        )
    }
}