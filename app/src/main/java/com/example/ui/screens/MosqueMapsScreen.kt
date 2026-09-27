package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.NoorLifeViewModel
import com.example.ui.components.NoorLifeTopBar
import com.example.ui.components.SourceVerificationBadge
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.theme.*

@Composable
fun MosqueFinderScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val mosques = remember { viewModel.repository.getNearbyMosques() }
    val places = remember { viewModel.repository.getHistoricalPlaces() }

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "কাছের মসজিদ ও ঐতিহাসিক স্থান",
                subtitle = "যাচাইকৃত মসজিদ ও ইসলামি ইতিহাসের পূতপবিত্র স্থানসমূহ",
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
                Text("নিকটস্থ মসজিদসমূহ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            items(mosques) { mosque ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                NoorLifeIcon(NoorLifeIconType.Mosque, size = 26.dp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(mosque.nameBengali, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                            }
                            Text("${mosque.distanceKm} কি.মি.", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(mosque.addressBengali, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            mosque.facilities.forEach { fac ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = EmeraldContainer.copy(alpha = 0.5f)
                                ) {
                                    Text(fac, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), color = OnEmeraldContainer)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                            ) {
                                Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("দিকনির্দেশনা")
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("পবিত্র ও ঐতিহাসিক স্থানসমূহ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            items(places) { place ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(place.nameBengali, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        Text("${place.nameArabic} • ${place.location}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(place.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(6.dp))
                        SourceVerificationBadge(reference = place.significance)
                    }
                }
            }
        }
    }
}
