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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DuaItem
import com.example.ui.NoorLifeViewModel
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.components.ShareCardDialog
import com.example.ui.components.SourceVerificationBadge
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun DuaZikrScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val duas by viewModel.duas.collectAsState()
    var selectedCategory by remember { mutableStateOf("সকল") }
    var shareDua by remember { mutableStateOf<DuaItem?>(null) }

    val categories = listOf("সকল", "সকাল-সন্ধ্যা", "ক্ষমা প্রার্থনা", "বিপদ-আপদ", "সফর")

    val filteredDuas = remember(selectedCategory, duas) {
        if (selectedCategory == "সকল") duas
        else duas.filter { it.category == selectedCategory }
    }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "দৈনন্দিন দোয়া ও মোনাজাত",
                subtitle = "হিসনুল মুসলিম ও পবিত্র কুরআন হতে সংগৃহীত",
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
            // Category Filter
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Duas List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredDuas) { dua ->
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
                                    NoorLifeIcon(NoorLifeIconType.Dua, size = 22.dp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = dua.titleBengali,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                }

                                Row {
                                    IconButton(onClick = {
                                        viewModel.toggleBookmark(
                                            "DUA",
                                            dua.id,
                                            dua.titleBengali,
                                            dua.meaningBengali,
                                            dua.reference
                                        )
                                    }) {
                                        Icon(Icons.Default.BookmarkBorder, contentDescription = "বুকমার্ক", tint = WarmGold)
                                    }
                                    IconButton(onClick = { shareDua = dua }) {
                                        Icon(Icons.Default.Share, contentDescription = "শেয়ার", tint = EmeraldPrimary)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Arabic Dua Text
                            Text(
                                text = dua.textArabic,
                                style = MaterialTheme.typography.titleMedium.copy(lineHeight = 28.sp),
                                textAlign = TextAlign.End,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Pronunciation
                            Text(
                                text = "উচ্চারণ: ${dua.pronunciationBengali}",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondaryLight
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // Bengali Meaning
                            Text(
                                text = "অর্থ: ${dua.meaningBengali}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Benefit
                            Card(
                                colors = CardDefaults.cardColors(containerColor = GoldContainer.copy(alpha = 0.5f)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "ফজিলত: ${dua.benefit}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OnGoldContainer,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            SourceVerificationBadge(reference = dua.reference)
                        }
                    }
                }
            }
        }
    }

    shareDua?.let { d ->
        ShareCardDialog(
            title = d.titleBengali,
            arabicText = d.textArabic,
            translation = d.meaningBengali,
            reference = d.reference,
            onDismiss = { shareDua = null }
        )
    }
}
