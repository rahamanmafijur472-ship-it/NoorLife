package com.example

import com.example.data.repository.NoorLifeRepository
import com.example.ui.icons.NoorLifeIconRegistry
import com.example.ui.icons.NoorLifeIconType
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testNoorLifeIconRegistryKeys() {
        // Enforce the PREMIUM MASTER ICON SYSTEM requirements
        val requiredKeys = listOf(
            NoorLifeIconRegistry.KEY_HOME,
            NoorLifeIconRegistry.KEY_PRAYER,
            NoorLifeIconRegistry.KEY_SALAH,
            NoorLifeIconRegistry.KEY_QIBLA,
            NoorLifeIconRegistry.KEY_QURAN,
            NoorLifeIconRegistry.KEY_HADITH,
            NoorLifeIconRegistry.KEY_DUA,
            NoorLifeIconRegistry.KEY_ZIKR,
            NoorLifeIconRegistry.KEY_TASBIH,
            NoorLifeIconRegistry.KEY_CALENDAR,
            NoorLifeIconRegistry.KEY_RAMADAN,
            NoorLifeIconRegistry.KEY_HAJJ,
            NoorLifeIconRegistry.KEY_UMRAH,
            NoorLifeIconRegistry.KEY_ZAKAT,
            NoorLifeIconRegistry.KEY_INHERITANCE,
            NoorLifeIconRegistry.KEY_JUMUAH,
            NoorLifeIconRegistry.KEY_LIBRARY,
            NoorLifeIconRegistry.KEY_KIDS,
            NoorLifeIconRegistry.KEY_ARABIC,
            NoorLifeIconRegistry.KEY_MOSQUE,
            NoorLifeIconRegistry.KEY_AI,
            NoorLifeIconRegistry.KEY_PERSONAL,
            NoorLifeIconRegistry.KEY_SETTINGS
        )

        for (key in requiredKeys) {
            assertTrue("Key $key should be registered in NoorLifeIconRegistry", NoorLifeIconRegistry.containsKey(key))
            val drawableRes = NoorLifeIconRegistry.getDrawable(key)
            assertTrue("Drawable resource for $key must be valid (> 0)", drawableRes > 0)
            val metadata = NoorLifeIconRegistry.getMetadata(key)
            assertNotNull("Metadata for $key should not be null", metadata)
            assertTrue("Bengali title for $key should not be blank", metadata!!.bengaliTitle.isNotBlank())
        }
    }

    @Test
    fun testNoorLifeIconRegistryAllEnumTypes() {
        // Test that all enum types map to valid vector drawables
        for (type in NoorLifeIconType.values()) {
            val resId = NoorLifeIconRegistry.getDrawable(type)
            assertTrue("Type $type must map to a valid drawable (> 0)", resId > 0)
        }
    }

    @Test
    fun testQiblaAngleCalculation() {
        // Dhaka coordinates: 23.8103, 90.4125
        val repo = NoorLifeRepository(MockNoorLifeDao())
        val angle = repo.calculateQiblaAngle(23.8103, 90.4125)
        // Qibla from Bangladesh is West-Northwest, roughly 273.5 degrees
        assertTrue("Qibla angle from Dhaka should be around 273°", angle in 270.0..276.0)
    }

    @Test
    fun testZakatCalculation() {
        val repo = NoorLifeRepository(MockNoorLifeDao())
        // Cash: 200,000 BDT (Above silver nisab of ~134,700 BDT), No debts
        val result = repo.calculateZakat(
            cashInHandAndBank = 200000.0,
            goldGrams = 0.0,
            goldGramPrice = 11500.0,
            silverGrams = 0.0,
            silverGramPrice = 220.0,
            businessGoods = 0.0,
            investments = 0.0,
            liabilitiesDebts = 0.0
        )
        assertTrue("Net wealth above nisab should be eligible", result.isZakatEligible)
        assertEquals(5000.0, result.zakatPayable, 0.01) // 2.5% of 200,000 = 5,000
    }

    @Test
    fun testInheritanceCalculation() {
        val repo = NoorLifeRepository(MockNoorLifeDao())
        // Estate: 80,000 BDT, Wife + 1 Son + 1 Daughter
        val shares = repo.calculateInheritance(
            totalEstate = 80000.0,
            hasHusband = false,
            hasWife = true,
            sonsCount = 1,
            daughtersCount = 1,
            hasFather = false,
            hasMother = false
        )
        val wifeShare = shares.find { it.relation == "স্ত্রী" }
        assertNotNull(wifeShare)
        assertEquals(10000.0, wifeShare!!.amountBDT, 0.01)
    }

    // Mock Dao for unit testing business logic in isolation
    class MockNoorLifeDao : com.example.data.database.NoorLifeDao {
        override fun getUserProfile(id: String) = kotlinx.coroutines.flow.flowOf(null)
        override suspend fun saveUserProfile(profile: com.example.data.model.UserProfileEntity) {}
        override fun getAllBookmarks() = kotlinx.coroutines.flow.flowOf(emptyList<com.example.data.model.BookmarkEntity>())
        override fun getBookmarksByType(type: String) = kotlinx.coroutines.flow.flowOf(emptyList<com.example.data.model.BookmarkEntity>())
        override fun isBookmarked(type: String, contentId: String) = kotlinx.coroutines.flow.flowOf(false)
        override suspend fun insertBookmark(bookmark: com.example.data.model.BookmarkEntity) = 1L
        override suspend fun deleteBookmark(type: String, contentId: String) {}
        override fun getAllNotes() = kotlinx.coroutines.flow.flowOf(emptyList<com.example.data.model.NoteEntity>())
        override suspend fun insertNote(note: com.example.data.model.NoteEntity) = 1L
        override suspend fun deleteNote(note: com.example.data.model.NoteEntity) {}
        override fun getTasbihRecords() = kotlinx.coroutines.flow.flowOf(emptyList<com.example.data.model.TasbihRecordEntity>())
        override suspend fun insertTasbihRecord(record: com.example.data.model.TasbihRecordEntity) {}
        override fun getPrayerTracking(dateString: String) = kotlinx.coroutines.flow.flowOf(null)
        override suspend fun savePrayerTracking(tracking: com.example.data.model.PrayerTrackingEntity) {}
        override fun getHabitsForDate(dateString: String) = kotlinx.coroutines.flow.flowOf(emptyList<com.example.data.model.HabitTrackingEntity>())
        override suspend fun insertOrUpdateHabit(habit: com.example.data.model.HabitTrackingEntity) {}
        override fun getAllReadingProgress() = kotlinx.coroutines.flow.flowOf(emptyList<com.example.data.model.ReadingProgressEntity>())
        override fun getReadingProgress(key: String) = kotlinx.coroutines.flow.flowOf(null)
        override suspend fun saveReadingProgress(progress: com.example.data.model.ReadingProgressEntity) {}
        override suspend fun insertReport(report: com.example.data.model.ContentReportEntity) = 1L
        override fun getAllReports() = kotlinx.coroutines.flow.flowOf(emptyList<com.example.data.model.ContentReportEntity>())
    }
}
