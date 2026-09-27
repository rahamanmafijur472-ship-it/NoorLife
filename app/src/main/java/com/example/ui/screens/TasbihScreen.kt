package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun TasbihScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val currentZikr by viewModel.currentZikr.collectAsState()
    val presets by viewModel.zikrPresets.collectAsState()
    val count by viewModel.tasbihCount.collectAsState()
    val target by viewModel.tasbihTarget.collectAsState()

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "ডিজিটাল তসবিহ",
                subtitle = "আল্লাহর জিকিরে অন্তরের প্রশান্তি",
                onBackClick = onNavigateBack,
                actions = {
                    IconButton(onClick = { viewModel.resetTasbih() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "রিসেট", tint = EmeraldPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Preset Zikr Selector
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "জিকির নির্বাচন করুন",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(presets) { zikr ->
                        val isSelected = zikr.id == currentZikr.id
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.selectZikr(zikr) },
                            label = { Text(zikr.pronunciationBengali) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EmeraldPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Current Zikr Info Card
            Card(
                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                shape = RoundedCornerShape(18.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = currentZikr.textArabic,
                        style = MaterialTheme.typography.headlineMedium.copy(lineHeight = 36.sp),
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = currentZikr.meaningBengali,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "ফজিলত: ${currentZikr.reward}",
                        style = MaterialTheme.typography.labelSmall,
                        color = EmeraldPrimary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Big Interactive Circular Counter Button
            Box(
                modifier = Modifier
                    .size(230.dp)
                    .clip(CircleShape)
                    .background(EmeraldPrimary)
                    .border(6.dp, WarmGold, CircleShape)
                    .clickable { viewModel.incrementTasbih() },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$count",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 56.sp),
                        fontWeight = FontWeight.Bold,
                        color = BrightGold
                    )
                    Text(
                        text = "লক্ষ্য: $target বার",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "চাপুন",
                        style = MaterialTheme.typography.labelSmall,
                        color = LightGold
                    )
                }
            }

            // Bottom Progress & Target Controls
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val progress = if (target > 0) (count.toFloat() / target).coerceIn(0f, 1f) else 0f
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = BrightGold,
                    trackColor = EmeraldContainer
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { viewModel.resetTasbih() }) {
                        Text("পুনরায় শুরু করুন", color = SoftRed, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "সূত্র: ${currentZikr.reference}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondaryLight
                    )
                }
            }
        }
    }
}
