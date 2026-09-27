package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BookItem
import com.example.ui.NoorLifeViewModel
import com.example.ui.Screen
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.components.SourceVerificationBadge
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun LibraryScreen(
    viewModel: NoorLifeViewModel,
    onNavigate: (Screen) -> Unit
) {
    val books by viewModel.books.collectAsState()
    var selectedCategory by remember { mutableStateOf("সকল") }
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf("সকল", "হাদিস", "সিরাত", "আকীদা", "বাংলা সাহিত্য")

    val filteredBooks = remember(selectedCategory, searchQuery, books) {
        books.filter { book ->
            val matchesCategory = selectedCategory == "সকল" || book.category == selectedCategory
            val matchesQuery = searchQuery.isBlank() || book.titleBengali.contains(searchQuery, ignoreCase = true) || book.author.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "ইসলামিক ও বাংলা লাইব্রেরি",
                subtitle = "কপিরাইটমুক্ত ও অনুমোদিত বিশুদ্ধ গ্রন্থ সংকলন"
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("বই বা লেখকের নাম লিখুন...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EmeraldPrimary,
                    unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                )
            )

            // Category Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = cat == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Books List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredBooks) { book ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectBook(book) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(EmeraldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                NoorLifeIcon(
                                    type = if (book.isIslamic) NoorLifeIconType.Library else NoorLifeIconType.Stories,
                                    size = 30.dp,
                                    tint = EmeraldPrimary,
                                    accentTint = WarmGold
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = book.titleBengali,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "লেখক: ${book.author}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "প্রকাশনা: ${book.publisher} • ${book.category}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = EmeraldPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookReaderScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val book by viewModel.selectedBook.collectAsState()
    var currentChapterIndex by remember { mutableStateOf(0) }
    var fontSize by remember { mutableStateOf(16.sp) }

    val chapters = book.chapters
    val currentChapter = if (chapters.isNotEmpty() && currentChapterIndex < chapters.size) {
        chapters[currentChapterIndex]
    } else null

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = book.titleBengali,
                subtitle = "অধ্যায়: ${currentChapter?.title ?: "সূচিপত্র"}",
                onBackClick = onNavigateBack
            )
        },
        bottomBar = {
            Surface(color = MaterialTheme.colorScheme.surfaceVariant, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { if (currentChapterIndex > 0) currentChapterIndex-- },
                        enabled = currentChapterIndex > 0,
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Text("← পূর্ববর্তী")
                    }

                    Text(
                        text = "${currentChapterIndex + 1} / ${chapters.size}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = { if (currentChapterIndex < chapters.size - 1) currentChapterIndex++ },
                        enabled = currentChapterIndex < chapters.size - 1,
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Text("পরবর্তী →")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = IvorySurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = currentChapter?.title ?: book.titleBengali,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "লেখক: ${book.author} | প্রকাশক: ${book.publisher}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        SourceVerificationBadge(reference = book.license)
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = currentChapter?.content ?: book.description,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = fontSize,
                                lineHeight = (fontSize.value * 1.6).sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
