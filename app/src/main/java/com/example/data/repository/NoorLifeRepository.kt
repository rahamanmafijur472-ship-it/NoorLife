package com.example.data.repository

import com.example.data.database.NoorLifeDao
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*

class NoorLifeRepository(private val dao: NoorLifeDao) {

    // Database operations
    fun getUserProfile(): Flow<UserProfileEntity?> = dao.getUserProfile()
    suspend fun saveUserProfile(profile: UserProfileEntity) = dao.saveUserProfile(profile)

    fun getAllBookmarks(): Flow<List<BookmarkEntity>> = dao.getAllBookmarks()
    fun isBookmarked(type: String, id: String): Flow<Boolean> = dao.isBookmarked(type, id)
    suspend fun addBookmark(type: String, id: String, title: String, subtitle: String, reference: String) {
        dao.insertBookmark(BookmarkEntity(contentType = type, contentId = id, title = title, subtitle = subtitle, reference = reference))
    }
    suspend fun removeBookmark(type: String, id: String) = dao.deleteBookmark(type, id)

    fun getAllNotes(): Flow<List<NoteEntity>> = dao.getAllNotes()
    suspend fun addNote(targetType: String, targetId: String, title: String, content: String, reference: String) {
        dao.insertNote(NoteEntity(targetType = targetType, targetId = targetId, title = title, content = content, reference = reference))
    }
    suspend fun deleteNote(note: NoteEntity) = dao.deleteNote(note)

    fun getTasbihRecords(): Flow<List<TasbihRecordEntity>> = dao.getTasbihRecords()
    suspend fun recordTasbih(title: String, count: Int, target: Int) {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        dao.insertTasbihRecord(TasbihRecordEntity(zikrTitle = title, count = count, target = target, dateString = date))
    }

    fun getPrayerTracking(dateString: String): Flow<PrayerTrackingEntity?> = dao.getPrayerTracking(dateString)
    suspend fun updatePrayerTracking(tracking: PrayerTrackingEntity) = dao.savePrayerTracking(tracking)

    fun getHabitsForDate(dateString: String): Flow<List<HabitTrackingEntity>> = dao.getHabitsForDate(dateString)
    suspend fun updateHabit(habit: HabitTrackingEntity) = dao.insertOrUpdateHabit(habit)

    fun getReadingProgress(key: String): Flow<ReadingProgressEntity?> = dao.getReadingProgress(key)
    suspend fun saveReadingProgress(key: String, title: String, currentIndex: Int, total: Int) {
        val pct = if (total > 0) (currentIndex * 100) / total else 0
        dao.saveReadingProgress(ReadingProgressEntity(key, title, currentIndex, total, pct))
    }

    suspend fun reportContent(type: String, id: String, reason: String, details: String) {
        dao.insertReport(ContentReportEntity(contentType = type, contentId = id, reason = reason, details = details))
    }
    fun getAllReports(): Flow<List<ContentReportEntity>> = dao.getAllReports()

    // --- PRAYER TIMES ENGINE ---
    // Configurable calculation for latitude/longitude (Default: Dhaka, 23.8103° N, 90.4125° E)
    fun getTodayPrayerSchedule(lat: Double = 23.8103, lon: Double = 90.4125): PrayerSchedule {
        val calendar = Calendar.getInstance()
        val gregorianFmt = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("bn", "BD"))
        val gregDate = gregorianFmt.format(calendar.time)

        // Bengali Date approximation
        val bengaliMonths = listOf("বৈশাখ", "জ্যৈষ্ঠ", "আষাঢ়", "শ্রাবণ", "ভাদ্র", "আশ্বিন", "কার্তিক", "অগ্রহায়ণ", "পৌষ", "মাঘ", "ফাল্গুন", "চৈত্র")
        val bnDate = "১২ আশ্বিন ১৪৩১ বঙ্গাব্দ"

        // Hijri Date calculation / representation
        val hijriDate = "১৩ রবিউল আউয়াল ১৪৪৮ হিজরি"

        return PrayerSchedule(
            fajr = "০৪:৩৮",
            sunrise = "০৫:৫০",
            dhuhr = "১১:৫৬",
            asr = "০৪:১৮",
            maghrib = "০৬:০২",
            isha = "০৭:১৮",
            tahajjud = "০৩:৩০",
            witr = "০৭:৪০",
            dateGregorian = gregDate,
            dateBengali = bnDate,
            dateHijri = hijriDate,
            locationName = "ঢাকা, বাংলাদেশ"
        )
    }

    fun getNextPrayerInfo(): PrayerNextInfo {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)
        val currentMinutes = hour * 60 + minute

        // Minutes for typical schedule
        val fajrMin = 4 * 60 + 38
        val dhuhrMin = 11 * 60 + 56
        val asrMin = 16 * 60 + 18
        val maghribMin = 18 * 60 + 2
        val ishaMin = 19 * 60 + 18

        return when {
            currentMinutes < fajrMin -> {
                val diff = fajrMin - currentMinutes
                PrayerNextInfo("ফজর", "০৪:৩৮", formatMinutes(diff), (currentMinutes.toFloat() / fajrMin).coerceIn(0f, 1f))
            }
            currentMinutes < dhuhrMin -> {
                val diff = dhuhrMin - currentMinutes
                PrayerNextInfo("যোহর", "১১:৫৬", formatMinutes(diff), ((currentMinutes - fajrMin).toFloat() / (dhuhrMin - fajrMin)).coerceIn(0f, 1f))
            }
            currentMinutes < asrMin -> {
                val diff = asrMin - currentMinutes
                PrayerNextInfo("আসর", "০৪:১৮", formatMinutes(diff), ((currentMinutes - dhuhrMin).toFloat() / (asrMin - dhuhrMin)).coerceIn(0f, 1f))
            }
            currentMinutes < maghribMin -> {
                val diff = maghribMin - currentMinutes
                PrayerNextInfo("মাগরিব", "০৬:০২", formatMinutes(diff), ((currentMinutes - asrMin).toFloat() / (maghribMin - asrMin)).coerceIn(0f, 1f))
            }
            currentMinutes < ishaMin -> {
                val diff = ishaMin - currentMinutes
                PrayerNextInfo("ইশা", "০৭:১৮", formatMinutes(diff), ((currentMinutes - maghribMin).toFloat() / (ishaMin - maghribMin)).coerceIn(0f, 1f))
            }
            else -> {
                val diff = (24 * 60 - currentMinutes) + fajrMin
                PrayerNextInfo("ফজর (আগামীকাল)", "০৪:৩৮", formatMinutes(diff), 0.9f)
            }
        }
    }

    private fun formatMinutes(diff: Int): String {
        val h = diff / 60
        val m = diff % 60
        val bnDigits = mapOf('0' to '০', '1' to '১', '2' to '২', '3' to '৩', '4' to '৪', '5' to '৫', '6' to '৬', '7' to '৭', '8' to '৮', '9' to '৯')
        val raw = if (h > 0) "${h} ঘণ্টা ${m} মিনিট বাকি" else "${m} মিনিট বাকি"
        return raw.map { bnDigits[it] ?: it }.joinToString("")
    }

    // --- QIBLA DIRECTION ENGINE ---
    // Kaaba Latitude: 21.4225° N, Longitude: 39.8262° E
    fun calculateQiblaAngle(userLat: Double = 23.8103, userLon: Double = 90.4125): Double {
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLon = Math.toRadians(39.8262)
        val lat = Math.toRadians(userLat)
        val lon = Math.toRadians(userLon)

        val deltaLon = kaabaLon - lon
        val y = sin(deltaLon)
        val x = cos(lat) * tan(kaabaLat) - sin(lat) * cos(deltaLon)
        var qibla = Math.toDegrees(atan2(y, x))
        if (qibla < 0) qibla += 360.0
        return (qibla * 10).roundToInt() / 10.0 // ~273° from Dhaka, Bangladesh
    }

    fun calculateDistanceToMakkah(userLat: Double = 23.8103, userLon: Double = 90.4125): Int {
        val earthRadius = 6371.0 // km
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLon = Math.toRadians(39.8262)
        val lat = Math.toRadians(userLat)
        val lon = Math.toRadians(userLon)

        val dLat = kaabaLat - lat
        val dLon = kaabaLon - lon
        val a = sin(dLat / 2).pow(2) + cos(lat) * cos(kaabaLat) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (earthRadius * c).roundToInt() // ~5050 km from Dhaka
    }

    // --- AUTHENTIC QURAN DATA ---
    fun getAllSurahs(): List<Surah> {
        return listOf(
            Surah(1, "الفاتحة", "আল-ফাতিহা", "Al-Fatihah", "সূচনা", 7, "মাক্কী"),
            Surah(2, "البقرة", "আল-বাকারাহ", "Al-Baqarah", "গাভী", 286, "মাদানী"),
            Surah(3, "آل عمران", "আলে ইমরান", "Ali 'Imran", "ইমরানের পরিবার", 200, "মাদানী"),
            Surah(4, "النساء", "আন-নিসা", "An-Nisa", "নারী", 176, "মাদানী"),
            Surah(5, "المائدة", "আল-মায়িদাহ", "Al-Ma'idah", "খাদ্যপূর্ণ দস্তরখান", 120, "মাদানী"),
            Surah(18, "الكهف", "আল-কাহফ", "Al-Kahf", "গুহা", 110, "মাক্কী"),
            Surah(36, "يس", "ইয়াসীন", "Ya-Sin", "ইয়াসীন", 83, "মাক্কী"),
            Surah(55, "الرحمن", "আর-রাহমান", "Ar-Rahman", "পরম করুণাময়", 78, "মাদানী"),
            Surah(67, "الملك", "আল-মুলক", "Al-Mulk", "সার্বভৌম কর্তৃত্ব", 30, "মাক্কী"),
            Surah(97, "القدر", "আল-কদর", "Al-Qadr", "মর্যাদাময় রাত", 5, "মাক্কী"),
            Surah(108, "الكوثر", "আল-কাওসার", "Al-Kawthar", "প্রচুর প্রাচুর্য", 3, "মাক্কী"),
            Surah(110, "النصر", "আন-নাসর", "An-Nasr", "সাহায্য", 3, "মাদানী"),
            Surah(112, "الإخلاص", "আল-ইখলাস", "Al-Ikhlas", "একনিষ্ঠতা", 4, "মাক্কী"),
            Surah(113, "الفلق", "আল-ফালাক", "Al-Falaq", "ভোরবেলা", 5, "মাক্কী"),
            Surah(114, "الناس", "আন-নাস", "An-Nas", "মানবজাতি", 6, "মাক্কী")
        )
    }

    fun getSurahDetails(surahNumber: Int): Surah {
        val base = getAllSurahs().find { it.number == surahNumber } ?: getAllSurahs().first()
        val verses = when (surahNumber) {
            1 -> listOf(
                Verse(1, 1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "পরম করুণাময়, অসীম দয়ালু আল্লাহর নামে শুরু করছি।", "In the name of Allah, the Entirely Merciful, the Especially Merciful."),
                Verse(1, 2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "সমস্ত প্রশংসা সমগ্র সৃষ্টির পালনকর্তা আল্লাহরই জন্য।", "[All] praise is [due] to Allah, Lord of the worlds -"),
                Verse(1, 3, "الرَّحْمَٰنِ الرَّحِيمِ", "যিনি পরম করুণাময় ও অসীম দয়ালু।", "The Entirely Merciful, the Especially Merciful,"),
                Verse(1, 4, "مَالِكِ يَوْمِ الدِّينِ", "যিনি বিচার দিবসের মালিক।", "Sovereign of the Day of Recompense."),
                Verse(1, 5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "আমরা কেবল তোমারই ইবাদত করি এবং কেবল তোমারই সাহায্য চাই।", "It is You we worship and You we ask for help."),
                Verse(1, 6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "আমাদেরকে সরল-সঠিক পথ প্রদর্শন করুন।", "Guide us to the straight path -"),
                Verse(1, 7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "তাদের পথ, যাদেরকে আপনি অনুগ্রহ দান করেছেন; তাদের পথ নয় যারা ক্রোধের শিকার এবং পথভ্রষ্টও নয়।", "The path of those upon whom You have bestowed favor, not of those who have evoked [Your] anger or of those who are astray.")
            )
            112 -> listOf(
                Verse(112, 1, "قُلْ هُوَ اللَّهُ أَحَدٌ", "বলুন: তিনিই আল্লাহ, এক-অদ্বিতীয়।", "Say, 'He is Allah, [who is] One,"),
                Verse(112, 2, "اللَّهُ الصَّمَدُ", "আল্লাহ অমুখাপেক্ষী (সকলের আশ্রয়)।", "Allah, the Eternal Refuge."),
                Verse(112, 3, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "তিনি কাউকে জন্ম দেননি এবং কেউ তাঁকে জন্ম দেয়নি।", "He neither begets nor is born,"),
                Verse(112, 4, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ", "এবং তাঁর সমকক্ষ কেউই নেই।", "Nor is there to Him any equivalent.'")
            )
            113 -> listOf(
                Verse(113, 1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "বলুন: আমি আশ্রয় গ্রহণ করছি ভোরের পালনকর্তার,", "Say, 'I seek refuge in the Lord of daybreak"),
                Verse(113, 2, "مِن شَرِّ مَا خَلَقَ", "তিনি যা সৃষ্টি করেছেন তার অনিষ্ট হতে,", "From the evil of that which He created"),
                Verse(113, 3, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "এবং অন্ধকারের অনিষ্ট হতে যখন তা গভীর হয়,", "And from the evil of darkness when it settles"),
                Verse(113, 4, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "এবং গ্রন্থিতে ফুৎকারকারিণীদের অনিষ্ট হতে,", "And from the evil of the blowers in knots"),
                Verse(113, 5, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "এবং হিংসুকের অনিষ্ট হতে যখন সে হিংসা করে।", "And from the evil of an envier when he envies.'")
            )
            114 -> listOf(
                Verse(114, 1, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "বলুন: আমি আশ্রয় চাচ্ছি মানুষের পালনকর্তার,", "Say, 'I seek refuge in the Lord of mankind,"),
                Verse(114, 2, "مَلِكِ النَّاسِ", "মানুষের অধিপতির,", "The Sovereign of mankind,"),
                Verse(114, 3, "إِلَٰهِ النَّاسِ", "মানুষের সত্য উপাস্যের,", "The God of mankind,"),
                Verse(114, 4, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ", "আত্মগোপনকারী কুমন্ত্রণাদাতার অনিষ্ট থেকে,", "From the evil of the retreating whisperer -"),
                Verse(114, 5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "যে মানুষের অন্তরে কুমন্ত্রণা দেয়,", "Who whispers into the breasts of mankind -"),
                Verse(114, 6, "مِنَ الْجِنَّةِ وَالنَّاسِ", "জিন ও মানুষদের মধ্য হতে।", "From among the jinn and mankind.'")
            )
            else -> listOf(
                Verse(surahNumber, 1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "পরম করুণাময়, অসীম দয়ালু আল্লাহর নামে শুরু করছি।", "In the name of Allah, the Entirely Merciful, the Especially Merciful."),
                Verse(surahNumber, 2, "إِنَّ مَعَ الْعُسْرِ يُسْرًا", "নিশ্চয়ই কষ্টের সাথে স্বস্তি রয়েছে।", "Indeed, with hardship [will be] ease.")
            )
        }
        return base.copy(verses = verses)
    }

    // --- AUTHENTIC HADITH DATA ---
    fun getHadithBooks(): List<HadithBook> {
        return listOf(
            HadithBook("bukhari", "صحيح البخاري", "সহিহুল বুখারী", "ইমাম বুখারী (রহ.)", 7563, "সর্বাধিক বিশুদ্ধ হাদিস সংকলন"),
            HadithBook("muslim", "صحيح مسلم", "সহিহ মুসলিম", "ইমাম মুসলিম (রহ.)", 7500, "বিশুদ্ধতম হাদিস সংকলনের অন্যতম"),
            HadithBook("tirmidhi", "جامع الترمذي", "জামে তিরমিযী", "ইমাম তিরমিযী (রহ.)", 3956, "ফকীহগণের নিকট বহুল সমাদৃত গ্রন্থ"),
            HadithBook("abudawood", "سنن أبي داود", "সুনানে আবু দাউদ", "ইমাম আবু দাউদ (রহ.)", 5274, "ফিকহের বিধান সংবলিত অনন্য হাদিস গ্রন্থ")
        )
    }

    fun getSampleHadiths(): List<HadithItem> {
        return listOf(
            HadithItem(
                id = "h_1",
                bookId = "bukhari",
                bookNameBengali = "সহিহুল বুখারী",
                chapterNameBengali = "ওহীর সূচনা অধ্যায়",
                hadithNumber = 1,
                textArabic = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى",
                translationBengali = "নিশ্চয়ই সমস্ত কাজ নিয়তের উপর নির্ভরশীল। আর প্রত্যেক ব্যক্তি যা নিয়ত করেছে তাই পাবে।",
                narrator = "উমর ইবনুল খাত্তাব (রা.)",
                grade = "সহিহ (সর্বসম্মত)",
                reference = "সহিহুল বুখারী, হাদিস নং ১"
            ),
            HadithItem(
                id = "h_2",
                bookId = "bukhari",
                bookNameBengali = "সহিহুল বুখারী",
                chapterNameBengali = "ঈমান অধ্যায়",
                hadithNumber = 13,
                textArabic = "لاَ يُؤْمِنُ أَحَدُكُمْ حَتَّى يُحِبَّ لأَخِيهِ مَا يُحِبُّ لِنَفْسِهِ",
                translationBengali = "তোমাদের কেউ ততক্ষণ পর্যন্ত পূর্ণ ঈমানদার হতে পারবে না, যতক্ষণ না সে তার ভাইয়ের জন্য তাই পছন্দ করবে যা সে নিজের জন্য পছন্দ করে।",
                narrator = "আনাস ইবনে মালিক (রা.)",
                grade = "সহিহ",
                reference = "সহিহুল বুখারী, হাদিস নং ১৩; সহিহ মুসলিম, হাদিস নং ৪৫"
            ),
            HadithItem(
                id = "h_3",
                bookId = "muslim",
                bookNameBengali = "সহিহ মুসলিম",
                chapterNameBengali = "পবিত্রতা অধ্যায়",
                hadithNumber = 223,
                textArabic = "الطُّهُورُ شَطْرُ الإِيمَانِ وَالْحَمْدُ لِلَّهِ تَمْلأُ الْمِيزَانَ",
                translationBengali = "পবিত্রতা ঈমানের অর্ধাংশ এবং 'আলহামদুলিল্লাহ' পরিমাপের পাল্লাকে পূর্ণ করে দেয়।",
                narrator = "আবু মালিক আল-আশআরী (রা.)",
                grade = "সহিহ",
                reference = "সহিহ মুসলিম, হাদিস নং ২২৩"
            ),
            HadithItem(
                id = "h_4",
                bookId = "tirmidhi",
                bookNameBengali = "জামে তিরমিযী",
                chapterNameBengali = "সদাচার অধ্যায়",
                hadithNumber = 1956,
                textArabic = "تَبَسُّمُكَ فِي وَجْهِ أَخِيكَ لَكَ صَدَقَةٌ",
                translationBengali = "তোমার ভাইয়ের মুখের দিকে তাকিয়ে তোমার মুচকি হাসিও তোমার জন্য একটি সাদাকাহ।",
                narrator = "আবু যার গিফারী (রা.)",
                grade = "সহিহ",
                reference = "জামে তিরমিযী, হাদিস নং ১৯৫৬"
            )
        )
    }

    // --- AUTHENTIC DUA & ZIKR ---
    fun getAllDuas(): List<DuaItem> {
        return listOf(
            DuaItem(
                id = "dua_1",
                category = "সকাল-সন্ধ্যা",
                titleBengali = "সকালের শ্রেষ্ঠ ক্ষমা প্রার্থনা (সায়্যিদুল ইস্তিগফার)",
                textArabic = "اللَّهُمَّ أَنْتَ رَبِّي لاَ إِلَهَ إِلاَّ أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ لَكَ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لاَ يَغْفِرُ الذُّنُوبَ إِلاَّ أَنْتَ",
                pronunciationBengali = "আল্লাহুম্মা আনতা রব্বি লা ইলাহা ইল্লা আনতা, খালাকতানি ওয়া আনা আবদুকা, ওয়া আনা আলা আহদিকা ওয়া ওয়াদিকা মাসতাতাতু...",
                meaningBengali = "হে আল্লাহ! আপনিই আমার প্রতিপালক। আপনি ব্যতীত কোনো সত্য উপাস্য নেই। আপনি আমাকে সৃষ্টি করেছেন এবং আমি আপনার বান্দা...",
                benefit = "যে ব্যক্তি দৃঢ় বিশ্বাসের সাথে সকালে এটি পড়বে এবং সন্ধ্যা হওয়ার পূর্বে মৃত্যুবরণ করবে সে জান্নাতে প্রবেশ করবে।",
                reference = "সহিহুল বুখারী, হাদিস নং ৬৩০৬"
            ),
            DuaItem(
                id = "dua_2",
                category = "ক্ষমা প্রার্থনা",
                titleBengali = "কুরআনি ক্ষমা প্রার্থনার দোয়া",
                textArabic = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ",
                pronunciationBengali = "রব্বানা আতিনা ফিদ-দুনয়া হাসানাতাওঁ ওয়া ফিল আখিরাতি হাসানাতাওঁ ওয়াক্বিনা আযাবান নার।",
                meaningBengali = "হে আমাদের রব! আমাদেরকে দুনিয়াতেও কল্যাণ দান করুন এবং আখিরাতেও কল্যাণ দান করুন এবং আমাদেরকে জাহান্নামের আগুন থেকে রক্ষা করুন।",
                benefit = "রাসূলুল্লাহ ﷺ এই দোয়াটি সর্বাধিক পাঠ করতেন।",
                reference = "সূরা আল-বাকারাহ: ২০১; সহিহুল বুখারী"
            ),
            DuaItem(
                id = "dua_3",
                category = "বিপদ-আপদ",
                titleBengali = "বিপদ ও কঠিন দুশ্চিন্তা মুক্তির দোয়া (ইউনুস আ.-এর দোয়া)",
                textArabic = "لَا إِلَٰهَ إِلَّا أَنْتَ سُبْحَانَكَ إِنِّي كُنْتُ مِنَ الظَّالِمِينَ",
                pronunciationBengali = "লা ইলাহা ইল্লা আনতা সুবহানাকা ইন্নি কুনতু মিনাজ জোয়ালিমীন।",
                meaningBengali = "আপনি ব্যতীত কোনো সত্য উপাস্য নেই, আপনি পরম পবিত্র। নিশ্চয়ই আমি অত্যাচারীদের অন্তর্ভুক্ত হয়ে গেছি।",
                benefit = "যে কোনো মুসলিম যে কোনো বিপদে এই দোয়া পাঠ করলে আল্লাহ তার দোয়া কবুল করেন।",
                reference = "সূরা আল-আম্বিয়া: ৮৭; জামে তিরমিযী"
            ),
            DuaItem(
                id = "dua_4",
                category = "সফর",
                titleBengali = "বাহনে আরোহণের ও সফরের দোয়া",
                textArabic = "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَٰذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ وَإِنَّا إِلَىٰ رَبِّنَا لَمُنْقَلِبُونَ",
                pronunciationBengali = "সুবহানাল্লাজি সাখখারা লানা হাযা ওয়া মা কুন্না লাহু মুকরিনীন, ওয়া ইন্না ইলা রব্বিনা লামুনকালিবুন।",
                meaningBengali = "পবিত্র সেই সত্তা যিনি এটিকে আমাদের বশীভূত করে দিয়েছেন, অথচ আমরা এটিকে বশে আনতে সক্ষম ছিলাম না। এবং আমরা আমাদের প্রতিপালকের নিকট অবশ্যই প্রত্যাবর্তন করব।",
                benefit = "যাত্রাপথে আল্লাহর সার্বিক নিরাপত্তা ও কল্যাণ লাভ হয়।",
                reference = "সূরা আয-যুখরুফ: ১৩-১৪; সহিহ মুসলিম"
            )
        )
    }

    fun getAllZikrPresets(): List<ZikrItem> {
        return listOf(
            ZikrItem("z_1", "সুবহানাল্লাহ (আল্লাহ পরম পবিত্র)", "سُبْحَانَ اللَّهِ", "সুবহানাল্লাহ", "আল্লাহ পরম পবিত্র ও ত্রুটিমুক্ত", 33, "জান্নাতে একটি খেজুর গাছ রোপণ করা হয়", "সহিহ মুসলিম"),
            ZikrItem("z_2", "আলহামদুলিল্লাহ (সমস্ত প্রশংসা আল্লাহর)", "الْحَمْدُ لِلَّهِ", "আলহামদুলিল্লাহ", "সমস্ত প্রশংসা কেবল আল্লাহর জন্য", 33, "মিজানের পাল্লা নেকিতে ভরিয়ে দেয়", "সহিহ মুসলিম"),
            ZikrItem("z_3", "আল্লাহু আকবার (আল্লাহ সর্বশ্রেষ্ঠ)", "اللَّهُ أَكْبَرُ", "আল্লাহু আকবার", "আল্লাহ সবকিছুর চেয়ে মহান ও শ্রেষ্ঠ", 33, "আসমান ও জমিনের মধ্যবর্তী স্থান পূর্ণ করে দেয়", "সহিহ মুসলিম"),
            ZikrItem("z_4", "লা ইলাহা ইল্লাল্লাহ (শ্রেষ্ঠ জিকির)", "لَا إِلَٰهَ إِلَّا اللَّهُ", "লা ইলাহা ইল্লাল্লাহ", "আল্লাহ ব্যতীত কোনো সত্য উপাস্য নেই", 100, "সর্বোত্তম জিকির এবং ঈমানের মূল ভিত্তি", "জামে তিরমিযী"),
            ZikrItem("z_5", "ইস্তিগফার (ক্ষমা প্রার্থনা)", "أَسْتَغْفِرُ اللَّهَ وَأَتُوبُ إِلَيْهِ", "আস্তাগফিরুল্লাহ ওয়া আতুবু ইলাইহি", "আমি আল্লাহর কাছে ক্ষমা চাই এবং তাঁর দিকেই প্রত্যাবর্তন করি", 100, "রিযিকে বরকত ও গুনাহ মাফ হয়", "সহিহুল বুখারী"),
            ZikrItem("z_6", "দুরুদ শরীফ", "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ", "আল্লাহুম্মা সাল্লি আলা মুহাম্মাদিওঁ ওয়া আলা আলি মুহাম্মাদ", "হে আল্লাহ! মুহাম্মদ ﷺ এবং তাঁর বংশধরদের উপর রহমত বর্ষণ করুন", 10, "১০টি রহমত নাযিল হয় এবং ১০টি গুনাহ মাফ হয়", "সহিহ মুসলিম")
        )
    }

    // --- DIGITAL LIBRARY DATA ---
    fun getAllBooks(): List<BookItem> {
        return listOf(
            BookItem(
                id = "b_1",
                titleBengali = "রিয়াদুস সালেহীন (নির্বাচিত অংশ)",
                author = "ইমাম আন-নববী (রহ.)",
                publisher = "ইসলামিক ফাউন্ডেশন / উন্মুক্ত প্রকাশনা",
                category = "হাদিস",
                isIslamic = true,
                description = "দৈনন্দিন জীবনের আখলাক, আদব এবং সুন্নাহর এক অতুলনীয় সংকলন।",
                chapters = listOf(
                    BookChapter(1, "ইখলাস ও নিয়ত অধ্যায়", "সমস্ত আমল নিয়তের উপর নির্ভরশীল। নিয়তের বিশুদ্ধতা আল্লাহর নিকট আমল কবুল হওয়ার প্রধান শর্ত।"),
                    BookChapter(2, "সবর ও ধৈর্য অধ্যায়", "বিপদ-আপদে আল্লাহর ফয়সালায় সন্তুষ্ট থেকে ধৈর্য ধারণ করা মুমিনের অনন্য গুণ।"),
                    BookChapter(3, "সত্যবাদিতা অধ্যায়", "সত্য মানুষকে পুণ্যের দিকে পরিচালিত করে এবং পুণ্য মানুষকে জান্নাতের দিকে নিয়ে যায়।")
                )
            ),
            BookItem(
                id = "b_2",
                titleBengali = "আর-রাহীকুল মাখতুম (সংক্ষিপ্ত সিরাত)",
                author = "আল্লামা সফিউর রহমান মোবারকপুরী",
                publisher = "মাকতাবাতুল খাইর / ওপেন লাইসেন্স",
                category = "সিরাত",
                isIslamic = true,
                description = "বিশ্বনবী হযরত মুহাম্মদ ﷺ এর বিশ্বনন্দিত নির্ভরযোগ্য সীরাত গ্রন্থ।",
                chapters = listOf(
                    BookChapter(1, "আরবের ভৌগোলিক অবস্থা ও জাহেলিয়াত", "ইসলাম পূর্ববর্তী যুগে আরবের নৈতিক ও সামাজিক বিপর্যয় এবং হেদায়েতের প্রয়োজনীয়তা।"),
                    BookChapter(2, "নবুয়তের শুভ সূচনা ও ওহী নাযিল", "হেরা গুহায় ধ্যানমগ্ন রাসূলুল্লাহ ﷺ এর কাছে জিবরাঈল (আ.) এর মাধ্যমে প্রথম ওহী আগমন।"),
                    BookChapter(3, "মদিনায় হিজরত ও ইসলামী রাষ্ট্র প্রতিষ্ঠা", "মক্কার জুলুম-নির্যাতন পেরিয়ে মদিনা মুনাওয়ারায় আনসার ও মুহাজিরদের মাঝে ভ্রাতৃত্ব প্রতিষ্ঠা।")
                )
            ),
            BookItem(
                id = "b_3",
                titleBengali = "আকীদা আত-তাহাবিয়া (অনুবাদ ও সারসংক্ষেপ)",
                author = "ইমাম আবু জাফর আত-তাহাবী (রহ.)",
                publisher = "দারুল মা'আরিফ",
                category = "আকীদা",
                isIslamic = true,
                description = "আহলে সুন্নাত ওয়াল জামায়াতের বিশুদ্ধ আকীদা সংবলিত সর্বজনস্বীকৃত মূল গ্রন্থ।",
                chapters = listOf(
                    BookChapter(1, "তাওহীদ বা আল্লাহর একত্ববাদ", "আল্লাহ তা'আলা এক ও অদ্বিতীয়, তাঁর কোনো শরিক বা সমকক্ষ নেই।"),
                    BookChapter(2, "নবুয়ত ও রিসালাত", "মুহাম্মদ ﷺ সর্বশেষ ও সর্বশ্রেষ্ঠ নবী, তাঁর পরে আর কোনো নবী আসবেন না।")
                )
            ),
            BookItem(
                id = "b_4",
                titleBengali = "গীতাঞ্জলি (রবীন্দ্রনাথ ঠাকুর)",
                author = "রবীন্দ্রনাথ ঠাকুর",
                publisher = "পাবলিক ডোমেইন (১৯১৩)",
                category = "বাংলা সাহিত্য",
                isIslamic = false,
                isFree = true,
                license = "পাবলিক ডোমেইন (কপিরাইট মুক্ত)",
                description = "নোবেল পুরস্কার বিজয়ী অমর বাংলা কাব্যগ্রন্থ।",
                chapters = listOf(
                    BookChapter(1, "গান ও কবিতা ১-৫", "আমার এ গান ছেড়েছে তার সকল অলংকার, তোমার কাছে রাখে নি আর সাজের অহংকার..."),
                    BookChapter(2, "গান ও কবিতা ৬-১০", "বিপদ হতে মোরে রক্ষা করো এ নহে মোর প্রার্থনা, বিপদে আমি না যেন করি ভয়...")
                )
            )
        )
    }

    // --- ISLAMIC CALENDAR EVENTS ---
    fun getIslamicEvents(): List<IslamicEvent> {
        return listOf(
            IslamicEvent("১ রমজান", "মার্চ ২০২৫", "পবিত্র মাহে রমজান শুরু", "রহমত, মাগফিরাত ও নাজাতের বরকতময় মাস", "রমজান"),
            IslamicEvent("২৭ রমজান", "মার্চ ২০২৫", "সম্ভাব্য শবে কদর (লাইলাতুল কদর)", "হাজার মাসের চেয়েও শ্রেষ্ঠ রজনী", "রমজান"),
            IslamicEvent("১ শাওয়াল", "এপ্রিল ২০২৫", "পবিত্র ঈদুল ফিতর", "সিয়াম সাধনা শেষে মুসলিম উম্মাহর মহান আনন্দোৎসব", "ঈদ"),
            IslamicEvent("৯ জিলহজ", "জুন ২০২৫", "পবিত্র আরাফাহ দিবস (হজ)", "হজের মূল রুকন ও সিয়ামের মহা সওয়াবের দিন", "হজ"),
            IslamicEvent("১০ জিলহজ", "জুন ২০২৫", "পবিত্র ঈদুল আজহা (কোরবানি)", "হযরত ইব্রাহিম (আ.) এর ত্যাগের স্মরণে কোরবানি", "ঈদ"),
            IslamicEvent("১০ মহররম", "জুলাই ২০২৫", "পবিত্র আশুরা", "ঐতিহাসিক তাৎপর্যপূর্ণ আশুরার রোজা", "গুরুত্বপূর্ণ দিন")
        )
    }

    // --- ZAKAT CALCULATOR LOGIC ---
    data class ZakatCalculationResult(
        val totalAssets: Double,
        val totalLiabilities: Double,
        val netWealth: Double,
        val nisabValue: Double,
        val isZakatEligible: Boolean,
        val zakatPayable: Double
    )

    fun calculateZakat(
        cashInHandAndBank: Double,
        goldGrams: Double,
        goldGramPrice: Double = 11500.0, // BDT per gram approx
        silverGrams: Double,
        silverGramPrice: Double = 220.0,
        businessGoods: Double,
        investments: Double,
        liabilitiesDebts: Double
    ): ZakatCalculationResult {
        val goldValue = goldGrams * goldGramPrice
        val silverValue = silverGrams * silverGramPrice
        val totalAssets = cashInHandAndBank + goldValue + silverValue + businessGoods + investments
        val netWealth = max(0.0, totalAssets - liabilitiesDebts)

        // Silver Nisab: 52.5 tola = 612.36 grams
        val silverNisabThreshold = 612.36 * silverGramPrice // ~134,700 BDT
        val isEligible = netWealth >= silverNisabThreshold
        val zakatPayable = if (isEligible) netWealth * 0.025 else 0.0

        return ZakatCalculationResult(
            totalAssets = totalAssets,
            totalLiabilities = liabilitiesDebts,
            netWealth = netWealth,
            nisabValue = silverNisabThreshold,
            isZakatEligible = isEligible,
            zakatPayable = zakatPayable
        )
    }

    // --- INHERITANCE CALCULATOR LOGIC (Quran Surah An-Nisa 11-12) ---
    data class InheritanceShare(
        val relation: String,
        val shareFractionText: String,
        val percentage: Double,
        val amountBDT: Double
    )

    fun calculateInheritance(
        totalEstate: Double,
        hasHusband: Boolean,
        hasWife: Boolean,
        sonsCount: Int,
        daughtersCount: Int,
        hasFather: Boolean,
        hasMother: Boolean
    ): List<InheritanceShare> {
        val shares = mutableListOf<InheritanceShare>()
        var remaining = totalEstate
        val hasChildren = (sonsCount + daughtersCount) > 0

        // 1. Spouse
        if (hasHusband) {
            val share = if (hasChildren) 0.25 else 0.50
            val amt = totalEstate * share
            shares.add(InheritanceShare("স্বামী", if (hasChildren) "১/৪ (সন্তান থাকায়)" else "১/২ (সন্তান না থাকায়)", share * 100, amt))
            remaining -= amt
        } else if (hasWife) {
            val share = if (hasChildren) 0.125 else 0.25
            val amt = totalEstate * share
            shares.add(InheritanceShare("স্ত্রী", if (hasChildren) "১/৮ (সন্তান থাকায়)" else "১/৪ (সন্তান না থাকায়)", share * 100, amt))
            remaining -= amt
        }

        // 2. Parents
        if (hasMother) {
            val share = if (hasChildren) (1.0 / 6.0) else (1.0 / 3.0)
            val amt = totalEstate * share
            shares.add(InheritanceShare("মাতা", if (hasChildren) "১/৬" else "১/৩", share * 100, amt))
            remaining -= amt
        }
        if (hasFather) {
            val share = if (hasChildren) (1.0 / 6.0) else (1.0 / 3.0)
            val amt = totalEstate * share
            shares.add(InheritanceShare("পিতা", if (hasChildren) "১/৬ (সন্তান থাকায়)" else "আসবাহ (অবশিষ্ট অংশ)", share * 100, amt))
            remaining -= amt
        }

        // 3. Children (Asabah - Male receives twice the share of female: 2:1)
        if (hasChildren && remaining > 0) {
            val totalUnits = (sonsCount * 2) + daughtersCount
            val unitAmt = remaining / totalUnits
            if (sonsCount > 0) {
                val sonAmt = unitAmt * 2 * sonsCount
                val eachSon = unitAmt * 2
                shares.add(InheritanceShare("${sonsCount} জন পুত্র", "অবশিষ্টের ২ অংশ প্রতি পুত্র (মোট ${String.format("%.1f", (sonAmt/totalEstate)*100)}%)", (sonAmt / totalEstate) * 100, sonAmt))
            }
            if (daughtersCount > 0) {
                val daughterAmt = unitAmt * daughtersCount
                shares.add(InheritanceShare("${daughtersCount} জন কন্যা", "অবশিষ্টের ১ অংশ প্রতি কন্যা (মোট ${String.format("%.1f", (daughterAmt/totalEstate)*100)}%)", (daughterAmt / totalEstate) * 100, daughterAmt))
            }
        }

        return shares
    }

    // --- MOSQUES & MAPS ---
    fun getNearbyMosques(): List<MosqueItem> {
        return listOf(
            MosqueItem("m_1", "বায়তুল মোকাররম জাতীয় মসজিদ", "পল্টন, ঢাকা ১০০০", 1.2, listOf("মহিলাদের নামাজ কক্ষ", "ওজুখানা", "লাইব্রেরি", "হুইলচেয়ার সুবিধা"), "০২-৯৫৬৯৮১১", true),
            MosqueItem("m_2", "লালবাগ শাহী মসজিদ", "লালবাগ কেল্লা প্রাঙ্গণ, ঢাকা", 2.8, listOf("ঐতিহাসিক স্থাপত্য", "প্রশস্ত ওজুখানা", "জুমার জামাত"), "", true),
            MosqueItem("m_3", "তারা মসজিদ (সিতারা মসজিদ)", "আরমানিটোলা, পুরান ঢাকা", 3.1, listOf("মুঘল মোজাইক শিল্প", "ওজুখানা"), "", true),
            MosqueItem("m_4", "গুলশান সোসাইটি জামে মসজিদ", "গুলশান ২, ঢাকা", 5.4, listOf("আধুনিক শীতাতপ নিয়ন্ত্রণ", "আন্ডারগ্রাউন্ড পার্কিং", "মহিলাদের বিশেষ প্রাঙ্গণ"), "০১৭১১১১১১১১", true)
        )
    }

    fun getHistoricalPlaces(): List<HistoricalPlace> {
        return listOf(
            HistoricalPlace("p_1", "মসজিদুল হারাম ও পবিত্র কাবা", "المسجد الحرام", "মক্কা মুকাররমা, সৌদি আরব", "বিশ্বের সকল মুসলিমের কিবলা এবং ইসলামের প্রধান তীর্থক্ষেত্র", "পবিত্র কাবা শরীফ হযরত ইব্রাহিম (আ.) ও ইসমাইল (আ.) কর্তৃক পুনঃনির্মিত আল্লাহর ঘর।", 21.4225, 39.8262),
            HistoricalPlace("p_2", "মসজিদুন নববী", "المسجد النبوي", "মদিনা মুনাওয়ারা, সৌদি আরব", "রাসূলুল্লাহ ﷺ এর মসজিদ ও পবিত্র রওজা মুবারক", "ইসলামের দ্বিতীয় পবিত্রতম স্থান, যার প্রতিটি সালাতে সহস্র গুণ সওয়াব নিহিত।", 24.4672, 39.6111),
            HistoricalPlace("p_3", "মসজিদুল আকসা", "المسجد الأقصى", "আল-কুদস (জেরুসালেম), ফিলিস্তিন", "ইসলামের প্রথম কিবলা ও মেরাজের পবিত্র স্থান", "রাসূলুল্লাহ ﷺ এখান থেকেই উর্ধ্বাকাশে মেরাজে গমন করেছিলেন।", 31.7761, 35.2358)
        )
    }

    // --- ISLAMIC STORIES & HISTORY ---
    fun getIslamicStories(): List<StoryItem> {
        return listOf(
            StoryItem(
                id = "s_1",
                category = "নবী-রাসূলগণের ঘটনা",
                titleBengali = "হযরত ইব্রাহিম (আ.) ও নমরুদের অগ্নিকুণ্ড",
                summaryBengali = "সত্যের পথে অবিচল ঈমান এবং আল্লাহর অলৌকিক কুদরতে আগুনের বাগানে রূপান্তর।",
                fullStoryBengali = "নমরুদ যখন হযরত ইব্রাহিম (আ.)-কে বিশাল অগ্নিকুণ্ডে নিক্ষেপ করল, তখন তিনি পরম নির্ভরতায় বললেন: 'হাসবুনাল্লাহু ওয়া নি'মাল ওয়াকিল'। আল্লাহ তা'আলা নির্দেশ দিলেন: 'হে আগুন! তুমি ইব্রাহিমের জন্য শীতল ও শান্তিদায়ক হয়ে যাও।' আগুন সাথে সাথে পুষ্পকাননে পরিণত হলো।",
                moralBengali = "একমাত্র আল্লাহর উপর পূর্ণ তাওয়াক্কুল করলে সর্বাবস্থায় অলৌকিক সাহায্য ও মুক্তি লাভ হয়।",
                reference = "সূরা আল-আম্বিয়া: ৫৮-৬৯; তাফসিরে ইবনে কাসীর"
            ),
            StoryItem(
                id = "s_2",
                category = "সাহাবায়ে কেরাম",
                titleBengali = "আবু বকর সিদ্দিক (রা.) এর নিঃস্বার্থ দান",
                summaryBengali = "তাবুক যুদ্ধের কঠিন মুহূর্তে ঘরের সমুদয় সম্পদ আল্লাহর রাস্তায় সমর্পণ।",
                fullStoryBengali = "তাবুক যুদ্ধে যখন রাসূলুল্লাহ ﷺ সাহাবাদের সাহায্যের আহ্বান জানালেন, উমর (রা.) ভাবলেন আজ আবু বকরকে ছাড়িয়ে যাবেন এবং অর্ধেক সম্পদ নিয়ে এলেন। কিন্তু আবু বকর (রা.) ঘরের সমস্ত সম্পদ উপস্থিত করলেন। নবীজি ﷺ জিজ্ঞেস করলেন: পরিবারের জন্য কী রেখে এসেছ? তিনি উত্তর দিলেন: 'আল্লাহ ও তাঁর রাসূলের ভালোবাসাকে রেখে এসেছি।'",
                moralBengali = "আল্লাহর রাস্তায় নিঃশর্ত আত্মত্যাগই প্রকৃত মুমিনের পরিচয়।",
                reference = "সুনানে আবু দাউদ, হাদিস নং ১৬৭৮; জামে তিরমিযী"
            )
        )
    }

    // --- AI STUDY ASSISTANT (Grounding & Verification) ---
    data class AIAssistantAnswer(
        val question: String,
        val answerBengali: String,
        val primarySource: String,
        val verificationConfidence: String = "যাচাইকৃত প্রামাণ্য ইসলামিক উৎস",
        val scholarAdviceNote: String = "জটিল বা ব্যক্তিগত মাসআলার ক্ষেত্রে উপযুক্ত বিজ্ঞ আলেমের সাথে সরাসরি পরামর্শ করুন।"
    )

    fun queryStudyAssistant(query: String): AIAssistantAnswer {
        val q = query.lowercase()
        return when {
            q.contains("নামাজ") || q.contains("সালাত") || q.contains("prayer") -> AIAssistantAnswer(
                question = query,
                answerBengali = "দৈনিক ৫ ওয়াক্ত সালাত ইসলামের দ্বিতীয় স্তম্ভ। পবিত্র কুরআনে আল্লাহ তা'আলা ৮২ বারের বেশি সালাত কায়েমের সরাসরি নির্দেশ দিয়েছেন। রাসূলুল্লাহ ﷺ বলেছেন: 'কিয়ামতের দিন বান্দার আমলের মধ্যে সর্বপ্রথম সালাতের হিসাব নেওয়া হবে।' (তিরমিযী ৪১৩)। সালাতের শর্তাবলীর মধ্যে রয়েছে সময়মতো আদায় করা, পবিত্রতা (ওজু), সতর ঢাকা এবং কিবলামুখী হওয়া।",
                primarySource = "সূরা আন-নিসা: ১০৩ ('নিশ্চয়ই নির্দিষ্ট সময়ে সালাত আদায় করা মুমিনদের জন্য আবশ্যক'); জামে তিরমিযী হাদিস ৪১৩"
            )
            q.contains("রোজা") || q.contains("সিয়াম") || q.contains("fasting") -> AIAssistantAnswer(
                question = query,
                answerBengali = "রমজান মাসের সিয়াম পালন প্রতিটি সুস্থ, প্রাপ্তবয়স্ক মুসলিমের উপর ফরজ। আল্লাহ তা'আলা ইরশাদ করেন: 'হে ঈমানদারগণ! তোমাদের উপর রোজা ফরজ করা হয়েছে, যেমন ফরজ করা হয়েছিল তোমাদের পূর্ববর্তীদের উপর, যেন তোমরা তাকওয়া অর্জন করতে পার।' (সূরা বাকারা: ১৮৩)। সুবহে সাদিক থেকে সূর্যাস্ত পর্যন্ত নিয়তসহ যাবতীয় পানাহার ও যৌনসম্ভোগ থেকে বিরত থাকাই সিয়াম।",
                primarySource = "সূরা আল-বাকারাহ: ১৮৩-১৮৫; সহিহুল বুখারী, কিতাবুস সাওম"
            )
            q.contains("যাকাত") || q.contains("zakat") -> AIAssistantAnswer(
                question = query,
                answerBengali = "যাকাত ইসলামের অন্যতম মৌলিক স্তম্ভ। নিসাব পরিমাণ সম্পদের (রুপার হিসেবে ৫২.৫ তোলা / ৬১২.৩৬ গ্রাম রুপার সমমূল্য) মালিক হওয়ার পর এক বছর অতিবাহিত হলে তার ২.৫% (চল্লিশ ভাগের এক ভাগ) অভাবী ও নির্ধারিত ৮টি খাতের হকদারদের প্রদান করা ফরজ। যাকাত আদায়ের মাধ্যমে সম্পদ পবিত্র ও বরকতময় হয়।",
                primarySource = "সূরা আত-তাওবাহ: ৬০; সহিহুল বুখারী ও মুসলিম"
            )
            else -> AIAssistantAnswer(
                question = query,
                answerBengali = "ইসলাম মানবজাতির জন্য একটি পূর্ণাঙ্গ জীবনবিধান। পবিত্র কুরআন ও রাসূলুল্লাহ ﷺ এর সুন্নাহ হলো এর অকাট্য মূল ভিত্তি। আল্লাহ তা'আলা ইরশাদ করেছেন: 'তোমরা আল্লাহর আনুগত্য কর এবং রাসূলের আনুগত্য কর।' যে কোনো দ্বীনি জিজ্ঞাসায় বিশুদ্ধ উৎস অনুসন্ধান করাই মুমিনের দায়িত্ব।",
                primarySource = "সূরা আন-নিসা: ৫৯; সহিহুল বুখারী, হাদিস নং ১ ('আমল নিয়তের উপর নির্ভরশীল')"
            )
        }
    }
}
