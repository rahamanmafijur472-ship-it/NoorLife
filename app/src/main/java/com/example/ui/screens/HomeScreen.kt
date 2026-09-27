package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.NoorLifeViewModel
import com.example.ui.Screen
import com.example.ui.components.*
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: NoorLifeViewModel,
    onNavigate: (Screen) -> Unit
) {
    val prayerSchedule by viewModel.prayerSchedule.collectAsState()
    val nextPrayer by viewModel.nextPrayerInfo.collectAsState()
    var shareCardData by remember { mutableStateOf<Triple<String, String, String>?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // --- 1. Top Islamic Header & Dates ---
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(EmeraldDark, EmeraldPrimary)
                        )
                    )
                    .statusBarsPadding()
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(EmeraldDark)
                                    .border(1.dp, BrightGold, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                NoorLifeIcon(
                                    type = NoorLifeIconType.Home,
                                    size = 26.dp,
                                    tint = BrightGold,
                                    accentTint = LightGold
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "নূরলাইফ — নূরুল ইসলাম",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = prayerSchedule.locationName,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = LightGold
                                )
                            }
                        }

                        Row {
                            IconButton(
                                onClick = { onNavigate(Screen.GlobalSearch) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.15f))
                            ) {
                                Icon(Icons.Default.Search, contentDescription = "অনুসন্ধান", tint = Color.White)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = { onNavigate(Screen.Bookmarks) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.15f))
                            ) {
                                Icon(Icons.Default.BookmarkBorder, contentDescription = "বুকমার্ক", tint = BrightGold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Three Calendars Box
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.12f)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = prayerSchedule.dateHijri,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = LightGold
                                )
                                Text(
                                    text = "${prayerSchedule.dateBengali} • ${prayerSchedule.dateGregorian}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                            NoorLifeIcon(
                                type = NoorLifeIconType.Calendar,
                                size = 28.dp,
                                tint = BrightGold,
                                accentTint = LightGold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Next Prayer Countdown Card
                    Card(
                        colors = CardDefaults.cardColors(containerColor = IvorySurface),
                        shape = RoundedCornerShape(18.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier.clickable { onNavigate(Screen.Prayer) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(38.dp)
                                            .clip(CircleShape)
                                            .background(EmeraldContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        NoorLifeIcon(
                                            type = NoorLifeIconType.Prayer,
                                            size = 22.dp,
                                            tint = EmeraldPrimary,
                                            accentTint = WarmGold
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "পরবর্তী নামাজ: ${nextPrayer.prayerNameBengali}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldDark
                                        )
                                        Text(
                                            text = nextPrayer.remainingTimeFormatted,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = EmeraldLight,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                Text(
                                    text = nextPrayer.prayerTime,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            LinearProgressIndicator(
                                progress = { nextPrayer.progressFraction },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = WarmGold,
                                trackColor = EmeraldContainer.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }

        // --- 2. Quick Actions Grid ---
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                Text(
                    text = "দ্রুত সেবা ও আমল",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    QuickActionItem("আল-কুরআন", NoorLifeIconType.Quran, { onNavigate(Screen.Quran) }, Modifier.weight(1f))
                    QuickActionItem("হাদিস শরীফ", NoorLifeIconType.Hadith, { onNavigate(Screen.Hadith) }, Modifier.weight(1f))
                    QuickActionItem("দোয়া ও জিকির", NoorLifeIconType.Dua, { onNavigate(Screen.Dua) }, Modifier.weight(1f))
                    QuickActionItem("ডিজিটাল তসবিহ", NoorLifeIconType.Tasbih, { onNavigate(Screen.Tasbih) }, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    QuickActionItem("কিবলা দিক", NoorLifeIconType.Qibla, { onNavigate(Screen.Qibla) }, Modifier.weight(1f))
                    QuickActionItem("রমজান সেন্টার", NoorLifeIconType.Ramadan, { onNavigate(Screen.Ramadan) }, Modifier.weight(1f))
                    QuickActionItem("হজ ও ওমরাহ", NoorLifeIconType.Hajj, { onNavigate(Screen.Hajj) }, Modifier.weight(1f))
                    QuickActionItem("যাকাত হিসাব", NoorLifeIconType.Zakat, { onNavigate(Screen.Zakat) }, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    QuickActionItem("জুমার কেন্দ্র", NoorLifeIconType.Jumuah, { onNavigate(Screen.Jumuah) }, Modifier.weight(1f))
                    QuickActionItem("লাইব্রেরি", NoorLifeIconType.Library, { onNavigate(Screen.Library) }, Modifier.weight(1f))
                    QuickActionItem("কাছের মসজিদ", NoorLifeIconType.Mosque, { onNavigate(Screen.MosqueFinder) }, Modifier.weight(1f))
                    QuickActionItem("নূর এআই", NoorLifeIconType.AI, { onNavigate(Screen.AIAssistant) }, Modifier.weight(1f))
                }
            }
        }

        // --- 3. Daily Quran Ayah Card ---
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                IslamicCard(
                    onClick = { onNavigate(Screen.Quran) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NoorLifeIcon(type = NoorLifeIconType.Quran, size = 24.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "আজকের আয়াত",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                        IconButton(onClick = {
                            shareCardData = Triple(
                                "আজকের আয়াত",
                                "إِنَّ مَعَ الْعُسْرِ يُسْرًا",
                                "নিশ্চয়ই কষ্টের সাথে স্বস্তি রয়েছে। (সূরা আশ-শারহ: ৬)"
                            )
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "শেয়ার", tint = WarmGold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "إِنَّ مَعَ الْعُسْرِ يُسْرًا",
                        style = MaterialTheme.typography.titleLarge.copy(lineHeight = 32.sp),
                        textAlign = TextAlign.End,
                        color = EmeraldPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "“নিশ্চয়ই কষ্টের সাথেই স্বস্তি রয়েছে।”",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SourceVerificationBadge(reference = "সূরা আশ-শারহ (৯৪), আয়াত নং ৬")
                }
            }
        }

        // --- 4. Daily Hadith Card ---
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                IslamicCard(
                    onClick = { onNavigate(Screen.Hadith) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NoorLifeIcon(type = NoorLifeIconType.Hadith, size = 24.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "আজকের হাদিস",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                        IconButton(onClick = {
                            shareCardData = Triple(
                                "আজকের হাদিস",
                                "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ",
                                "নিশ্চয়ই সমস্ত আমল নিয়তের উপর নির্ভরশীল। (সহিহুল বুখারী, হাদিস ১)"
                            )
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "শেয়ার", tint = WarmGold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى",
                        style = MaterialTheme.typography.titleMedium.copy(lineHeight = 28.sp),
                        textAlign = TextAlign.End,
                        color = EmeraldDark,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "“নিশ্চয়ই সমস্ত কাজ নিয়তের উপর নির্ভরশীল। আর প্রত্যেক ব্যক্তি যা নিয়ত করেছে তাই পাবে।”",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SourceVerificationBadge(reference = "সহিহুল বুখারী, কিতাবুল ওহী, হাদিস নং ১ (সহিহ)")
                }
            }
        }

        // --- 5. Today's Sunnah & Personal Deeds Progress ---
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                IslamicCard(
                    backgroundColor = GoldContainer.copy(alpha = 0.4f),
                    borderColor = WarmGold,
                    onClick = { onNavigate(Screen.PersonalDeeds) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NoorLifeIcon(type = NoorLifeIconType.Personal, size = 24.dp, tint = OnGoldContainer)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "আমার আমল ও শেখা",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnGoldContainer
                            )
                        }
                        Text(
                            text = "বিস্তারিত দেখুন →",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "আজকের সুন্নাহ আমল: মিসওয়াক করা, সালামের প্রসার ঘটানো এবং তাহাজ্জুদের প্রস্তুতি নেওয়া।",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimaryLight
                    )
                }
            }
        }
    }

    // Share Card Dialog
    shareCardData?.let { (title, arabic, translation) ->
        ShareCardDialog(
            title = title,
            arabicText = arabic,
            translation = translation,
            reference = "নূরলাইফ অ্যাপ থেকে সংগৃহীত",
            onDismiss = { shareCardData = null }
        )
    }
}
