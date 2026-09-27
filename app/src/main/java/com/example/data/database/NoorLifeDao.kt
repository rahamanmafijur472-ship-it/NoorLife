package com.example.data.database

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoorLifeDao {
    // User Profile
    @Query("SELECT * FROM user_profiles WHERE id = :id LIMIT 1")
    fun getUserProfile(id: String = "default_user"): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)

    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE contentType = :type ORDER BY timestamp DESC")
    fun getBookmarksByType(type: String): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE contentType = :type AND contentId = :contentId)")
    fun isBookmarked(type: String, contentId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity): Long

    @Query("DELETE FROM bookmarks WHERE contentType = :type AND contentId = :contentId")
    suspend fun deleteBookmark(type: String, contentId: String)

    // Notes
    @Query("SELECT * FROM user_notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    // Tasbih Records
    @Query("SELECT * FROM tasbih_records ORDER BY timestamp DESC LIMIT 100")
    fun getTasbihRecords(): Flow<List<TasbihRecordEntity>>

    @Insert
    suspend fun insertTasbihRecord(record: TasbihRecordEntity)

    // Prayer Tracking
    @Query("SELECT * FROM prayer_tracking WHERE dateString = :dateString LIMIT 1")
    fun getPrayerTracking(dateString: String): Flow<PrayerTrackingEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePrayerTracking(tracking: PrayerTrackingEntity)

    // Habit Tracking
    @Query("SELECT * FROM habit_tracking WHERE dateString = :dateString")
    fun getHabitsForDate(dateString: String): Flow<List<HabitTrackingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateHabit(habit: HabitTrackingEntity)

    // Reading Progress
    @Query("SELECT * FROM reading_progress ORDER BY lastUpdated DESC")
    fun getAllReadingProgress(): Flow<List<ReadingProgressEntity>>

    @Query("SELECT * FROM reading_progress WHERE contentKey = :key LIMIT 1")
    fun getReadingProgress(key: String): Flow<ReadingProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReadingProgress(progress: ReadingProgressEntity)

    // Content Reports
    @Insert
    suspend fun insertReport(report: ContentReportEntity): Long

    @Query("SELECT * FROM content_reports ORDER BY timestamp DESC")
    fun getAllReports(): Flow<List<ContentReportEntity>>
}
