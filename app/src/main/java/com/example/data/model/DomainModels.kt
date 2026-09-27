package com.example.data.model

// --- Prayer Times Model ---
data class PrayerSchedule(
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val tahajjud: String,
    val witr: String,
    val dateGregorian: String,
    val dateBengali: String,
    val dateHijri: String,
    val locationName: String
)

data class PrayerNextInfo(
    val prayerNameBengali: String,
    val prayerTime: String,
    val remainingTimeFormatted: String,
    val progressFraction: Float
)

// --- Quran Models ---
data class Surah(
    val number: Int,
    val nameArabic: String,
    val nameBengali: String,
    val nameEnglish: String,
    val meaningBengali: String,
    val totalVerses: Int,
    val revelationType: String, // Makki, Madani
    val verses: List<Verse> = emptyList()
)

data class Verse(
    val surahNumber: Int,
    val verseNumber: Int,
    val textArabic: String,
    val translationBengali: String,
    val translationEnglish: String,
    val transliteration: String = "",
    val sajdah: Boolean = false
)

// --- Hadith Models ---
data class HadithBook(
    val id: String,
    val nameArabic: String,
    val nameBengali: String,
    val author: String,
    val totalHadithCount: Int,
    val description: String
)

data class HadithItem(
    val id: String,
    val bookId: String,
    val bookNameBengali: String,
    val chapterNameBengali: String,
    val hadithNumber: Int,
    val textArabic: String,
    val translationBengali: String,
    val narrator: String,
    val grade: String, // সহিহ (Sahih), হাসান (Hasan)
    val reference: String
)

// --- Dua & Zikr Models ---
data class DuaItem(
    val id: String,
    val category: String, // সকাল-সন্ধ্যা, নামাজ, ক্ষমা প্রার্থনা, বিপদ-আপদ, সফর, রমজান
    val titleBengali: String,
    val textArabic: String,
    val pronunciationBengali: String,
    val meaningBengali: String,
    val benefit: String,
    val reference: String
)

data class ZikrItem(
    val id: String,
    val titleBengali: String,
    val textArabic: String,
    val pronunciationBengali: String,
    val meaningBengali: String,
    val defaultTarget: Int = 33,
    val reward: String,
    val reference: String
)

// --- Library Models ---
data class BookItem(
    val id: String,
    val titleBengali: String,
    val author: String,
    val translator: String = "",
    val publisher: String,
    val category: String, // তাফসির, হাদিস, সিরাত, ফিকহ, আকীদা, বাংলা সাহিত্য
    val isIslamic: Boolean = true,
    val isFree: Boolean = true,
    val isVerified: Boolean = true,
    val license: String = "পাবলিক ডোমেইন / উন্মুক্ত বিতরণ",
    val description: String,
    val chapters: List<BookChapter> = emptyList()
)

data class BookChapter(
    val chapterNumber: Int,
    val title: String,
    val content: String
)

// --- Calendar & Events ---
data class IslamicEvent(
    val dateHijri: String,
    val dateGregorian: String,
    val titleBengali: String,
    val description: String,
    val category: String // গুরুত্বপূর্ণ দিন, রমজান, ঈদ, হজ
)

// --- Course & Learning ---
data class CourseItem(
    val id: String,
    val titleBengali: String,
    val category: String,
    val level: String, // প্রাথমিক, মধ্যম, উচ্চতর
    val totalLessons: Int,
    val description: String,
    val lessons: List<LessonItem> = emptyList()
)

data class LessonItem(
    val id: String,
    val lessonNumber: Int,
    val title: String,
    val content: String,
    val quiz: List<QuizQuestion> = emptyList()
)

data class QuizQuestion(
    val id: String,
    val questionBengali: String,
    val optionsBengali: List<String>,
    val correctIndex: Int,
    val explanationBengali: String,
    val reference: String
)

// --- Stories, Biographies & History ---
data class StoryItem(
    val id: String,
    val category: String, // নবী-রাসূলগণের ঘটনা, সাহাবায়ে কেরাম, পূর্বসূরী উলামা
    val titleBengali: String,
    val summaryBengali: String,
    val fullStoryBengali: String,
    val moralBengali: String,
    val reference: String,
    val authenticityStatus: String = "বিশ্বস্ত ঐতিহাসিক বর্ণনা"
)

data class HistoricalPlace(
    val id: String,
    val nameBengali: String,
    val nameArabic: String,
    val location: String,
    val significance: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)

data class MosqueItem(
    val id: String,
    val nameBengali: String,
    val addressBengali: String,
    val distanceKm: Double,
    val facilities: List<String>,
    val contactPhone: String = "",
    val isVerified: Boolean = true
)
