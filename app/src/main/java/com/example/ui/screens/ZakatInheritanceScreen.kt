package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ui.NoorLifeViewModel
import com.example.ui.components.IslamicCard
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun ZakatScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    var cashText by remember { mutableStateOf("") }
    var goldText by remember { mutableStateOf("") }
    var silverText by remember { mutableStateOf("") }
    var businessStockText by remember { mutableStateOf("") }
    var debtsText by remember { mutableStateOf("") }

    val zakatResult by viewModel.zakatResult.collectAsState()

    fun recalculate() {
        val cash = cashText.toDoubleOrNull() ?: 0.0
        val gold = goldText.toDoubleOrNull() ?: 0.0
        val silver = silverText.toDoubleOrNull() ?: 0.0
        val stock = businessStockText.toDoubleOrNull() ?: 0.0
        val debts = debtsText.toDoubleOrNull() ?: 0.0
        viewModel.calculateZakat(cash, gold, silver, stock, 0.0, debts)
    }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "যাকাত ক্যালকুলেটর",
                subtitle = "সম্পদ পবিত্রকরণ ও সঠিক হিসাব নির্ধারণ",
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
            // Result Banner Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NoorLifeIcon(NoorLifeIconType.Zakat, size = 32.dp, tint = BrightGold, accentTint = LightGold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("প্রদেয় যাকাত (২.৫%)", style = MaterialTheme.typography.labelSmall, color = LightGold)
                        Text(
                            text = "৳ ${String.format("%,.0f", zakatResult.zakatPayable)}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = BrightGold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (zakatResult.isZakatEligible) "আপনার উপর যাকাত ফরজ হয়েছে" else "সম্পদ নিসাব পরিমাণের নিচে",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            }

            // Input Fields
            item {
                IslamicCard {
                    Text("সম্পদ ও দায়ের বিবরণ দিন (টাকা/গ্রাম)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = cashText,
                        onValueChange = { cashText = it; recalculate() },
                        label = { Text("নগদ টাকা ও ব্যাংক ব্যালেন্স (৳)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = goldText,
                        onValueChange = { goldText = it; recalculate() },
                        label = { Text("স্বর্ণের পরিমাণ (গ্রাম হিসেবে)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = silverText,
                        onValueChange = { silverText = it; recalculate() },
                        label = { Text("রুপার পরিমাণ (গ্রাম হিসেবে)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = businessStockText,
                        onValueChange = { businessStockText = it; recalculate() },
                        label = { Text("ব্যবসায়িক পণ্য ও শেয়ারের বর্তমান মূল্য (৳)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = debtsText,
                        onValueChange = { debtsText = it; recalculate() },
                        label = { Text("তাত্ক্ষণিক ঋণ ও অপরিশোধিত দেনা (মাইনাস হবে)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                IslamicCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
                    Text("নিসাব ও ধর্মীয় শর্তাবলী", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• রুপার নিসাব: ৫২.৫ তোলা / ৬১২.৩৬ গ্রাম (বর্তমান বাজারদর প্রায় ৳১,৩৪,০০০)।\n• সম্পদের উপর পূর্ণ ১ চান্দ্র বছর অতিবাহিত হওয়া আবশ্যক।\n• এটি একটি শিক্ষামূলক ক্যালকুলেটর। বিশেষ ও জটিল মাসআলায় বিশ্বস্ত আলেমের পরামর্শ নিন।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun InheritanceScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    var estateText by remember { mutableStateOf("1000000") }
    var hasHusband by remember { mutableStateOf(false) }
    var hasWife by remember { mutableStateOf(true) }
    var sonsCount by remember { mutableStateOf(1) }
    var daughtersCount by remember { mutableStateOf(1) }
    var hasFather by remember { mutableStateOf(false) }
    var hasMother by remember { mutableStateOf(true) }

    val shares by viewModel.inheritanceShares.collectAsState()

    fun calculate() {
        val estate = estateText.toDoubleOrNull() ?: 0.0
        viewModel.calculateInheritance(estate, hasHusband, hasWife, sonsCount, daughtersCount, hasFather, hasMother)
    }

    LaunchedEffect(Unit) {
        calculate()
    }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "ইসলামিক উত্তরাধিকার বণ্টন (ফারায়েজ)",
                subtitle = "পবিত্র কুরআনুল কারীমের সূরা নিসার অকাট্য বিধান অনুযায়ী",
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
                    Text("মোট রেখে যাওয়া সম্পত্তি (টাকা হিসেবে)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = estateText,
                        onValueChange = { estateText = it; calculate() },
                        label = { Text("সম্পত্তির মূল্য (৳)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                IslamicCard {
                    Text("ওয়ারিশগণের উপস্থিতি নির্বাচন করুন", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("স্ত্রী আছেন কি?")
                        Switch(checked = hasWife, onCheckedChange = { hasWife = it; if (it) hasHusband = false; calculate() })
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("মাতা জীবিত আছেন?")
                        Switch(checked = hasMother, onCheckedChange = { hasMother = it; calculate() })
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("পিতা জীবিত আছেন?")
                        Switch(checked = hasFather, onCheckedChange = { hasFather = it; calculate() })
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("পুত্র সন্তান সংখ্যা: $sonsCount")
                        Row {
                            TextButton(onClick = { if (sonsCount > 0) { sonsCount--; calculate() } }) { Text("-") }
                            TextButton(onClick = { sonsCount++; calculate() }) { Text("+") }
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("কন্যা সন্তান সংখ্যা: $daughtersCount")
                        Row {
                            TextButton(onClick = { if (daughtersCount > 0) { daughtersCount--; calculate() } }) { Text("-") }
                            TextButton(onClick = { daughtersCount++; calculate() }) { Text("+") }
                        }
                    }
                }
            }

            item {
                Text("কুরআনিক অংশীদারিত্বের ফলাফল", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            items(shares) { share ->
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
                        Column {
                            Text(share.relation, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                            Text(share.shareFractionText, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text(
                            text = "৳ ${String.format("%,.0f", share.amountBDT)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldPrimary
                        )
                    }
                }
            }
        }
    }
}
