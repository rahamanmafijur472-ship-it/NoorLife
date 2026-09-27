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
import androidx.compose.material.icons.filled.Notifications
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
fun PrayerScreen(
    viewModel: NoorLifeViewModel,
    onNavigate: (Screen) -> Unit
) {
    val schedule by viewModel.prayerSchedule.collectAsState()
    val nextPrayer by viewModel.nextPrayerInfo.collectAsState()

    // Completed prayer states for today
    var fajrDone by remember { mutableStateOf(false) }
    var dhuhrDone by remember { mutableStateOf(false) }
    var asrDone by remember { mutableStateOf(false) }
    var maghribDone by remember { mutableStateOf(false) }
    var ishaDone by remember { mutableStateOf(false) }
    var tahajjudDone by remember { mutableStateOf(false) }

    val prayerList = listOf(
        Triple("ফজর", schedule.fajr, fajrDone) to { fajrDone = !fajrDone },
        Triple("সূর্যোদয় (নামাজ নিষেধ)", schedule.sunrise, false) to {},
        Triple("যোহর", schedule.dhuhr, dhuhrDone) to { dhuhrDone = !dhuhrDone },
        Triple("আসর", schedule.asr, asrDone) to { asrDone = !asrDone },
        Triple("মাগরিব", schedule.maghrib, maghribDone) to { maghribDone = !maghribDone },
        Triple("ইশা", schedule.isha, ishaDone) to { ishaDone = !ishaDone },
        Triple("বিতর", schedule.witr, false) to {},
        Triple("তাহাজ্জুদ", schedule.tahajjud, tahajjudDone) to { tahajjudDone = !tahajjudDone }
    )

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "নামাজ ও ওয়াক্ত",
                subtitle = "${schedule.locationName} • ${schedule.dateHijri}"
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Next Prayer Banner
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("পরবর্তী ওয়াক্ত", style = MaterialTheme.typography.labelMedium, color = LightGold)
                            Text(nextPrayer.prayerNameBengali, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(nextPrayer.remainingTimeFormatted, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.9f))
                        }
                        Text(nextPrayer.prayerTime, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = BrightGold)
                    }
                }
            }

            // Prohibited Times Alert
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SoftAmber.copy(alpha = 0.12f)),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SoftAmber.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NoorLifeIcon(NoorLifeIconType.Prayer, size = 22.dp, tint = SoftAmber)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "নামাজ আদায়ের নিষিদ্ধ সময়: সূর্যোদয়ের সময়, ঠিক দ্বিপ্রহরে এবং সূর্যাস্তের সময়।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Daily Prayer Timetable
            item {
                Text(
                    text = "আজকের নামাজের সময়সূচি ও আমল ট্র্যাকিং",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(prayerList) { (item, toggle) ->
                val (name, time, isDone) = item
                val isProhibited = name.contains("নিষেধ")
                val isNext = name == nextPrayer.prayerNameBengali

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isNext) EmeraldContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        if (isNext) 1.5.dp else 1.dp,
                        if (isNext) WarmGold else Color.LightGray.copy(alpha = 0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (!isProhibited) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(if (isDone) EmeraldPrimary else Color.Transparent)
                                        .border(2.dp, if (isDone) EmeraldPrimary else Color.Gray, CircleShape)
                                        .clickable { toggle() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isDone) {
                                        Icon(Icons.Default.Check, contentDescription = "সম্পন্ন", tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                            }
                            Column {
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = if (isNext) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isProhibited) SoftAmber else MaterialTheme.colorScheme.onSurface
                                )
                                if (isNext) {
                                    Text("বর্তমান / পরবর্তী ওয়াক্ত", style = MaterialTheme.typography.labelSmall, color = EmeraldPrimary)
                                }
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = time,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isNext) EmeraldPrimary else MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = "আজান নোটিফিকেশন চালু",
                                tint = if (isNext) WarmGold else Color.Gray.copy(alpha = 0.6f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Calculation Method Card
            item {
                IslamicCard(
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text("হিসাব পদ্ধতি ও মাযহাব সেটিংস", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "পদ্ধতি: ইসলামিক ইউনিভার্সিটি করাচি (বাংলাদেশ ও ভারত উপমহাদেশে সর্বজনস্বীকৃত)\nআসর পদ্ধতি: হানাফী (যোহর শেষ থেকে ২ গুণ ছায়া)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
