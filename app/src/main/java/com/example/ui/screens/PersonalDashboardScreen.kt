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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.NoorLifeViewModel
import com.example.ui.Screen
import com.example.ui.components.IslamicCard
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun PersonalDeedsScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    var miswakDone by remember { mutableStateOf(true) }
    var salamDone by remember { mutableStateOf(false) }
    var quranDone by remember { mutableStateOf(true) }
    var charityDone by remember { mutableStateOf(false) }
    var istighfarDone by remember { mutableStateOf(true) }

    val habits = listOf(
        "দৈনিক মেসওয়াক করা (সুন্নাত)" to (miswakDone to { miswakDone = !miswakDone }),
        "পরিচিত-অপরিচিত সকলকে সালাম দেওয়া" to (salamDone to { salamDone = !salamDone }),
        "কমপক্ষে ১ পৃষ্ঠা কুরআন তিলাওয়াত" to (quranDone to { quranDone = !quranDone }),
        "সামান্য হলেও সাদাকাহ বা দান করা" to (charityDone to { charityDone = !charityDone }),
        "অন্তত ১০০ বার ইস্তিগফার ও তওবা করা" to (istighfarDone to { istighfarDone = !istighfarDone })
    )

    val completedCount = listOf(miswakDone, salamDone, quranDone, charityDone, istighfarDone).count { it }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "আমার আমল ও শেখা",
                subtitle = "দৈনন্দিন সুন্নাহ ও নেক আমল ট্র্যাকিং (কোনো অহংকার বা প্রতিযোগিতা ব্যতিরেকে)",
                onBackClick = onNavigateBack
            )
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
                    colors = CardDefaults.cardColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NoorLifeIcon(NoorLifeIconType.Personal, size = 34.dp, tint = BrightGold, accentTint = LightGold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("আজকের সম্পন্ন আমল: $completedCount / 5", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { completedCount / 5f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = BrightGold,
                            trackColor = EmeraldContainer
                        )
                    }
                }
            }

            item {
                Text("আজকের সুন্নাহ আমলসমূহ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            items(habits) { (title, pair) ->
                val (isDone, toggle) = pair
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDone) EmeraldContainer.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surface
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isDone) EmeraldPrimary else Color.LightGray.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable { toggle() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (isDone) EmeraldPrimary else Color.Transparent)
                                .border(2.dp, if (isDone) EmeraldPrimary else Color.Gray, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isDone) Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            item {
                IslamicCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
                    Text("নিয়তের আন্তরিকতা ও ইখলাস", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "আমলের মূল লক্ষ্য কেবল আল্লাহর সন্তুষ্টি অর্জন করা। নূরলাইফ কোনো ব্যবহারকারীর ইবাদতের ভিত্তিতে অন্যদের সাথে কোনো অসম প্রতিযোগিতা তৈরি করে না।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarksScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val bookmarks by viewModel.bookmarks.collectAsState()

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "সংরক্ষিত বিষয় ও বুকমার্ক",
                subtitle = "আপনার প্রিয় আয়াত, হাদিস, দোয়া ও বই",
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        if (bookmarks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    NoorLifeIcon(NoorLifeIconType.Quran, size = 48.dp, tint = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("কোনো বুকমার্ক সংরক্ষিত নেই", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(bookmarks) { item ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = EmeraldDark)
                                Text(item.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("সূত্র: ${item.reference}", style = MaterialTheme.typography.labelSmall, color = EmeraldPrimary)
                            }
                            IconButton(onClick = { viewModel.toggleBookmark(item.contentType, item.contentId, "", "", "") }) {
                                Icon(Icons.Default.Delete, contentDescription = "মুছুন", tint = SoftRed)
                            }
                        }
                    }
                }
            }
        }
    }
}
