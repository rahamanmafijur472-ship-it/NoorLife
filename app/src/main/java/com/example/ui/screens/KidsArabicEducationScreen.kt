package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
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
import com.example.ui.NoorLifeViewModel
import com.example.ui.components.IslamicCard
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun KidsScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    var parentPinEntered by remember { mutableStateOf("") }
    var isPinVerified by remember { mutableStateOf(false) }

    val kidsTopics = listOf(
        "ছোটদের জন্য সংক্ষিপ্ত সূরা" to "সূরা আল-ইখলাস, ফালাক, নাস ও কাউসার সহজ অর্থসহ।",
        "নবী-রাসূলগণের শিক্ষণীয় গল্প" to "হযরত আদম (আ.), নূহ (আ.) ও ইব্রাহিম (আ.) এর সম্মানজনক কাহিনী (কোনো অবমাননাকর চিত্র নেই)।",
        "ইসলামিক আদব ও চরিত্র" to "মা-বাবার প্রতি সম্মান, সত্যবাদিতা এবং বড়দের সালাম দেওয়া।",
        "সহজ ইসলামিক কুইজ" to "ছোট ছোট প্রশ্নের মাধ্যমে আনন্দের সাথে দ্বীন শেখা।"
    )

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "ছোটদের ইসলামিক জোন",
                subtitle = "নিরাপদ, পবিত্র ও শিশু-বান্ধব দ্বীনি শিক্ষা",
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
                    colors = CardDefaults.cardColors(containerColor = GoldContainer),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NoorLifeIcon(NoorLifeIconType.Kids, size = 40.dp, tint = OnGoldContainer, accentTint = WarmGold)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text("আস-সালামু আলাইকুম প্রিয় সোনামণি!", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = OnGoldContainer)
                            Text("এখানে কোনো ক্ষতিকর বিজ্ঞাপন বা অনুপযুক্ত কনটেন্ট নেই।", style = MaterialTheme.typography.bodySmall, color = TextPrimaryLight)
                        }
                    }
                }
            }

            items(kidsTopics.size) { index ->
                val (title, desc) = kidsTopics[index]
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = BrightGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(desc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            item {
                IslamicCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = EmeraldPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("অভিভাবকীয় নিয়ন্ত্রণ (Parental Lock)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "স্ক্রিন টাইম সীমাবদ্ধকরণ ও বয়স ভিত্তিক কনটেন্ট অনুমোদন নিশ্চিত করতে অভিভাবকীয় পিন সক্রিয় রয়েছে।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ArabicScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val arabicLetters = listOf(
        "ا" to "আলিফ", "ب" to "বা", "ت" to "তা", "ث" to "ছা", "ج" to "জিম", "ح" to "হা", "خ" to "খা",
        "د" to "দাল", "ذ" to "যাল", "ر" to "রা", "ز" to "যা", "س" to "সিন", "ش" to "শিন", "ص" to "সোয়াদ",
        "ض" to "দোয়াদ", "ط" to "তোয়া", "ظ" to "যোয়া", "ع" to "আইন", "غ" to "গাইন", "ف" to "ফা",
        "ق" to "ক্বাফ", "ك" to "কাফ", "ل" to "লাম", "م" to "মীম", "ن" to "নূন", "ه" to "হা", "و" to "ওয়াও", "ي" to "ইয়া"
    )

    var selectedLetter by remember { mutableStateOf(arabicLetters.first()) }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "আরবি ভাষা শিক্ষা",
                subtitle = "কুরআনের ভাষা শেখার সহজ সূচনা",
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            // Selected Big Letter Card
            Card(
                colors = CardDefaults.cardColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = selectedLetter.first,
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 72.sp),
                        fontWeight = FontWeight.Bold,
                        color = BrightGold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "হরফ: ${selectedLetter.second}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text("নিচের যে কোনো হরফে চাপ দিয়ে উচ্চারণ দেখুন", style = MaterialTheme.typography.labelSmall, color = LightGold)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text("২৮টি আরবি হরফ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            // Grid of 28 Arabic Letters
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(arabicLetters) { letter ->
                    val isSelected = letter.first == selectedLetter.first
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable { selectedLetter = letter },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) EmeraldContainer else MaterialTheme.colorScheme.surface
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) EmeraldPrimary else Color.LightGray.copy(alpha = 0.4f)
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = letter.first,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) EmeraldPrimary else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = letter.second,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) OnEmeraldContainer else TextSecondaryLight
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
fun EducationScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val courses = listOf(
        "বিশুদ্ধ সালাত শিক্ষা" to "ওজু, তহারাত এবং ৫ ওয়াক্ত সালাতের ধারাবাহিক সহীহ সুন্নাহ পদ্ধতি।",
        "আকায়েদ ও ঈমানিয়াত" to "আল্লাহ, ফেরেশতা, কিতাব, নবী-রাসূল ও আখেরাতের উপর বিশুদ্ধ বিশ্বাস।",
        "সীরাতুন্নবী ﷺ" to "মক্কী ও মাদানী জীবনের ঐতিহাসিক মাইলফলক ও জীবনঘনিষ্ঠ শিক্ষা।"
    )

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "ইসলামিক শিক্ষা ও কোর্স",
                subtitle = "পরিকল্পিত ও বিশুদ্ধ দ্বীন শিক্ষা",
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
            items(courses) { (title, desc) ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NoorLifeIcon(NoorLifeIconType.Education, size = 26.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(desc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                        ) {
                            Text("কোর্স শুরু করুন", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
