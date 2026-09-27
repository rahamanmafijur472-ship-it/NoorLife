package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.NoorLifeViewModel
import com.example.ui.components.IslamicCard
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun JumuahScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val fridaySunnahs = listOf(
        "১. গোসল করা ও পরিষ্কার পোশাক পরিধান করা" to "জুমার দিন উত্তমরূপে গোসল করা রাসূলুল্লাহ ﷺ এর সুন্নাত।",
        "২. মিসওয়াক ও সুগন্ধি ব্যবহার করা" to "দাঁত পরিষ্কার রাখা এবং আতর বা সুগন্ধি মাখা।",
        "৩. মসজিদে আগে আগে গমন করা" to "প্রথম প্রহরে মসজিদে গেলে একটি উট কোরবানির সওয়াব পাওয়া যায়।",
        "৪. সূরা আল-কাহফ তিলাওয়াত করা" to "এক জুমা থেকে পরবর্তী জুমা পর্যন্ত নুর বা আলোর দিশা লাভ হয়।",
        "৫. রাসূলুল্লাহ ﷺ এর উপর অধিক পরিমাণে দুরুদ পাঠ করা" to "জুমার দিনে পঠিত দুরুদ নবীজি ﷺ এর দরবারে পেশ করা হয়।",
        "৬. দোয়ার বিশেষ মুহূর্ত অনুসন্ধান করা" to "আসরের পর থেকে সূর্যাস্ত পর্যন্ত সময় দোয়া কবুলের বিশেষ সম্ভাবনা থাকে।"
    )

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "পবিত্র জুমার কেন্দ্র",
                subtitle = "সাপ্তাহিক ঈদের দিন ও বরকতময় আমলসমূহ",
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        NoorLifeIcon(NoorLifeIconType.Jumuah, size = 36.dp, tint = BrightGold, accentTint = LightGold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("সাইয়্যিদুল আইয়াম — জুমাতুল মুবারক", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "“দিবসসমূহের মধ্যে সর্বোত্তম দিন হলো জুমার দিন; এই দিনে আদম (আ.)-কে সৃষ্টি করা হয়েছে।” (সহিহ মুসলিম ৮৫৪)",
                            style = MaterialTheme.typography.bodySmall,
                            color = LightGold
                        )
                    }
                }
            }

            item {
                Text("জুমার বিশেষ সুন্নাতসমূহ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            items(fridaySunnahs) { (title, desc) ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.35f))
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = EmeraldDark)
                            Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PurificationScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val wuduSteps = listOf(
        "১. নিয়ত ও বিসমিল্লাহ বলা" to "মনে মনে ওজুর সংকল্প করে 'বিসমিল্লাহ' বলে শুরু করা।",
        "২. দুই হাত কবজি পর্যন্ত ধৌত করা" to "৩ বার উভয় হাত কবজিসহ ভালোভাবে ধৌত করা।",
        "৩. কুলি করা ও মেসওয়াক করা" to "ডান হাতে পানি নিয়ে ৩ বার ভালোভাবে কুলি করা।",
        "৪. নাকে পানি দেওয়া ও পরিষ্কার করা" to "৩ বার নাকে পানি টেনে নিয়ে বাম হাত দিয়ে পরিষ্কার করা।",
        "৫. সমস্ত মুখমণ্ডল ধৌত করা" to "কপালের চুলের গোড়া থেকে থুতনির নিচ এবং এক কানের লতি থেকে অন্য কানের লতি পর্যন্ত ৩ বার ধোয়া।",
        "৬. দুই হাত কনুইসহ ধৌত করা" to "প্রথমে ডান হাত ও পরে বাম হাত কনুইসহ ৩ বার ধোয়া।",
        "৭. মাথা মাসেহ করা" to "উভয় ভেজা হাত দ্বারা মাথার সম্মুখভাগ থেকে পেছনের দিক পর্যন্ত একবার মাসেহ করা।",
        "৮. দুই পা টাখনুসহ ধৌত করা" to "প্রথমে ডান পা ও পরে বাম পা টাখনুসহ আঙুলের ফাঁক দিয়ে ৩ বার ধৌত করা।"
    )

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "ওজু ও পবিত্রতা কেন্দ্র",
                subtitle = "সুন্নাহ মোতাবেক ওজু, গোসল ও তায়াম্মুমের ধারাবাহিক নিয়ম",
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                IslamicCard(backgroundColor = EmeraldContainer) {
                    Text(
                        text = "রাসূলুল্লাহ ﷺ বলেছেন: 'পবিত্রতা ঈমানের অর্ধাংশ।' (সহিহ মুসলিম ২২৩)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = OnEmeraldContainer
                    )
                }
            }

            item {
                Text("ধারাবাহিক ওজুর নিয়ম", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            items(wuduSteps) { (title, desc) ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(desc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}
