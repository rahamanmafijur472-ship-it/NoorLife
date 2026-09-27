package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.AppDatabase
import com.example.data.model.*
import com.example.data.repository.NoorLifeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "আজকের দ্বীন")
    object Prayer : Screen("prayer", "নামাজ ও ওয়াক্ত")
    object Quran : Screen("quran", "আল-কুরআন")
    object SurahDetail : Screen("surah_detail", "সূরা পাঠ")
    object Library : Screen("library", "লাইব্রেরি")
    object BookReader : Screen("book_reader", "বই পাঠ")
    object MoreMenu : Screen("more_menu", "আরও ফিচার")
    object Qibla : Screen("qibla", "কিবলা কম্পাস")
    object Hadith : Screen("hadith", "সহিহ হাদিস")
    object Dua : Screen("dua", "দোয়া ও মুনাজাত")
    object Tasbih : Screen("tasbih", "ডিজিটাল তসবিহ")
    object Calendar : Screen("calendar", "ইসলামিক ক্যালেন্ডার")
    object Ramadan : Screen("ramadan", "পবিত্র মাহে রমজান")
    object Hajj : Screen("hajj", "হজ ও ওমরাহ গাইড")
    object Zakat : Screen("zakat", "যাকাত ক্যালকুলেটর")
    object Inheritance : Screen("inheritance", "উত্তরাধিকার বণ্টন")
    object Jumuah : Screen("jumuah", "জুমার কেন্দ্র")
    object Purification : Screen("purification", "ওজু ও পবিত্রতা")
    object Kids : Screen("kids", "ছোটদের ইসলামিক জোন")
    object Arabic : Screen("arabic", "আরবি ভাষা শিক্ষা")
    object Education : Screen("education", "ইসলামিক কোর্স")
    object Stories : Screen("stories", "ইসলামিক গল্প ও ঘটনা")
    object MosqueFinder : Screen("mosque", "কাছের মসজিদ")
    object AIAssistant : Screen("ai_assistant", "নূর এআই সহকারী")
    object PersonalDeeds : Screen("personal_deeds", "আমার আমল ও শেখা")
    object GlobalSearch : Screen("global_search", "অনুসন্ধান")
    object Bookmarks : Screen("bookmarks", "সংরক্ষিত বিষয়")
    object AdminCMS : Screen("admin_cms", "অ্যাডমিন ও সিএমএস")
    object Settings : Screen("settings", "সেটিংস ও তথ্য")
}

class NoorLifeViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    val repository = NoorLifeRepository(db.noorLifeDao())

    // Navigation State
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _screenBackStack = mutableListOf<Screen>()

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            _screenBackStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (_screenBackStack.isNotEmpty()) {
            _currentScreen.value = _screenBackStack.removeAt(_screenBackStack.size - 1)
            return true
        }
        if (_currentScreen.value != Screen.Home) {
            _currentScreen.value = Screen.Home
            return true
        }
        return false
    }

    // Prayer Schedule State
    val prayerSchedule = MutableStateFlow(repository.getTodayPrayerSchedule())
    val nextPrayerInfo = MutableStateFlow(repository.getNextPrayerInfo())

    // Quran State
    val allSurahs = MutableStateFlow(repository.getAllSurahs())
    private val _selectedSurah = MutableStateFlow(repository.getSurahDetails(1))
    val selectedSurah: StateFlow<Surah> = _selectedSurah.asStateFlow()

    fun selectSurah(surahNumber: Int) {
        _selectedSurah.value = repository.getSurahDetails(surahNumber)
        navigateTo(Screen.SurahDetail)
    }

    // Hadith State
    val hadiths = MutableStateFlow(repository.getSampleHadiths())
    val hadithBooks = MutableStateFlow(repository.getHadithBooks())

    // Dua State
    val duas = MutableStateFlow(repository.getAllDuas())
    private val _selectedDuaCategory = MutableStateFlow("সকল")
    val selectedDuaCategory: StateFlow<String> = _selectedDuaCategory.asStateFlow()
    fun filterDuaCategory(category: String) { _selectedDuaCategory.value = category }

    // Tasbih State
    private val _currentZikr = MutableStateFlow(repository.getAllZikrPresets().first())
    val currentZikr: StateFlow<ZikrItem> = _currentZikr.asStateFlow()
    val zikrPresets = MutableStateFlow(repository.getAllZikrPresets())

    private val _tasbihCount = MutableStateFlow(0)
    val tasbihCount: StateFlow<Int> = _tasbihCount.asStateFlow()

    private val _tasbihTarget = MutableStateFlow(33)
    val tasbihTarget: StateFlow<Int> = _tasbihTarget.asStateFlow()

    fun incrementTasbih() {
        val next = _tasbihCount.value + 1
        _tasbihCount.value = next
        if (next >= _tasbihTarget.value) {
            viewModelScope.launch {
                repository.recordTasbih(_currentZikr.value.titleBengali, next, _tasbihTarget.value)
            }
        }
    }

    fun resetTasbih() {
        _tasbihCount.value = 0
    }

    fun selectZikr(zikr: ZikrItem) {
        _currentZikr.value = zikr
        _tasbihTarget.value = zikr.defaultTarget
        _tasbihCount.value = 0
    }

    // Library State
    val books = MutableStateFlow(repository.getAllBooks())
    private val _selectedBook = MutableStateFlow(repository.getAllBooks().first())
    val selectedBook: StateFlow<BookItem> = _selectedBook.asStateFlow()

    fun selectBook(book: BookItem) {
        _selectedBook.value = book
        navigateTo(Screen.BookReader)
    }

    // Zakat Calculator State
    val zakatResult = MutableStateFlow(repository.calculateZakat(0.0, 0.0, 11500.0, 0.0, 220.0, 0.0, 0.0, 0.0))
    fun calculateZakat(cash: Double, goldGrams: Double, silverGrams: Double, businessStock: Double, investments: Double, debts: Double) {
        zakatResult.value = repository.calculateZakat(cash, goldGrams, 11500.0, silverGrams, 220.0, businessStock, investments, debts)
    }

    // Inheritance State
    val inheritanceShares = MutableStateFlow(emptyList<NoorLifeRepository.InheritanceShare>())
    fun calculateInheritance(estate: Double, hasHusband: Boolean, hasWife: Boolean, sons: Int, daughters: Int, hasFather: Boolean, hasMother: Boolean) {
        inheritanceShares.value = repository.calculateInheritance(estate, hasHusband, hasWife, sons, daughters, hasFather, hasMother)
    }

    // Bookmarks Flow
    val bookmarks = repository.getAllBookmarks().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun toggleBookmark(type: String, id: String, title: String, subtitle: String, ref: String) {
        viewModelScope.launch {
            repository.addBookmark(type, id, title, subtitle, ref)
        }
    }

    // AI Assistant State
    private val _aiResponse = MutableStateFlow<NoorLifeRepository.AIAssistantAnswer?>(null)
    val aiResponse: StateFlow<NoorLifeRepository.AIAssistantAnswer?> = _aiResponse.asStateFlow()

    private val _aiQueryLoading = MutableStateFlow(false)
    val aiQueryLoading: StateFlow<Boolean> = _aiQueryLoading.asStateFlow()

    fun askAIAssistant(prompt: String) {
        if (prompt.isBlank()) return
        _aiQueryLoading.value = true
        viewModelScope.launch {
            kotlinx.coroutines.delay(600) // Brief simulation of grounded retrieval
            _aiResponse.value = repository.queryStudyAssistant(prompt)
            _aiQueryLoading.value = false
        }
    }

    // Global Search State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    data class SearchResult(
        val category: String,
        val title: String,
        val subtitle: String,
        val reference: String,
        val targetScreen: Screen
    )

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> = _searchResults.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        val q = query.lowercase()
        val results = mutableListOf<SearchResult>()

        // Search in Surahs
        allSurahs.value.filter { it.nameBengali.contains(q) || it.nameArabic.contains(q) || it.meaningBengali.contains(q) }
            .forEach {
                results.add(SearchResult("আল-কুরআন", it.nameBengali, "অর্থ: ${it.meaningBengali} (${it.totalVerses} আয়াত)", "সূরা নং ${it.number}", Screen.Quran))
            }

        // Search in Hadiths
        hadiths.value.filter { it.translationBengali.contains(q) || it.chapterNameBengali.contains(q) }
            .forEach {
                results.add(SearchResult("হাদিস", it.bookNameBengali, it.translationBengali.take(60) + "...", it.reference, Screen.Hadith))
            }

        // Search in Duas
        duas.value.filter { it.titleBengali.contains(q) || it.meaningBengali.contains(q) }
            .forEach {
                results.add(SearchResult("দোয়া", it.titleBengali, it.meaningBengali.take(60) + "...", it.reference, Screen.Dua))
            }

        // Search in Books
        books.value.filter { it.titleBengali.contains(q) || it.author.contains(q) }
            .forEach {
                results.add(SearchResult("লাইব্রেরি", it.titleBengali, "লেখক: ${it.author}", it.publisher, Screen.Library))
            }

        _searchResults.value = results
    }

    // User Profile & Settings
    val userProfile = repository.getUserProfile().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        UserProfileEntity()
    )

    fun updateKidsMode(active: Boolean) {
        viewModelScope.launch {
            val curr = userProfile.value ?: UserProfileEntity()
            repository.saveUserProfile(curr.copy(kidsModeActive = active))
        }
    }

    fun updateCalculationMethod(method: String) {
        viewModelScope.launch {
            val curr = userProfile.value ?: UserProfileEntity()
            repository.saveUserProfile(curr.copy(prayerCalculationMethod = method))
        }
    }
}
