package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
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
fun QiblaScreen(
    viewModel: NoorLifeViewModel,
    onNavigateBack: () -> Unit
) {
    val qiblaAngle = remember { viewModel.repository.calculateQiblaAngle() } // 273.5° from Dhaka
    val distanceToMakkah = remember { viewModel.repository.calculateDistanceToMakkah() } // ~5050 km

    // Simulated compass heading (smooth facing indicator)
    var currentHeading by remember { mutableStateOf(0f) }
    val animatedRotation by animateFloatAsState(targetValue = (qiblaAngle.toFloat() - currentHeading), label = "compass")

    Scaffold(
        topBar = {
            NoorLifeTopBar(
                title = "কিবলা কম্পাস",
                subtitle = "মক্কা মুকাররমা অভিমুখী দিক নির্দেশনা",
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Stats Card
            Card(
                colors = CardDefaults.cardColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("কিবলার কোণ", style = MaterialTheme.typography.labelSmall, color = LightGold)
                        Text("${qiblaAngle}° পশ্চিম-উত্তর", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("ঢাকা, বাংলাদেশ হতে", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.85f))
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("পবিত্র কাবার দূরত্ব", style = MaterialTheme.typography.labelSmall, color = LightGold)
                        Text("$distanceToMakkah কি.মি.", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = BrightGold)
                    }
                }
            }

            // Compass Dial View
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
                    .background(IvorySurface),
                contentAlignment = Alignment.Center
            ) {
                // Compass Outer Dial with Degrees
                Canvas(modifier = Modifier.size(260.dp)) {
                    val radius = size.minDimension / 2
                    val center = Offset(size.width / 2, size.height / 2)

                    drawCircle(EmeraldPrimary.copy(alpha = 0.1f), radius = radius, center = center)
                    drawCircle(WarmGold, radius = radius, center = center, style = Stroke(3.dp.toPx()))

                    // Compass tick marks
                    for (i in 0 until 360 step 30) {
                        val angle = Math.toRadians(i.toDouble())
                        val p1 = Offset(
                            (center.x + (radius - 12.dp.toPx()) * kotlin.math.cos(angle)).toFloat(),
                            (center.y + (radius - 12.dp.toPx()) * kotlin.math.sin(angle)).toFloat()
                        )
                        val p2 = Offset(
                            (center.x + radius * kotlin.math.cos(angle)).toFloat(),
                            (center.y + radius * kotlin.math.sin(angle)).toFloat()
                        )
                        drawLine(WarmGold, p1, p2, strokeWidth = 2.dp.toPx())
                    }
                }

                // Rotating Needle & Kaaba Pointer
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(animatedRotation),
                    contentAlignment = Alignment.Center
                ) {
                    // Kaaba symbol pointing toward the exact direction
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.offset(y = (-80).dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            // Golden Kiswah line
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .background(BrightGold)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("পবিত্র কাবা", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                    }

                    // Needle
                    Canvas(modifier = Modifier.size(160.dp)) {
                        val center = Offset(size.width / 2, size.height / 2)
                        val needlePath = Path().apply {
                            moveTo(center.x, center.y - 70.dp.toPx())
                            lineTo(center.x + 10.dp.toPx(), center.y)
                            lineTo(center.x - 10.dp.toPx(), center.y)
                            close()
                        }
                        drawPath(needlePath, EmeraldPrimary)

                        val southPath = Path().apply {
                            moveTo(center.x, center.y + 70.dp.toPx())
                            lineTo(center.x + 10.dp.toPx(), center.y)
                            lineTo(center.x - 10.dp.toPx(), center.y)
                            close()
                        }
                        drawPath(southPath, Color.Gray.copy(alpha = 0.5f))

                        drawCircle(WarmGold, radius = 8.dp.toPx(), center = center)
                    }
                }
            }

            // Calibration & Instructions Card
            IslamicCard(
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text("সঠিক কিবলা নির্ণয়ের নির্দেশনা", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "• ফোনটি অনুভূমিকভাবে সমতল স্থানে রাখুন।\n• ধাতব বস্তু বা ইলেকট্রনিক চুম্বকীয় পদার্থ থেকে দূরে রাখুন।\n• নির্ভুলতার জন্য ফোনটিকে হাতের মধ্যে ইংরেজি '8' এর আকারে ঘুরিয়ে ক্যালিব্রেট করুন।",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
