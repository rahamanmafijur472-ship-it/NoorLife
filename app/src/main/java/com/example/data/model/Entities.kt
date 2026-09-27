package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// --- User & Profile ---
@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val id: String = "default_user",
    val name: String = "সম্মানিত ব্যবহারকারী",
    val email: String = "",
    val isPremium: Boolean = false,
    val prayerCalculationMethod: String = "Karachi (Islamic University)",
    val asrJuristicMethod: String = "Hanafi",
    val language: String = "bn", // bn, ar, en
    val fontSizeScale: Float = 1.0f,
    val arabicFontSizeScale: Float = 1.2f,
    val darkTheme: Boolean = false,
    val kidsModeActive: Boolean = false,
    val kidsPin: String = "1234"
)

// --- Bookmarks Entity for Quran, Hadith, Books, Duas ---
@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val contentType: String, // QURAN, HADITH, DUA, BOOK, LESSON
    val contentId: String,
    val title: String,
    val subtitle: String,
    val reference: String,
    val timestamp: Long = System.currentTimeMillis()
)

// --- Notes Entity ---
@Entity(tableName = "user_notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val targetType: String,
    val targetId: String,
    val title: String,
    val content: String,
    val reference: String,
    val timestamp: Long = System.currentTimeMillis()
)

// --- Tasbih History ---
@Entity(tableName = "tasbih_records")
data class TasbihRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val zikrTitle: String,
    val count: Int,
    val target: Int,
    val dateString: String,
    val timestamp: Long = System.currentTimeMillis()
)

// --- Prayer Tracking ---
@Entity(tableName = "prayer_tracking")
data class PrayerTrackingEntity(
    @PrimaryKey val dateString: String, // yyyy-MM-dd
    val fajrDone: Boolean = false,
    val dhuhrDone: Boolean = false,
    val asrDone: Boolean = false,
    val maghribDone: Boolean = false,
    val ishaDone: Boolean = false,
    val tahajjudDone: Boolean = false,
    val witrDone: Boolean = false
)

// --- Daily Sunnah & Good Habit Tracker ---
@Entity(tableName = "habit_tracking")
data class HabitTrackingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateString: String,
    val habitKey: String,
    val habitTitle: String,
    val isCompleted: Boolean,
    val category: String // SUNNAH, CHARITY, QURAN, ZIKR
)

// --- Reading Progress ---
@Entity(tableName = "reading_progress")
data class ReadingProgressEntity(
    @PrimaryKey val contentKey: String, // e.g. "quran_surah_1", "book_101"
    val title: String,
    val lastItemIndex: Int,
    val totalItems: Int,
    val percentage: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)

// --- Content Reporting Entity ---
@Entity(tableName = "content_reports")
data class ContentReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val contentType: String,
    val contentId: String,
    val reason: String,
    val details: String,
    val status: String = "PENDING", // PENDING, REVIEWED, RESOLVED
    val timestamp: Long = System.currentTimeMillis()
)
