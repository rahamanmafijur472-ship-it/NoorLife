package com.example.ui.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import com.example.ui.theme.BrightGold
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.WarmGold

enum class NoorLifeIconType {
    Home,
    Prayer,
    Qibla,
    Quran,
    Hadith,
    Dua,
    Zikr,
    Tasbih,
    Calendar,
    Ramadan,
    Hajj,
    Zakat,
    Inheritance,
    Jumuah,
    Library,
    Kids,
    Arabic,
    Mosque,
    AI,
    Personal,
    Settings,
    Education,
    Stories,
    History,
    Media
}

@Composable
fun NoorLifeIcon(
    type: NoorLifeIconType,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = MaterialTheme.colorScheme.primary,
    accentTint: Color = BrightGold,
    description: String? = null
) {
    Box(
        modifier = modifier
            .size(size)
            .then(
                if (description != null) Modifier.semantics { contentDescription = description }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val w = this.size.width
            val h = this.size.height

            when (type) {
                NoorLifeIconType.Home -> drawHomeIcon(w, h, tint, accentTint)
                NoorLifeIconType.Prayer -> drawPrayerIcon(w, h, tint, accentTint)
                NoorLifeIconType.Qibla -> drawQiblaIcon(w, h, tint, accentTint)
                NoorLifeIconType.Quran -> drawQuranIcon(w, h, tint, accentTint)
                NoorLifeIconType.Hadith -> drawHadithIcon(w, h, tint, accentTint)
                NoorLifeIconType.Dua -> drawDuaIcon(w, h, tint, accentTint)
                NoorLifeIconType.Zikr -> drawZikrIcon(w, h, tint, accentTint)
                NoorLifeIconType.Tasbih -> drawTasbihIcon(w, h, tint, accentTint)
                NoorLifeIconType.Calendar -> drawCalendarIcon(w, h, tint, accentTint)
                NoorLifeIconType.Ramadan -> drawRamadanIcon(w, h, tint, accentTint)
                NoorLifeIconType.Hajj -> drawHajjIcon(w, h, tint, accentTint)
                NoorLifeIconType.Zakat -> drawZakatIcon(w, h, tint, accentTint)
                NoorLifeIconType.Inheritance -> drawInheritanceIcon(w, h, tint, accentTint)
                NoorLifeIconType.Jumuah -> drawJumuahIcon(w, h, tint, accentTint)
                NoorLifeIconType.Library -> drawLibraryIcon(w, h, tint, accentTint)
                NoorLifeIconType.Kids -> drawKidsIcon(w, h, tint, accentTint)
                NoorLifeIconType.Arabic -> drawArabicIcon(w, h, tint, accentTint)
                NoorLifeIconType.Mosque -> drawMosqueIcon(w, h, tint, accentTint)
                NoorLifeIconType.AI -> drawAIIcon(w, h, tint, accentTint)
                NoorLifeIconType.Personal -> drawPersonalIcon(w, h, tint, accentTint)
                NoorLifeIconType.Settings -> drawSettingsIcon(w, h, tint, accentTint)
                NoorLifeIconType.Education -> drawEducationIcon(w, h, tint, accentTint)
                NoorLifeIconType.Stories -> drawStoriesIcon(w, h, tint, accentTint)
                NoorLifeIconType.History -> drawHistoryIcon(w, h, tint, accentTint)
                NoorLifeIconType.Media -> drawMediaIcon(w, h, tint, accentTint)
            }
        }
    }
}

// 1. Home - Mosque Dome + Subtle Arch Silhouette
private fun DrawScope.drawHomeIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Arch outline
    val path = Path().apply {
        moveTo(w * 0.2f, h * 0.85f)
        lineTo(w * 0.2f, h * 0.5f)
        cubicTo(w * 0.2f, h * 0.3f, w * 0.4f, h * 0.15f, w * 0.5f, h * 0.12f)
        cubicTo(w * 0.6f, h * 0.15f, w * 0.8f, h * 0.3f, w * 0.8f, h * 0.5f)
        lineTo(w * 0.8f, h * 0.85f)
        close()
    }
    drawPath(path, primary, style = Stroke(width = strokeW, join = StrokeJoin.Round))

    // Inner Arch Gate
    val innerGate = Path().apply {
        moveTo(w * 0.38f, h * 0.85f)
        lineTo(w * 0.38f, h * 0.62f)
        cubicTo(w * 0.38f, h * 0.52f, w * 0.45f, h * 0.46f, w * 0.5f, h * 0.44f)
        cubicTo(w * 0.55f, h * 0.46f, w * 0.62f, h * 0.52f, w * 0.62f, h * 0.62f)
        lineTo(w * 0.62f, h * 0.85f)
    }
    drawPath(innerGate, accent, style = Stroke(width = strokeW * 0.9f))
    drawCircle(accent, radius = w * 0.04f, center = Offset(w * 0.5f, h * 0.1f))
}

// 2. Prayer / Salah - Minaret & Prayer Clock Concept
private fun DrawScope.drawPrayerIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Clock rim
    drawCircle(primary, radius = w * 0.35f, center = Offset(w * 0.5f, h * 0.52f), style = Stroke(strokeW))
    // Clock hands
    drawLine(primary, Offset(w * 0.5f, h * 0.52f), Offset(w * 0.5f, h * 0.32f), strokeWidth = strokeW, cap = StrokeCap.Round)
    drawLine(accent, Offset(w * 0.5f, h * 0.52f), Offset(w * 0.68f, h * 0.52f), strokeWidth = strokeW, cap = StrokeCap.Round)
    // Top crescent finial
    val crescentPath = Path().apply {
        moveTo(w * 0.48f, h * 0.08f)
        cubicTo(w * 0.42f, h * 0.08f, w * 0.38f, h * 0.13f, w * 0.42f, h * 0.18f)
        cubicTo(w * 0.46f, h * 0.22f, w * 0.54f, h * 0.22f, w * 0.58f, h * 0.18f)
        cubicTo(w * 0.52f, h * 0.16f, w * 0.5f, h * 0.12f, w * 0.48f, h * 0.08f)
    }
    drawPath(crescentPath, accent, style = Fill)
}

// 3. Qibla - Compass Dial + Geometric Kaaba Center
private fun DrawScope.drawQiblaIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.07f
    // Compass ring
    drawCircle(primary, radius = w * 0.4f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(strokeW))
    // Kaaba square in center
    val kaabaSize = w * 0.26f
    drawRoundRect(
        color = primary,
        topLeft = Offset(w * 0.5f - kaabaSize / 2, h * 0.5f - kaabaSize / 2),
        size = Size(kaabaSize, kaabaSize),
        cornerRadius = CornerRadius(w * 0.03f, w * 0.03f)
    )
    // Golden Kiswah stripe
    drawLine(accent, Offset(w * 0.37f, h * 0.44f), Offset(w * 0.63f, h * 0.44f), strokeWidth = strokeW * 0.8f)
    // Pointing indicator arrow toward Makkah (North-East / Bearing)
    val arrowPath = Path().apply {
        moveTo(w * 0.5f, h * 0.06f)
        lineTo(w * 0.58f, h * 0.2f)
        lineTo(w * 0.42f, h * 0.2f)
        close()
    }
    drawPath(arrowPath, accent, style = Fill)
}

// 4. Quran - Open Holy Book with Noor Rays
private fun DrawScope.drawQuranIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Left Page
    val leftPage = Path().apply {
        moveTo(w * 0.5f, h * 0.8f)
        cubicTo(w * 0.4f, h * 0.74f, w * 0.25f, h * 0.74f, w * 0.14f, h * 0.78f)
        lineTo(w * 0.14f, h * 0.38f)
        cubicTo(w * 0.25f, h * 0.34f, w * 0.4f, h * 0.34f, w * 0.5f, h * 0.4f)
        close()
    }
    drawPath(leftPage, primary, style = Stroke(strokeW, join = StrokeJoin.Round))

    // Right Page
    val rightPage = Path().apply {
        moveTo(w * 0.5f, h * 0.8f)
        cubicTo(w * 0.6f, h * 0.74f, w * 0.75f, h * 0.74f, w * 0.86f, h * 0.78f)
        lineTo(w * 0.86f, h * 0.38f)
        cubicTo(w * 0.75f, h * 0.34f, w * 0.6f, h * 0.34f, w * 0.5f, h * 0.4f)
        close()
    }
    drawPath(rightPage, primary, style = Stroke(strokeW, join = StrokeJoin.Round))

    // Radiant Noor Star at top center
    drawCircle(accent, radius = w * 0.05f, center = Offset(w * 0.5f, h * 0.22f))
    drawLine(accent, Offset(w * 0.5f, h * 0.4f), Offset(w * 0.5f, h * 0.8f), strokeWidth = strokeW * 0.7f)
}

// 5. Hadith - Distinct Knowledge Emblem / Quill & Scroll Motif
private fun DrawScope.drawHadithIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Classical tablet / scroll boundary
    drawRoundRect(
        color = primary,
        topLeft = Offset(w * 0.2f, h * 0.15f),
        size = Size(w * 0.6f, h * 0.7f),
        cornerRadius = CornerRadius(w * 0.08f, w * 0.08f),
        style = Stroke(strokeW)
    )
    // 3 Calligraphy knowledge lines
    drawLine(primary, Offset(w * 0.32f, h * 0.34f), Offset(w * 0.68f, h * 0.34f), strokeWidth = strokeW * 0.8f, cap = StrokeCap.Round)
    drawLine(accent, Offset(w * 0.32f, h * 0.48f), Offset(w * 0.68f, h * 0.48f), strokeWidth = strokeW * 0.8f, cap = StrokeCap.Round)
    drawLine(primary, Offset(w * 0.32f, h * 0.62f), Offset(w * 0.56f, h * 0.62f), strokeWidth = strokeW * 0.8f, cap = StrokeCap.Round)
    // Authenticity seal / dot
    drawCircle(accent, radius = w * 0.045f, center = Offset(w * 0.66f, h * 0.62f))
}

// 6. Dua - Cupped Supplication Hands
private fun DrawScope.drawDuaIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Left hand silhouette
    val leftHand = Path().apply {
        moveTo(w * 0.48f, h * 0.78f)
        lineTo(w * 0.22f, h * 0.65f)
        cubicTo(w * 0.16f, h * 0.45f, w * 0.28f, h * 0.3f, w * 0.38f, h * 0.24f)
        cubicTo(w * 0.44f, h * 0.35f, w * 0.47f, h * 0.52f, w * 0.48f, h * 0.78f)
    }
    drawPath(leftHand, primary, style = Stroke(strokeW, cap = StrokeCap.Round, join = StrokeJoin.Round))

    // Right hand silhouette
    val rightHand = Path().apply {
        moveTo(w * 0.52f, h * 0.78f)
        lineTo(w * 0.78f, h * 0.65f)
        cubicTo(w * 0.84f, h * 0.45f, w * 0.72f, h * 0.3f, w * 0.62f, h * 0.24f)
        cubicTo(w * 0.56f, h * 0.35f, w * 0.53f, h * 0.52f, w * 0.52f, h * 0.78f)
    }
    drawPath(rightHand, primary, style = Stroke(strokeW, cap = StrokeCap.Round, join = StrokeJoin.Round))

    // Golden divine light droplet
    drawCircle(accent, radius = w * 0.04f, center = Offset(w * 0.5f, h * 0.16f))
}

// 7. Zikr - Concentric Remembrance Ring with Radiant Center
private fun DrawScope.drawZikrIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.07f
    drawCircle(primary, radius = w * 0.38f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(strokeW))
    drawCircle(accent, radius = w * 0.24f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(strokeW * 0.8f))
    drawCircle(accent, radius = w * 0.1f, center = Offset(w * 0.5f, h * 0.5f), style = Fill)
}

// 8. Tasbih - Circular Prayer Beads Loop
private fun DrawScope.drawTasbihIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val beadRadius = w * 0.045f
    val center = Offset(w * 0.5f, h * 0.42f)
    val ringR = w * 0.28f
    val count = 10
    for (i in 0 until count) {
        val angle = Math.toRadians((i * 360.0 / count))
        val x = center.x + ringR * cos(angle).toFloat()
        val y = center.y + ringR * sin(angle).toFloat()
        drawCircle(if (i % 2 == 0) primary else accent, radius = beadRadius, center = Offset(x, y))
    }
    // Hanging Tassel
    drawLine(accent, Offset(w * 0.5f, h * 0.7f), Offset(w * 0.5f, h * 0.88f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
}

// 9. Calendar - Islamic Calendar with Crescent
private fun DrawScope.drawCalendarIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    drawRoundRect(
        color = primary,
        topLeft = Offset(w * 0.16f, h * 0.2f),
        size = Size(w * 0.68f, h * 0.68f),
        cornerRadius = CornerRadius(w * 0.08f, w * 0.08f),
        style = Stroke(strokeW)
    )
    // Top pegs
    drawLine(primary, Offset(w * 0.32f, h * 0.12f), Offset(w * 0.32f, h * 0.24f), strokeWidth = strokeW, cap = StrokeCap.Round)
    drawLine(primary, Offset(w * 0.68f, h * 0.12f), Offset(w * 0.68f, h * 0.24f), strokeWidth = strokeW, cap = StrokeCap.Round)
    // Crescent inside
    drawCircle(accent, radius = w * 0.12f, center = Offset(w * 0.5f, h * 0.55f))
    drawCircle(primary.copy(alpha = 0.9f), radius = w * 0.09f, center = Offset(w * 0.54f, h * 0.53f))
}

// 10. Ramadan - Crescent Moon and Fanous Lantern
private fun DrawScope.drawRamadanIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.07f
    // Crescent
    val crescent = Path().apply {
        moveTo(w * 0.4f, h * 0.15f)
        cubicTo(w * 0.2f, h * 0.25f, w * 0.15f, h * 0.65f, w * 0.45f, h * 0.82f)
        cubicTo(w * 0.28f, h * 0.72f, w * 0.28f, h * 0.32f, w * 0.4f, h * 0.15f)
    }
    drawPath(crescent, accent, style = Fill)
    // Hanging Lantern
    drawRoundRect(
        color = primary,
        topLeft = Offset(w * 0.52f, h * 0.35f),
        size = Size(w * 0.26f, h * 0.38f),
        cornerRadius = CornerRadius(w * 0.06f, w * 0.06f),
        style = Stroke(strokeW)
    )
    drawCircle(accent, radius = w * 0.04f, center = Offset(w * 0.65f, h * 0.54f), style = Fill)
    drawLine(primary, Offset(w * 0.65f, h * 0.16f), Offset(w * 0.65f, h * 0.35f), strokeWidth = strokeW * 0.7f)
}

// 11. Hajj & Umrah - Kaaba Cube with Tawaf Rings
private fun DrawScope.drawHajjIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Orbital Tawaf elliptical arc
    drawArc(
        color = accent,
        startAngle = 140f,
        sweepAngle = 260f,
        useCenter = false,
        topLeft = Offset(w * 0.1f, h * 0.55f),
        size = Size(w * 0.8f, h * 0.32f),
        style = Stroke(strokeW * 0.7f)
    )
    // Kaaba Cube
    drawRoundRect(
        color = primary,
        topLeft = Offset(w * 0.3f, h * 0.25f),
        size = Size(w * 0.4f, h * 0.45f),
        cornerRadius = CornerRadius(w * 0.04f, w * 0.04f)
    )
    // Golden Band
    drawLine(accent, Offset(w * 0.3f, h * 0.35f), Offset(w * 0.7f, h * 0.35f), strokeWidth = strokeW)
}

// 12. Zakat - Generous Hand of Charity & Growth
private fun DrawScope.drawZakatIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Open Giving Palm
    val palm = Path().apply {
        moveTo(w * 0.15f, h * 0.65f)
        lineTo(w * 0.45f, h * 0.78f)
        lineTo(w * 0.85f, h * 0.65f)
        cubicTo(w * 0.75f, h * 0.52f, w * 0.55f, h * 0.58f, w * 0.45f, h * 0.62f)
        lineTo(w * 0.25f, h * 0.52f)
        close()
    }
    drawPath(palm, primary, style = Stroke(strokeW, join = StrokeJoin.Round))
    // Seed/Coin of Barakah
    drawCircle(accent, radius = w * 0.14f, center = Offset(w * 0.5f, h * 0.32f), style = Stroke(strokeW))
    drawCircle(accent, radius = w * 0.05f, center = Offset(w * 0.5f, h * 0.32f), style = Fill)
}

// 13. Inheritance - Balance Scale / Equity
private fun DrawScope.drawInheritanceIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Balance Beam
    drawLine(primary, Offset(w * 0.2f, h * 0.35f), Offset(w * 0.8f, h * 0.35f), strokeWidth = strokeW, cap = StrokeCap.Round)
    // Pillar
    drawLine(primary, Offset(w * 0.5f, h * 0.2f), Offset(w * 0.5f, h * 0.85f), strokeWidth = strokeW, cap = StrokeCap.Round)
    // Left Scale Pan
    drawLine(accent, Offset(w * 0.25f, h * 0.35f), Offset(w * 0.25f, h * 0.6f), strokeWidth = strokeW * 0.7f)
    drawArc(accent, 0f, 180f, false, Offset(w * 0.15f, h * 0.55f), Size(w * 0.2f, w * 0.15f), style = Stroke(strokeW * 0.8f))
    // Right Scale Pan
    drawLine(accent, Offset(w * 0.75f, h * 0.35f), Offset(w * 0.75f, h * 0.6f), strokeWidth = strokeW * 0.7f)
    drawArc(accent, 0f, 180f, false, Offset(w * 0.65f, h * 0.55f), Size(w * 0.2f, w * 0.15f), style = Stroke(strokeW * 0.8f))
}

// 14. Jumu'ah - Friday Mosque Dome & Rays
private fun DrawScope.drawJumuahIcon(w: Float, h: Float, primary: Color, accent: Color) {
    drawHomeIcon(w, h, primary, accent)
    // Extra Friday sun rays
    val rayLen = w * 0.08f
    drawLine(accent, Offset(w * 0.5f, h * 0.04f), Offset(w * 0.5f, h * 0.04f - rayLen), strokeWidth = w * 0.05f, cap = StrokeCap.Round)
}

// 15. Library - Bookshelf & Knowledge Volumes
private fun DrawScope.drawLibraryIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Books standing on shelf
    drawRoundRect(primary, Offset(w * 0.18f, h * 0.22f), Size(w * 0.16f, h * 0.56f), CornerRadius(w * 0.03f, w * 0.03f), Stroke(strokeW))
    drawRoundRect(accent, Offset(w * 0.42f, h * 0.15f), Size(w * 0.16f, h * 0.63f), CornerRadius(w * 0.03f, w * 0.03f), Stroke(strokeW))
    drawRoundRect(primary, Offset(w * 0.66f, h * 0.3f), Size(w * 0.16f, h * 0.48f), CornerRadius(w * 0.03f, w * 0.03f), Stroke(strokeW))
    // Shelf Base
    drawLine(primary, Offset(w * 0.1f, h * 0.82f), Offset(w * 0.9f, h * 0.82f), strokeWidth = strokeW * 1.2f, cap = StrokeCap.Round)
}

// 16. Kids Mode - Child-friendly Islamic Star & Crescent
private fun DrawScope.drawKidsIcon(w: Float, h: Float, primary: Color, accent: Color) {
    // Smiling Crescent
    drawCircle(accent, radius = w * 0.35f, center = Offset(w * 0.45f, h * 0.5f))
    drawCircle(Color.White, radius = w * 0.28f, center = Offset(w * 0.58f, h * 0.46f))
    // Playful Star
    drawCircle(primary, radius = w * 0.1f, center = Offset(w * 0.72f, h * 0.3f), style = Fill)
}

// 17. Arabic Learning - Abstract Alif-Lam-Meem Calligraphy
private fun DrawScope.drawArabicIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.09f
    // Vertical Alif
    drawLine(primary, Offset(w * 0.78f, h * 0.18f), Offset(w * 0.78f, h * 0.8f), strokeWidth = strokeW, cap = StrokeCap.Round)
    // Lam-Meem Curvature
    val path = Path().apply {
        moveTo(w * 0.55f, h * 0.18f)
        lineTo(w * 0.55f, h * 0.65f)
        cubicTo(w * 0.55f, h * 0.82f, w * 0.28f, h * 0.82f, w * 0.22f, h * 0.65f)
        cubicTo(w * 0.18f, h * 0.5f, w * 0.32f, h * 0.45f, w * 0.4f, h * 0.55f)
    }
    drawPath(path, accent, style = Stroke(strokeW, cap = StrokeCap.Round, join = StrokeJoin.Round))
}

// 18. Mosque Finder - Dome + Location Pin Base
private fun DrawScope.drawMosqueIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    // Pin Outline
    val pin = Path().apply {
        moveTo(w * 0.5f, h * 0.9f)
        cubicTo(w * 0.22f, h * 0.65f, w * 0.15f, h * 0.45f, w * 0.15f, h * 0.35f)
        cubicTo(w * 0.15f, h * 0.16f, w * 0.3f, h * 0.1f, w * 0.5f, h * 0.1f)
        cubicTo(w * 0.7f, h * 0.1f, w * 0.85f, h * 0.16f, w * 0.85f, h * 0.35f)
        cubicTo(w * 0.85f, h * 0.45f, w * 0.78f, h * 0.65f, w * 0.5f, h * 0.9f)
    }
    drawPath(pin, primary, style = Stroke(strokeW, join = StrokeJoin.Round))
    // Inner Crescent
    drawCircle(accent, radius = w * 0.12f, center = Offset(w * 0.5f, h * 0.36f), style = Fill)
}

// 19. AI Assistant - Noor Radiance & Intelligent Star
private fun DrawScope.drawAIIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.07f
    val center = Offset(w * 0.5f, h * 0.5f)
    // 8-pointed Islamic Star geometry
    val starPath = Path().apply {
        val rOuter = w * 0.42f
        val rInner = w * 0.28f
        for (i in 0 until 16) {
            val r = if (i % 2 == 0) rOuter else rInner
            val angle = Math.toRadians(i * 22.5 - 90)
            val x = center.x + r * cos(angle).toFloat()
            val y = center.y + r * sin(angle).toFloat()
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }
    drawPath(starPath, primary, style = Stroke(strokeW, join = StrokeJoin.Round))
    // Radiance core
    drawCircle(accent, radius = w * 0.14f, center = center, style = Fill)
}

// 20. Personal - Deeds & Habits Shield
private fun DrawScope.drawPersonalIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    val shield = Path().apply {
        moveTo(w * 0.5f, h * 0.12f)
        lineTo(w * 0.82f, h * 0.24f)
        lineTo(w * 0.82f, h * 0.55f)
        cubicTo(w * 0.82f, h * 0.75f, w * 0.62f, h * 0.86f, w * 0.5f, h * 0.9f)
        cubicTo(w * 0.38f, h * 0.86f, w * 0.18f, h * 0.75f, w * 0.18f, h * 0.55f)
        lineTo(w * 0.18f, h * 0.24f)
        close()
    }
    drawPath(shield, primary, style = Stroke(strokeW, join = StrokeJoin.Round))
    // Checkmark inside
    val check = Path().apply {
        moveTo(w * 0.34f, h * 0.52f)
        lineTo(w * 0.46f, h * 0.64f)
        lineTo(w * 0.68f, h * 0.4f)
    }
    drawPath(check, accent, style = Stroke(strokeW * 1.1f, cap = StrokeCap.Round, join = StrokeJoin.Round))
}

// 21. Settings - Islamic 8-Point Geometric Rosette
private fun DrawScope.drawSettingsIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    drawCircle(primary, radius = w * 0.38f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(strokeW))
    drawCircle(accent, radius = w * 0.14f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(strokeW))
    for (i in 0 until 4) {
        val angle = Math.toRadians(i * 45.0)
        val x1 = w * 0.5f + w * 0.22f * cos(angle).toFloat()
        val y1 = h * 0.5f + w * 0.22f * sin(angle).toFloat()
        val x2 = w * 0.5f + w * 0.38f * cos(angle).toFloat()
        val y2 = h * 0.5f + w * 0.38f * sin(angle).toFloat()
        drawLine(primary, Offset(x1, y1), Offset(x2, y2), strokeWidth = strokeW, cap = StrokeCap.Round)
    }
}

// 22. Education - Academic Graduation Cap / Diploma with Quill
private fun DrawScope.drawEducationIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    val cap = Path().apply {
        moveTo(w * 0.5f, h * 0.22f)
        lineTo(w * 0.86f, h * 0.38f)
        lineTo(w * 0.5f, h * 0.54f)
        lineTo(w * 0.14f, h * 0.38f)
        close()
    }
    drawPath(cap, primary, style = Stroke(strokeW, join = StrokeJoin.Round))
    // Cap base
    val base = Path().apply {
        moveTo(w * 0.3f, h * 0.47f)
        lineTo(w * 0.3f, h * 0.65f)
        cubicTo(w * 0.3f, h * 0.78f, w * 0.7f, h * 0.78f, w * 0.7f, h * 0.65f)
        lineTo(w * 0.7f, h * 0.47f)
    }
    drawPath(base, accent, style = Stroke(strokeW * 0.9f))
}

// 23. Stories - Ancient Scroll & Star
private fun DrawScope.drawStoriesIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    drawRoundRect(primary, Offset(w * 0.2f, h * 0.2f), Size(w * 0.6f, h * 0.65f), CornerRadius(w * 0.06f, w * 0.06f), Stroke(strokeW))
    drawLine(accent, Offset(w * 0.35f, h * 0.4f), Offset(w * 0.65f, h * 0.4f), strokeWidth = strokeW, cap = StrokeCap.Round)
    drawLine(primary, Offset(w * 0.35f, h * 0.55f), Offset(w * 0.65f, h * 0.55f), strokeWidth = strokeW, cap = StrokeCap.Round)
}

// 24. History - Timeline Milestone with Crescent
private fun DrawScope.drawHistoryIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    drawLine(primary, Offset(w * 0.5f, h * 0.12f), Offset(w * 0.5f, h * 0.88f), strokeWidth = strokeW, cap = StrokeCap.Round)
    drawCircle(accent, radius = w * 0.12f, center = Offset(w * 0.5f, h * 0.32f), style = Fill)
    drawCircle(primary, radius = w * 0.08f, center = Offset(w * 0.5f, h * 0.65f), style = Stroke(strokeW))
}

// 25. Media - Video / Audio Play with Islamic Arc
private fun DrawScope.drawMediaIcon(w: Float, h: Float, primary: Color, accent: Color) {
    val strokeW = w * 0.08f
    drawRoundRect(primary, Offset(w * 0.15f, h * 0.2f), Size(w * 0.7f, h * 0.6f), CornerRadius(w * 0.1f, w * 0.1f), Stroke(strokeW))
    val triangle = Path().apply {
        moveTo(w * 0.42f, h * 0.35f)
        lineTo(w * 0.64f, h * 0.5f)
        lineTo(w * 0.42f, h * 0.65f)
        close()
    }
    drawPath(triangle, accent, style = Fill)
}
