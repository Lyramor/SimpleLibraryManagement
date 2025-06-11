package com.example.simplelibrarymanagement.presentation.ui.screen.user.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.simplelibrarymanagement.R
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.model.Category
import com.example.simplelibrarymanagement.presentation.ui.component.*
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen
import com.example.simplelibrarymanagement.presentation.ui.theme.CustomShapes

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> LoadingScreen(message = "Loading your library...")
        uiState.errorMessage != null -> NetworkErrorMessage(
            message = uiState.errorMessage ?: "An error occurred.",
            onRetry = viewModel::loadHomePageData
        )
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header dengan gradient background
                item {
                    HeaderSection(userName = uiState.userName)
                }

                // Quick Stats Section
                item {
                    QuickStatsSection(
                        totalBooks = uiState.featuredBooks.size + uiState.newArrivals.size,
                        availableBooks = (uiState.featuredBooks + uiState.newArrivals).count { it.isAvailable }
                    )
                }

                // Featured Books Section
                item {
                    BookCarouselSection(
                        title = "📚 Featured Books",
                        subtitle = "Handpicked recommendations for you",
                        books = uiState.featuredBooks,
                        onBookClick = { bookId ->
                            navController.navigate(Screen.UserBookDetail.createRoute(bookId))
                        },
                        onCategoryClick = { category ->
                            navController.navigate(
                                Screen.UserBookByCategory.createRoute(category.id, category.name)
                            )
                        }
                    )
                }

                // New Arrivals Section
                item {
                    BookCarouselSection(
                        title = "✨ New Arrivals",
                        subtitle = "Fresh additions to our collection",
                        books = uiState.newArrivals,
                        onBookClick = { bookId ->
                            navController.navigate(Screen.UserBookDetail.createRoute(bookId))
                        },
                        onCategoryClick = { category ->
                            navController.navigate(
                                Screen.UserBookByCategory.createRoute(category.id, category.name)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderSection(userName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                )
            )
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column {
            // Welcome Message dengan animasi
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "👋",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Welcome back!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = userName.ifEmpty { "Dear Reader" },
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "What adventure awaits you today?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Search Bar dengan card wrapper untuk elevated look
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = CustomShapes.SearchField
            ) {
                SearchTextField(
                    value = "",
                    onValueChange = { /* Handle search */ },
                    placeholder = "Search for books, authors, genres...",
                    leadingIcon = Icons.Default.Search,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
private fun QuickStatsSection(
    totalBooks: Int,
    availableBooks: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            icon = Icons.Outlined.MenuBook,
            title = "Total Books",
            value = totalBooks.toString(),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            icon = Icons.Outlined.AutoStories,
            title = "Available",
            value = availableBooks.toString(),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    icon: ImageVector,
    title: String,
    value: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = CustomShapes.CardMedium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon dengan background circle
            Surface(
                modifier = Modifier.size(48.dp),
                shape = androidx.compose.foundation.shape.CircleShape,
                color = color.copy(alpha = 0.1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun BookCarouselSection(
    title: String,
    subtitle: String,
    books: List<Book>,
    onBookClick: (String) -> Unit,
    onCategoryClick: (Category) -> Unit
) {
    Column {
        // Section Header dengan subtitle
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (books.isEmpty()) {
            // Enhanced empty state dengan ilustrasi yang lebih menarik
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                shape = CustomShapes.CardLarge,
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Animated book emoji stack
                    Text(
                        text = "📚",
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "📖",
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.offset(x = 16.dp, y = (-16).dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "No Books Available",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "This section is currently empty, but new books are coming soon!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(books, key = { it.id }) { book ->
                    EnhancedCardBook(
                        title = book.title,
                        author = book.author,
                        imageUrl = book.imageUrl,
                        status = if (book.isAvailable) BookStatus.Available else BookStatus.Borrowed,
                        category = book.category,
                        onClick = { onBookClick(book.id) },
                        onCategoryClick = onCategoryClick,
                        modifier = Modifier.width(280.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EnhancedCardBook(
    title: String,
    author: String,
    imageUrl: String,
    status: BookStatus,
    category: Category?,
    onClick: () -> Unit,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 6.dp,
            hoveredElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = CustomShapes.CardMedium
    ) {
        Column {
            // Image section dengan overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
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
                        .fillMaxSize()
                        .clip(
                            CustomShapes.CardMedium.copy(
                                bottomStart = CornerSize(0.dp),
                                bottomEnd = CornerSize(0.dp)
                            )
                        )
                )

                // Gradient overlay untuk readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    androidx.compose.ui.graphics.Color.Transparent,
                                    androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.3f)
                                ),
                                startY = 0f,
                                endY = 200f
                            )
                        )
                )

                // Status badge overlay
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    color = status.color,
                    shape = CustomShapes.Badge
                ) {
                    Text(
                        text = status.displayName,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Content section
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Category chip
                category?.let { cat ->
                    Surface(
                        onClick = { onCategoryClick(cat) },
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CustomShapes.Tab,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = "📚 ${cat.name}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }

                // Book title
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Author dengan icon
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "✍️",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = author,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}