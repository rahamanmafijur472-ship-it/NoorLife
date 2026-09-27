package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.NoorLifeViewModel
import com.example.ui.components.IslamicCard
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.components.SourceVerificationBadge
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun AIAssistantScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    var queryText by remember { mutableStateOf("") }
    val response by viewModel.aiResponse.collectAsState()
    val isLoading by viewModel.aiQueryLoading.collectAsState()

    val quickQuestions = listOf(
        "দৈনিক ৫ ওয়াক্ত নামাজের গুরুত্ব কী?",
        "রমজানের রোজার প্রধান শর্তগুলো কী কী?",
        "কাদের উপর যাকাত ফরজ হয় এবং পরিমাণ কত?"
    )

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "নূর এআই স্টাডি সহকারী",
                subtitle = "বিশুদ্ধ কুরআন ও সহীহ সুন্নাহ ভিত্তিক নির্ভরযোগ্য তথ্য সেবা",
                onBackClick = onNavigateBack
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = queryText,
                        onValueChange = { queryText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("ইসলামি জিজ্ঞাসা লিখুন...") },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            viewModel.askAIAssistant(queryText)
                            queryText = ""
                        },
                        enabled = queryText.isNotBlank() && !isLoading,
                        colors = IconButtonDefaults.iconButtonColors(containerColor = EmeraldPrimary)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "পাঠান", tint = Color.White)
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
            // Strict Disclaimer Banner
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SoftAmber.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SoftAmber)
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = SoftAmber)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "গুরুত্বপূর্ণ ধর্মীয় সতর্কতা: নূর এআই কোনো মুফতি নয় এবং কোনো প্রকার ব্যক্তিগত ফতোয়া প্রদান করে না। এটি কেবল যাচাইকৃত ইসলামিক বই ও দলিলের ভিত্তিতে শিক্ষামূলক তথ্য সরবরাহ করে।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            item {
                Text("দ্রুত জেনে নিন", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            }

            items(quickQuestions.size) { i ->
                val q = quickQuestions[i]
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.3f)),
                    onClick = { viewModel.askAIAssistant(q) }
                ) {
                    Text(
                        text = "• $q",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = EmeraldDark,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            if (isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = EmeraldPrimary)
                    }
                }
            }

            // AI Answer Display
            response?.let { res ->
                item {
                    IslamicCard(backgroundColor = IvorySurface) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NoorLifeIcon(NoorLifeIconType.AI, size = 26.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("জিজ্ঞাসা: ${res.question}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = res.answerBengali,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        SourceVerificationBadge(reference = res.primarySource)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = res.scholarAdviceNote,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondaryLight
                        )
                    }
                }
            }
        }
    }
}
