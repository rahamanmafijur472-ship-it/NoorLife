package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun AdminCMSScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    var contentType by remember { mutableStateOf("বই / প্রবন্ধ") }
    var contentTitle by remember { mutableStateOf("") }
    var sourceRef by remember { mutableStateOf("") }
    var licenseText by remember { mutableStateOf("পাবলিক ডোমেইন / ওপেন লাইসেন্স") }
    var isSubmitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "অ্যাডমিন ও সিএমএস প্যানেল",
                subtitle = "কন্টেন্ট যাচাইকরণ, মডারেশন ও কপিরাইট নিয়ন্ত্রণ",
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
                    colors = CardDefaults.cardColors(containerColor = EmeraldDark),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NoorLifeIcon(NoorLifeIconType.Settings, size = 26.dp, tint = BrightGold)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("ভূমিকা: ইসলামিক কন্টেন্ট পর্যালোচক (Religious Reviewer)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = BrightGold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "কন্টেন্ট ওয়ার্কফ্লো: খসড়া (Draft) → পর্যালোচনা (Review) → অনুমোদিত (Approved) → প্রকাশিত (Published)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            }

            item {
                IslamicCard {
                    Text("নতুন কন্টেন্ট অন্তর্ভুক্তি ও অনুমোদন ফর্ম", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = contentTitle,
                        onValueChange = { contentTitle = it },
                        label = { Text("কন্টেন্ট বা বইয়ের শিরোনাম") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = sourceRef,
                        onValueChange = { sourceRef = it },
                        label = { Text("মূল প্রামাণ্য সূত্র (অধ্যায় ও পৃষ্ঠা নং সহ)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = licenseText,
                        onValueChange = { licenseText = it },
                        label = { Text("কপিরাইট লাইসেন্স / অনুমতিপত্র প্রমাণ") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { isSubmitted = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Text("যাচাইকরণে জমা দিন", color = Color.White)
                    }

                    if (isSubmitted) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("সফলভাবে জমা হয়েছে এবং পর্যালোচনাধীন রয়েছে।", color = EmeraldPrimary, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            item {
                IslamicCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
                    Text("কপিরাইট টেকডাউন ও রিপোর্ট নীতি", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "কোনো স্বত্বাধিকারী কন্টেন্ট অপসারণের দাবি জানালে তা তাৎক্ষণিকভাবে যাচাই করে প্রয়োজনে আর্কাইভ বা প্রত্যাহার করার পূর্ণ ব্যবস্থা সংরক্ষিত রয়েছে।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val profile by viewModel.userProfile.collectAsState()
    var kidsMode by remember { mutableStateOf(profile?.kidsModeActive ?: false) }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "সেটিংস ও তথ্যাবলী",
                subtitle = "ভাষা, হিসাব পদ্ধতি, প্রাইভেসি ও আইনি শর্তাবলী",
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
                IslamicCard {
                    Text("নামাজ হিসাব পদ্ধতি", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("বর্তমান পদ্ধতি: ইসলামিক ইউনিভার্সিটি করাচি (হানাফী আসর পদ্ধতি)", style = MaterialTheme.typography.bodyMedium)
                }
            }

            item {
                IslamicCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("ছোটদের নিরাপদ মোড (Kids Mode)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                            Text("অনুপযুক্ত বা বাহ্যিক লিংক বন্ধ রাখে", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(checked = kidsMode, onCheckedChange = {
                            kidsMode = it
                            viewModel.updateKidsMode(it)
                        })
                    }
                }
            }

            item {
                IslamicCard {
                    Text("তথ্য সুরক্ষা ও প্রাইভেসি পলিসি", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• নূরলাইফ ব্যবহারকারীর কোনো সংবেদনশীল ধর্মীয় ডাটা বা ব্যক্তিগত তথ্য বিজ্ঞাপনদাতাদের কাছে বিক্রয় করে না।\n• লোকেশন অনুমতি কেবল কিবলা ও নামাজের ওয়াক্ত গণনার উদ্দেশ্যে ব্যবহৃত হয়।\n• ডাটা ব্যাকআপ ও এক্সপোর্টের পূর্ণ অধিকার ব্যবহারকারীর রয়েছে।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            item {
                IslamicCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
                    Text("নূরলাইফ — সংস্করণ ১.০", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "বাংলা ভাষার সর্ববৃহৎ নির্ভরযোগ্য ইসলামিক সুপার অ্যাপ।\nসকল প্রশংসার মালিক মহান আল্লাহ রব্বুল আলামীন।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
