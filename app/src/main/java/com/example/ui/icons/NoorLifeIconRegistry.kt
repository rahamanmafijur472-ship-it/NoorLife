package com.example.ui.icons

import androidx.annotation.DrawableRes
import com.example.R

/**
 * NoorLifeIconRegistry
 * Centralized Single Source of Truth for the PREMIUM MASTER ICON SYSTEM.
 * Enforces unified icon art direction, consistent geometry, optical alignment,
 * and vector drawable resource mapping across all NoorLife modules.
 */
object NoorLifeIconRegistry {

    data class IconMetadata(
        val key: String,
        val bengaliTitle: String,
        val englishTitle: String,
        val category: String,
        @DrawableRes val resId: Int,
        val iconType: NoorLifeIconType,
        val isMasterModule: Boolean = true
    )

    // Master Icon Keys
    const val KEY_HOME = "home"
    const val KEY_PRAYER = "prayer"
    const val KEY_SALAH = "salah"
    const val KEY_NEXT_PRAYER = "next_prayer"
    const val KEY_ADHAN = "adhan"
    const val KEY_QIBLA = "qibla"
    const val KEY_QURAN = "quran"
    const val KEY_HADITH = "hadith"
    const val KEY_DUA = "dua"
    const val KEY_ZIKR = "zikr"
    const val KEY_TASBIH = "tasbih"
    const val KEY_CALENDAR = "calendar"
    const val KEY_RAMADAN = "ramadan"
    const val KEY_FASTING = "fasting"
    const val KEY_HAJJ = "hajj"
    const val KEY_UMRAH = "umrah"
    const val KEY_ZAKAT = "zakat"
    const val KEY_INHERITANCE = "inheritance"
    const val KEY_JUMUAH = "jumuah"
    const val KEY_PURIFICATION = "purification"
    const val KEY_WUDU = "wudu"
    const val KEY_LIBRARY = "library"
    const val KEY_ISLAMIC_LIBRARY = "islamic_library"
    const val KEY_BENGALI_LIBRARY = "bengali_library"
    const val KEY_KIDS = "kids"
    const val KEY_ARABIC = "arabic"
    const val KEY_EDUCATION = "education"
    const val KEY_STORIES = "stories"
    const val KEY_MOSQUE = "mosque"
    const val KEY_AI = "ai"
    const val KEY_PERSONAL = "personal"
    const val KEY_SETTINGS = "settings"
    const val KEY_BRAND_LOGO = "brand_logo"
    const val KEY_APP_MARK = "app_mark"

    private val registry: Map<String, IconMetadata> = mapOf(
        KEY_HOME to IconMetadata(KEY_HOME, "হোম", "Home", "Core", R.drawable.ic_master_home, NoorLifeIconType.Home),
        KEY_PRAYER to IconMetadata(KEY_PRAYER, "নামাজ", "Prayer", "Core", R.drawable.ic_master_prayer, NoorLifeIconType.Prayer),
        KEY_SALAH to IconMetadata(KEY_SALAH, "সালাত", "Salah", "Core", R.drawable.ic_master_prayer, NoorLifeIconType.Prayer),
        KEY_NEXT_PRAYER to IconMetadata(KEY_NEXT_PRAYER, "পরবর্তী ওয়াক্ত", "Next Prayer", "Core", R.drawable.ic_master_prayer, NoorLifeIconType.Prayer),
        KEY_ADHAN to IconMetadata(KEY_ADHAN, "আজান", "Adhan", "Core", R.drawable.ic_master_prayer, NoorLifeIconType.Prayer),
        KEY_QIBLA to IconMetadata(KEY_QIBLA, "কিবলা কম্পাস", "Qibla", "Core", R.drawable.ic_master_qibla, NoorLifeIconType.Qibla),
        KEY_QURAN to IconMetadata(KEY_QURAN, "আল-কুরআন", "Quran", "Core", R.drawable.ic_master_quran, NoorLifeIconType.Quran),
        KEY_HADITH to IconMetadata(KEY_HADITH, "সহিহ হাদিস", "Hadith", "Core", R.drawable.ic_master_hadith, NoorLifeIconType.Hadith),
        KEY_DUA to IconMetadata(KEY_DUA, "দোয়া ও মোনাজাত", "Dua", "Core", R.drawable.ic_master_dua, NoorLifeIconType.Dua),
        KEY_ZIKR to IconMetadata(KEY_ZIKR, "জিকির", "Zikr", "Core", R.drawable.ic_master_tasbih, NoorLifeIconType.Zikr),
        KEY_TASBIH to IconMetadata(KEY_TASBIH, "ডিজিটাল তসবিহ", "Tasbih", "Core", R.drawable.ic_master_tasbih, NoorLifeIconType.Tasbih),
        KEY_CALENDAR to IconMetadata(KEY_CALENDAR, "ইসলামিক ক্যালেন্ডার", "Calendar", "Core", R.drawable.ic_master_calendar, NoorLifeIconType.Calendar),
        KEY_RAMADAN to IconMetadata(KEY_RAMADAN, "পবিত্র মাহে রমজান", "Ramadan", "Core", R.drawable.ic_master_ramadan, NoorLifeIconType.Ramadan),
        KEY_FASTING to IconMetadata(KEY_FASTING, "রোজা", "Fasting", "Core", R.drawable.ic_master_ramadan, NoorLifeIconType.Ramadan),
        KEY_HAJJ to IconMetadata(KEY_HAJJ, "হজ গাইড", "Hajj", "Core", R.drawable.ic_master_hajj, NoorLifeIconType.Hajj),
        KEY_UMRAH to IconMetadata(KEY_UMRAH, "ওমরাহ গাইড", "Umrah", "Core", R.drawable.ic_master_hajj, NoorLifeIconType.Hajj),
        KEY_ZAKAT to IconMetadata(KEY_ZAKAT, "যাকাত ক্যালকুলেটর", "Zakat", "Core", R.drawable.ic_master_zakat, NoorLifeIconType.Zakat),
        KEY_INHERITANCE to IconMetadata(KEY_INHERITANCE, "উত্তরাধিকার বণ্টন (ফারায়েজ)", "Inheritance", "Core", R.drawable.ic_master_inheritance, NoorLifeIconType.Inheritance),
        KEY_JUMUAH to IconMetadata(KEY_JUMUAH, "জুমার কেন্দ্র", "Jumu'ah", "Core", R.drawable.ic_master_jumuah, NoorLifeIconType.Jumuah),
        KEY_PURIFICATION to IconMetadata(KEY_PURIFICATION, "ওজু ও পবিত্রতা", "Purification", "Core", R.drawable.ic_master_prayer, NoorLifeIconType.Prayer),
        KEY_WUDU to IconMetadata(KEY_WUDU, "ওজু", "Wudu", "Core", R.drawable.ic_master_prayer, NoorLifeIconType.Prayer),
        KEY_LIBRARY to IconMetadata(KEY_LIBRARY, "ডিজিটাল লাইব্রেরি", "Library", "Library", R.drawable.ic_master_library, NoorLifeIconType.Library),
        KEY_ISLAMIC_LIBRARY to IconMetadata(KEY_ISLAMIC_LIBRARY, "ইসলামিক লাইব্রেরি", "Islamic Library", "Library", R.drawable.ic_master_library, NoorLifeIconType.Library),
        KEY_BENGALI_LIBRARY to IconMetadata(KEY_BENGALI_LIBRARY, "বাংলা সাহিত্য লাইব্রেরি", "Bengali Library", "Library", R.drawable.ic_master_library, NoorLifeIconType.Library),
        KEY_KIDS to IconMetadata(KEY_KIDS, "ছোটদের ইসলামিক জোন", "Kids Zone", "Family & Kids", R.drawable.ic_master_kids, NoorLifeIconType.Kids),
        KEY_ARABIC to IconMetadata(KEY_ARABIC, "আরবি ভাষা শিক্ষা", "Arabic Learning", "Learning", R.drawable.ic_master_arabic, NoorLifeIconType.Arabic),
        KEY_EDUCATION to IconMetadata(KEY_EDUCATION, "ইসলামিক কোর্স", "Education", "Learning", R.drawable.ic_master_home, NoorLifeIconType.Education),
        KEY_STORIES to IconMetadata(KEY_STORIES, "ইসলামিক গল্প ও ঘটনা", "Stories", "People & History", R.drawable.ic_master_hadith, NoorLifeIconType.Stories),
        KEY_MOSQUE to IconMetadata(KEY_MOSQUE, "কাছের মসজিদ", "Mosque Finder", "Location", R.drawable.ic_master_mosque, NoorLifeIconType.Mosque),
        KEY_AI to IconMetadata(KEY_AI, "নূর এআই স্টাডি সহকারী", "AI Study Assistant", "Intelligence", R.drawable.ic_master_ai, NoorLifeIconType.AI),
        KEY_PERSONAL to IconMetadata(KEY_PERSONAL, "আমার আমল ও শেখা", "Personal Deeds", "Personal", R.drawable.ic_master_personal, NoorLifeIconType.Personal),
        KEY_SETTINGS to IconMetadata(KEY_SETTINGS, "সেটিংস ও অ্যাডমিন", "Settings", "System", R.drawable.ic_master_settings, NoorLifeIconType.Settings),
        KEY_BRAND_LOGO to IconMetadata(KEY_BRAND_LOGO, "নূরলাইফ লোগো", "Brand Logo", "Brand", R.drawable.ic_noorlife_logo, NoorLifeIconType.Home),
        KEY_APP_MARK to IconMetadata(KEY_APP_MARK, "নূরলাইফ অ্যাপ আইকন", "App Mark", "Brand", R.drawable.ic_launcher_foreground, NoorLifeIconType.Home)
    )

    /**
     * Resolves the vector drawable resource ID for a given icon key.
     * Falls back to ic_master_home if key is unknown.
     */
    @DrawableRes
    fun getDrawable(key: String): Int {
        return registry[key.lowercase()]?.resId ?: R.drawable.ic_master_home
    }

    /**
     * Resolves the vector drawable resource ID for a given NoorLifeIconType.
     */
    @DrawableRes
    fun getDrawable(type: NoorLifeIconType): Int {
        return when (type) {
            NoorLifeIconType.Home -> R.drawable.ic_master_home
            NoorLifeIconType.Prayer -> R.drawable.ic_master_prayer
            NoorLifeIconType.Qibla -> R.drawable.ic_master_qibla
            NoorLifeIconType.Quran -> R.drawable.ic_master_quran
            NoorLifeIconType.Hadith -> R.drawable.ic_master_hadith
            NoorLifeIconType.Dua -> R.drawable.ic_master_dua
            NoorLifeIconType.Zikr -> R.drawable.ic_master_tasbih
            NoorLifeIconType.Tasbih -> R.drawable.ic_master_tasbih
            NoorLifeIconType.Calendar -> R.drawable.ic_master_calendar
            NoorLifeIconType.Ramadan -> R.drawable.ic_master_ramadan
            NoorLifeIconType.Hajj -> R.drawable.ic_master_hajj
            NoorLifeIconType.Zakat -> R.drawable.ic_master_zakat
            NoorLifeIconType.Inheritance -> R.drawable.ic_master_inheritance
            NoorLifeIconType.Jumuah -> R.drawable.ic_master_jumuah
            NoorLifeIconType.Library -> R.drawable.ic_master_library
            NoorLifeIconType.Kids -> R.drawable.ic_master_kids
            NoorLifeIconType.Arabic -> R.drawable.ic_master_arabic
            NoorLifeIconType.Mosque -> R.drawable.ic_master_mosque
            NoorLifeIconType.AI -> R.drawable.ic_master_ai
            NoorLifeIconType.Personal -> R.drawable.ic_master_personal
            NoorLifeIconType.Settings -> R.drawable.ic_master_settings
            NoorLifeIconType.Education -> R.drawable.ic_master_home
            NoorLifeIconType.Stories -> R.drawable.ic_master_hadith
            NoorLifeIconType.History -> R.drawable.ic_master_calendar
            NoorLifeIconType.Media -> R.drawable.ic_master_quran
        }
    }

    fun getMetadata(key: String): IconMetadata? {
        return registry[key.lowercase()]
    }

    fun allKeys(): List<String> = registry.keys.toList()

    fun allIcons(): List<IconMetadata> = registry.values.toList()

    fun containsKey(key: String): Boolean = registry.containsKey(key.lowercase())
}
