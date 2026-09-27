package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HadithItem
import com.example.ui.NoorLifeViewModel
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.components.ShareCardDialog
import com.example.ui.components.SourceVerificationBadge
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun HadithScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val hadiths by viewModel.hadiths.collectAsState()
    val books by viewModel.hadithBooks.collectAsState()
    var selectedBookId by remember { mutableStateOf("all") }
    var shareHadith by remember { mutableStateOf<HadithItem?>(null) }

    val filteredHadiths = remember(selectedBookId, hadiths) {
        if (selectedBookId == "all") hadiths
        else hadiths.filter { it.bookId == selectedBookId }
    }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "সহিহ হাদিস সংকলন",
                subtitle = "বুখারী, মুসলিম, তিরমিযী ও অন্যান্য বিশুদ্ধ গ্রন্থ",
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Book Selection Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedBookId == "all",
                        onClick = { selectedBookId = "all" },
                        label = { Text("সকল গ্রন্থ") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
                items(books) { book ->
                    val isSelected = selectedBookId == book.id
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedBookId = book.id },
                        label = { Text(book.nameBengali) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Hadith List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredHadiths) { hadith ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.35f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    NoorLifeIcon(NoorLifeIconType.Hadith, size = 22.dp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "${hadith.bookNameBengali} - হাদিস নং ${hadith.hadithNumber}",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldDark
                                        )
                                        Text(
                                            text = "অধ্যায়: ${hadith.chapterNameBengali}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Row {
                                    IconButton(onClick = {
                                        viewModel.toggleBookmark(
                                            "HADITH",
                                            hadith.id,
                                            hadith.bookNameBengali,
                                            hadith.translationBengali,
                                            hadith.reference
                                        )
                                    }) {
                                        Icon(Icons.Default.BookmarkBorder, contentDescription = "বুকমার্ক", tint = WarmGold)
                                    }
                                    IconButton(onClick = { shareHadith = hadith }) {
                                        Icon(Icons.Default.Share, contentDescription = "শেয়ার", tint = EmeraldPrimary)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Arabic Hadith Text
                            Text(
                                text = hadith.textArabic,
                                style = MaterialTheme.typography.titleMedium.copy(lineHeight = 28.sp),
                                textAlign = TextAlign.End,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Bengali Translation
                            Text(
                                text = hadith.translationBengali,
                                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "বর্ণনাকারী: ${hadith.narrator}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SourceVerificationBadge(reference = hadith.reference)
                                Text(
                                    text = hadith.grade,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    shareHadith?.let { h ->
        ShareCardDialog(
            title = "${h.bookNameBengali} - হাদিস নং ${h.hadithNumber}",
            arabicText = h.textArabic,
            translation = h.translationBengali,
            reference = h.reference,
            onDismiss = { shareHadith = null }
        )
    }
}
