package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.data.model.Surah
import com.example.data.model.Verse
import com.example.ui.NoorLifeViewModel
import com.example.ui.Screen
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.components.ShareCardDialog
import com.example.ui.components.SourceVerificationBadge
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun QuranScreen(
    viewModel: NoorLifeViewModel,
    onNavigate: (Screen) -> Unit
) {
    val surahs by viewModel.allSurahs.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredSurahs = remember(searchQuery, surahs) {
        if (searchQuery.isBlank()) surahs
        else surahs.filter {
            it.nameBengali.contains(searchQuery, ignoreCase = true) ||
            it.nameArabic.contains(searchQuery) ||
            it.nameEnglish.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "আল-কুরআনুল কারীম",
                subtitle = "১১৪টি সূরা • আরবি পাঠ ও বাংলা অর্থ"
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Search Surah Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                placeholder = { Text("সূরা খুঁজুন (যেমন: ফাতিহা, ইয়াসীন, বাকারা...)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EmeraldPrimary,
                    unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                )
            )

            // Last Read Continue Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = EmeraldDark),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable { viewModel.selectSurah(1) }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NoorLifeIcon(NoorLifeIconType.Quran, size = 32.dp, tint = BrightGold, accentTint = LightGold)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("সর্বশেষ পাঠ চালিয়ে যান", style = MaterialTheme.typography.labelSmall, color = LightGold)
                        Text("সূরা আল-ফাতিহা (আয়াত ১-৭)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Text("পড়ুন →", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = BrightGold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Surah List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredSurahs) { surah ->
                    SurahListItem(surah = surah, onClick = { viewModel.selectSurah(surah.number) })
                }
            }
        }
    }
}

@Composable
fun SurahListItem(
    surah: Surah,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.25f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(EmeraldContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${surah.number}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnEmeraldContainer
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = surah.nameBengali,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${surah.meaningBengali} • ${surah.totalVerses} আয়াত • ${surah.revelationType}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = surah.nameArabic,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = EmeraldPrimary
            )
        }
    }
}

@Composable
fun SurahDetailScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val surah by viewModel.selectedSurah.collectAsState()
    var arabicFontSize by remember { mutableStateOf(24.sp) }
    var bengaliFontSize by remember { mutableStateOf(15.sp) }
    var isAudioPlaying by remember { mutableStateOf(false) }
    var shareCardVerse by remember { mutableStateOf<Verse?>(null) }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "সূরা ${surah.nameBengali} (${surah.nameArabic})",
                subtitle = "${surah.revelationType} • মোট আয়াত: ${surah.totalVerses}",
                onBackClick = onNavigateBack
            )
        },
        bottomBar = {
            // Audio Player Bar & Font Controls
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { isAudioPlaying = !isAudioPlaying },
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimary)
                        ) {
                            Icon(
                                imageVector = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isAudioPlaying) "বিরতি" else "তিলাওয়াত শুনুন",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("তিলাওয়াত (মিশারি রশিদ)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                            Text(if (isAudioPlaying) "চলছে..." else "শুনতে চাপুন", style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    // Font scaling buttons
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = {
                            if (arabicFontSize > 18.sp) arabicFontSize = (arabicFontSize.value - 2).sp
                        }) {
                            Text("A-", fontWeight = FontWeight.Bold)
                        }
                        TextButton(onClick = {
                            if (arabicFontSize < 34.sp) arabicFontSize = (arabicFontSize.value + 2).sp
                        }) {
                            Text("A+", fontWeight = FontWeight.Bold)
                        }
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
            // Bismillah Header
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = IvorySurfaceVariant),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                            style = MaterialTheme.typography.headlineMedium.copy(lineHeight = 38.sp),
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "পরম করুণাময়, অসীম দয়ালু আল্লাহর নামে শুরু করছি",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondaryLight
                        )
                    }
                }
            }

            items(surah.verses) { verse ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.25f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${verse.verseNumber}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = OnEmeraldContainer
                                )
                            }

                            Row {
                                IconButton(onClick = {
                                    viewModel.toggleBookmark(
                                        "QURAN",
                                        "${surah.number}:${verse.verseNumber}",
                                        "সূরা ${surah.nameBengali}, আয়াত ${verse.verseNumber}",
                                        verse.translationBengali,
                                        "কুরআনুল কারীম"
                                    )
                                }) {
                                    Icon(Icons.Default.BookmarkBorder, contentDescription = "বুকমার্ক", tint = WarmGold)
                                }
                                IconButton(onClick = { shareCardVerse = verse }) {
                                    Icon(Icons.Default.Share, contentDescription = "শেয়ার", tint = EmeraldPrimary)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Arabic Text
                        Text(
                            text = verse.textArabic,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = arabicFontSize,
                                lineHeight = (arabicFontSize.value * 1.5).sp
                            ),
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Bengali Translation
                        Text(
                            text = verse.translationBengali,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = bengaliFontSize,
                                lineHeight = (bengaliFontSize.value * 1.4).sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // English Translation
                        Text(
                            text = verse.translationEnglish,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Source Attribution
            item {
                SourceVerificationBadge(
                    reference = "কিং ফাহাদ কুরআন কমপ্লেক্স অনুদিত ও ইসলামিক ফাউন্ডেশন বাংলাদেশ অনুমোদিত অনুবাদ"
                )
            }
        }
    }

    // Share Dialog
    shareCardVerse?.let { v ->
        ShareCardDialog(
            title = "সূরা ${surah.nameBengali}, আয়াত ${v.verseNumber}",
            arabicText = v.textArabic,
            translation = v.translationBengali,
            reference = "আল-কুরআন, ${surah.nameBengali}: ${v.verseNumber}",
            onDismiss = { shareCardVerse = null }
        )
    }
}
