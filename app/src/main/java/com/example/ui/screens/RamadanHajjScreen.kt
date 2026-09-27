package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun RamadanScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "পবিত্র মাহে রমজান ও রোজা",
                subtitle = "সেহরি, ইফতার ও সিয়াম পালন সংক্রান্ত সার্বিক নির্দেশিকা",
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NoorLifeIcon(NoorLifeIconType.Ramadan, size = 36.dp, tint = BrightGold, accentTint = LightGold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("রমজানুল মুবারক প্রস্তুতি", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("আজকের শেষ সেহরি", style = MaterialTheme.typography.labelSmall, color = LightGold)
                                Text("০৪:৩২", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("আজকের ইফতার", style = MaterialTheme.typography.labelSmall, color = LightGold)
                                Text("০৬:০৩", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = BrightGold)
                            }
                        }
                    }
                }
            }

            item {
                IslamicCard {
                    Text("ইফতারের মাসনূন দোয়া", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ",
                        style = MaterialTheme.typography.titleMedium,
                        color = EmeraldPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "উচ্চারণ: জাহাবাজ জামা'উ ওয়াবতাল্লাতিল উরূকু ওয়া ছাবাতাল আজরু ইনশাআল্লাহ।\nঅর্থ: পিপাসা দূরীভূত হলো, শিরা-উপশিরা সিক্ত হলো এবং আল্লাহর ইচ্ছায় সওয়াব নির্ধারিত হলো।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            item {
                IslamicCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
                    Text("রোজা ভঙ্গের কারণসমূহ (সতর্কতা)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "১. ইচ্ছাকৃত পানাহার বা ধূমপান করা।\n২. স্ত্রী সহবাসে লিপ্ত হওয়া।\n৩. ইচ্ছাকৃত মুখভরে বমি করা।\n৪. পুষ্টিকর ইনজেকশন বা স্যালাইন গ্রহণ করা।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun HajjScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val hajjSteps = listOf(
        "১. ইহরাম ও তালবিয়া পাঠ" to "৮ই জিলহজ হজের নিয়ত করে ইহরাম পরিধান ও লাব্বাইক পাঠ করা।",
        "২. মিনায় অবস্থান" to "৮ই জিলহজ মিনায় পৌঁছে পাঁচ ওয়াক্ত সালাত আদায় ও রাতযাপন।",
        "৩. আরাফাতের ময়দানে অবস্থান (হজের মূল রুকন)" to "৯ই জিলহজ সূর্য হেলে পড়ার পর থেকে সূর্যাস্ত পর্যন্ত অবস্থান ও খুতবা শ্রবণ।",
        "৪. মুযদালিফায় রাতযাপন ও কঙ্কর সংগ্রহ" to "৯ই জিলহজ সূর্যাস্তের পর মাগরিব ও ইশা একত্রে আদায় ও রাতযাপন।",
        "৫. কঙ্কর নিক্ষেপ (রমী), দমে শোকর (কোরবানি) ও হলক" to "১০ই জিলহজ বড় জামারায় ৭টি কঙ্কর নিক্ষেপ, কোরবানি ও মাথা মুণ্ডন।",
        "৬. তাওয়াফে যিয়ারত ও সাঈ" to "কাবা শরীফের ফরজ তাওয়াফ সম্পাদন এবং সাফা-মারওয়ায় সাঈ।",
        "৭. তাওয়াফে বিদা (বিদায়ী তাওয়াফ)" to "মক্কা মুকাররমা ত্যাগের পূর্বে শেষ তাওয়াফ সম্পাদন।"
    )

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "হজ ও ওমরাহ সম্পূর্ণ গাইড",
                subtitle = "রাসূলুল্লাহ ﷺ এর সুন্নাহ মোতাবেক ধারাবাহিক রুকন ও নিয়মাবলী",
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
                    colors = CardDefaults.cardColors(containerColor = EmeraldDark),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NoorLifeIcon(NoorLifeIconType.Hajj, size = 38.dp, tint = BrightGold, accentTint = LightGold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "لَبَّيْكَ اللَّهُمَّ لَبَّيْكَ، لَبَّيْكَ لاَ شَرِيكَ لَكَ لَبَّيْكَ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = BrightGold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "“হজব্রত পালনকারীদের জন্য জান্নাত ব্যতীত অন্য কোনো প্রতিদান নেই।” (বুখারী ১৭৭৩)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            }

            items(hajjSteps) { (title, desc) ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(desc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}
