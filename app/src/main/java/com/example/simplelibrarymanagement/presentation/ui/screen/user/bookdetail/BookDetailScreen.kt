package com.example.simplelibrarymanagement.presentation.ui.screen.user.bookdetail

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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
    val scrollState = rememberScrollState()

    // Animation for floating elements
    val infiniteTransition = rememberInfiniteTransition(label = "floating")
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating_offset"
    )

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                actionLabel = "Tutup",
                duration = SnackbarDuration.Short
            )
            viewModel.clearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading && uiState.book == null -> {
                LoadingScreen()
            }
            uiState.book != null -> {
                val book = uiState.book!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    // Enhanced Book Header
                    EnhancedBookHeader(
                        book = book,
                        onNavigateBack = onNavigateBack,
                        floatingOffset = floatingOffset
                    )

                    // Enhanced Book Info Section
                    EnhancedBookInfoSection(book = book)

                    // Bottom padding for button
                    Spacer(modifier = Modifier.height(100.dp))
                }

                // Enhanced Floating Action Button
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    EnhancedActionButton(
                        book = book,
                        uiState = uiState,
                        onBorrowClick = { viewModel.borrowBook() }
                    )
                }
            }
            else -> {
                ErrorMessage(
                    message = "Buku tidak ditemukan.",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Enhanced Snackbar Host
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { snackbarData ->
            Snackbar(
                snackbarData = snackbarData,
                shape = MaterialTheme.shapes.large,
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                actionColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedBookHeader(
    book: Book,
    onNavigateBack: () -> Unit,
    floatingOffset: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        // Blurred background image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 20.dp)
        )

        // Enhanced gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f),
                            MaterialTheme.colorScheme.surface
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Enhanced Top App Bar
        TopAppBar(
            title = { },
            navigationIcon = {
                Surface(
                    modifier = Modifier
                        .size(48.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    shadowElevation = 8.dp
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.statusBarsPadding()
        )

        // Floating book cover with enhanced styling
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = floatingOffset.dp)
                .size(width = 200.dp, height = 280.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .crossfade(true)
                    .build(),
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large)
            )
        }

        // Status badge
        Surface(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
                .offset(y = 60.dp),
            shape = MaterialTheme.shapes.medium,
            color = if (book.isAvailable)
                MaterialTheme.libraryColors.availableContainer else
                MaterialTheme.libraryColors.borrowedContainer,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = if (book.isAvailable) Icons.Default.CheckCircle else Icons.Default.Schedule,
                    contentDescription = null,
                    tint = if (book.isAvailable)
                        MaterialTheme.libraryColors.available else
                        MaterialTheme.libraryColors.borrowed,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = if (book.isAvailable) "Tersedia" else "Dipinjam",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (book.isAvailable)
                        MaterialTheme.libraryColors.available else
                        MaterialTheme.libraryColors.borrowed
                )
            }
        }
    }
}

@Composable
fun EnhancedBookInfoSection(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Enhanced Title and Author
        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 36.sp
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "oleh ${book.author}",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Enhanced Info Grid
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EnhancedInfoChip(
                    icon = Icons.Default.Bookmark,
                    label = "Kategori",
                    value = book.category?.name ?: "Tidak ada",
                    color = MaterialTheme.libraryColors.categoryFiction
                )
                EnhancedInfoChip(
                    icon = Icons.Default.CalendarToday,
                    label = "Tahun",
                    value = book.year?.toString() ?: "N/A",
                    color = MaterialTheme.libraryColors.info
                )
                EnhancedInfoChip(
                    icon = Icons.Default.MenuBook,
                    label = "Status",
                    value = if (book.isAvailable) "Tersedia" else "Dipinjam",
                    color = if (book.isAvailable)
                        MaterialTheme.libraryColors.available else
                        MaterialTheme.libraryColors.borrowed
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Enhanced Description Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Sinopsis",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = book.description,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 26.sp,
                        fontSize = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun EnhancedInfoChip(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = color.copy(alpha = 0.1f),
            shadowElevation = 2.dp
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EnhancedActionButton(
    book: Book,
    uiState: BookDetailUiState,
    onBorrowClick: () -> Unit
) {
    val isAvailable = !uiState.isBookBorrowed && book.isAvailable
    val buttonText = when {
        uiState.isLoading -> "Memproses..."
        !book.isAvailable -> "Buku Sedang Dipinjam"
        uiState.isBookBorrowed -> "Sudah Dipinjam"
        else -> "Pinjam Buku Sekarang"
    }

    Button(
        onClick = onBorrowClick,
        enabled = isAvailable && !uiState.isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isAvailable)
                MaterialTheme.colorScheme.primary else
                MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isAvailable)
                MaterialTheme.colorScheme.onPrimary else
                MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isAvailable) 8.dp else 2.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Icon(
                    imageVector = when {
                        !book.isAvailable -> Icons.Default.Block
                        uiState.isBookBorrowed -> Icons.Default.Done
                        else -> Icons.Default.LibraryBooks
                    },
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = buttonText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}