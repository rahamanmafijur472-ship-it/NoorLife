package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.NoorLifeViewModel
import com.example.ui.Screen
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

data class MoreMenuItem(
    val title: String,
    val iconType: NoorLifeIconType,
    val targetScreen: Screen,
    val category: String
)

@Composable
fun MoreMenuScreen(
    viewModel: NoorLifeViewModel,
    onNavigate: (Screen) -> Unit
) {
    val items = listOf(
        MoreMenuItem("নামাজ ও ওয়াক্ত", NoorLifeIconType.Prayer, Screen.Prayer, "মূল ইবাদত"),
        MoreMenuItem("আল-কুরআন", NoorLifeIconType.Quran, Screen.Quran, "মূল ইবাদত"),
        MoreMenuItem("সহিহ হাদিস", NoorLifeIconType.Hadith, Screen.Hadith, "মূল ইবাদত"),
        MoreMenuItem("কিবলা কম্পাস", NoorLifeIconType.Qibla, Screen.Qibla, "মূল ইবাদত"),
        MoreMenuItem("দোয়া ও মোনাজাত", NoorLifeIconType.Dua, Screen.Dua, "আমল"),
        MoreMenuItem("ডিজিটাল তসবিহ", NoorLifeIconType.Tasbih, Screen.Tasbih, "আমল"),
        MoreMenuItem("জুমার কেন্দ্র", NoorLifeIconType.Jumuah, Screen.Jumuah, "আমল"),
        MoreMenuItem("ওজু ও পবিত্রতা", NoorLifeIconType.Prayer, Screen.Purification, "আমল"),
        MoreMenuItem("ইসলামিক ক্যালেন্ডার", NoorLifeIconType.Calendar, Screen.Calendar, "সময়"),
        MoreMenuItem("পবিত্র রমজান", NoorLifeIconType.Ramadan, Screen.Ramadan, "সময়"),
        MoreMenuItem("হজ ও ওমরাহ", NoorLifeIconType.Hajj, Screen.Hajj, "ফরজ বিধান"),
        MoreMenuItem("যাকাত ক্যালকুলেটর", NoorLifeIconType.Zakat, Screen.Zakat, "ফরজ বিধান"),
        MoreMenuItem("উত্তরাধিকার বণ্টন", NoorLifeIconType.Inheritance, Screen.Inheritance, "ফরজ বিধান"),
        MoreMenuItem("ডিজিটাল লাইব্রেরি", NoorLifeIconType.Library, Screen.Library, "জ্ঞান"),
        MoreMenuItem("ছোটদের জোন", NoorLifeIconType.Kids, Screen.Kids, "জ্ঞান"),
        MoreMenuItem("আরবি ভাষা শিক্ষা", NoorLifeIconType.Arabic, Screen.Arabic, "জ্ঞান"),
        MoreMenuItem("ইসলামিক কোর্স", NoorLifeIconType.Education, Screen.Education, "জ্ঞান"),
        MoreMenuItem("কাছের মসজিদ", NoorLifeIconType.Mosque, Screen.MosqueFinder, "স্থান"),
        MoreMenuItem("নূর এআই সহকারী", NoorLifeIconType.AI, Screen.AIAssistant, "বুদ্ধিমত্তা"),
        MoreMenuItem("আমার আমল ও শেখা", NoorLifeIconType.Personal, Screen.PersonalDeeds, "আমল"),
        MoreMenuItem("সার্বজনীন অনুসন্ধান", NoorLifeIconType.Quran, Screen.GlobalSearch, "সেবা"),
        MoreMenuItem("বুকমার্ক", NoorLifeIconType.Quran, Screen.Bookmarks, "সেবা"),
        MoreMenuItem("অ্যাডমিন ও সিএমএস", NoorLifeIconType.Settings, Screen.AdminCMS, "প্রশাসন"),
        MoreMenuItem("সেটিংস ও তথ্য", NoorLifeIconType.Settings, Screen.Settings, "প্রশাসন")
    )

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "নূরলাইফ — সকল ফিচার ও মডিউল",
                subtitle = "সম্পূর্ণ ইসলামিক সুপার অ্যাপের সকল সেবা এক নজরে"
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(items) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.92f)
                        .clickable { onNavigate(item.targetScreen) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.35f)),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            NoorLifeIcon(
                                type = item.iconType,
                                size = 26.dp,
                                tint = MaterialTheme.colorScheme.primary,
                                accentTint = WarmGold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}
