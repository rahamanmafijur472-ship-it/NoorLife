package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.NoorLifeViewModel
import com.example.ui.Screen
import com.example.ui.icons.NoorLifeIcon
import com.example.ui.icons.NoorLifeIconType
import com.example.ui.screens.*
import com.example.ui.theme.BrightGold
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.NoorLifeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: NoorLifeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoorLifeTheme {
                val currentScreen by viewModel.currentScreen.collectAsState()

                BackHandler(enabled = currentScreen != Screen.Home) {
                    viewModel.navigateBack()
                }

                val showBottomBar = currentScreen in listOf(
                    Screen.Home, Screen.Prayer, Screen.Quran, Screen.Library, Screen.MoreMenu
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surface,
                                tonalElevation = 8.dp
                            ) {
                                NavigationBarItem(
                                    selected = currentScreen == Screen.Home,
                                    onClick = { viewModel.navigateTo(Screen.Home) },
                                    icon = {
                                        NoorLifeIcon(
                                            type = NoorLifeIconType.Home,
                                            size = 24.dp,
                                            tint = if (currentScreen == Screen.Home) EmeraldPrimary else Color.Gray,
                                            accentTint = BrightGold
                                        )
                                    },
                                    label = { Text("হোম", fontWeight = if (currentScreen == Screen.Home) FontWeight.Bold else FontWeight.Normal) }
                                )

                                NavigationBarItem(
                                    selected = currentScreen == Screen.Prayer,
                                    onClick = { viewModel.navigateTo(Screen.Prayer) },
                                    icon = {
                                        NoorLifeIcon(
                                            type = NoorLifeIconType.Prayer,
                                            size = 24.dp,
                                            tint = if (currentScreen == Screen.Prayer) EmeraldPrimary else Color.Gray,
                                            accentTint = BrightGold
                                        )
                                    },
                                    label = { Text("নামাজ", fontWeight = if (currentScreen == Screen.Prayer) FontWeight.Bold else FontWeight.Normal) }
                                )

                                NavigationBarItem(
                                    selected = currentScreen == Screen.Quran,
                                    onClick = { viewModel.navigateTo(Screen.Quran) },
                                    icon = {
                                        NoorLifeIcon(
                                            type = NoorLifeIconType.Quran,
                                            size = 24.dp,
                                            tint = if (currentScreen == Screen.Quran) EmeraldPrimary else Color.Gray,
                                            accentTint = BrightGold
                                        )
                                    },
                                    label = { Text("কুরআন", fontWeight = if (currentScreen == Screen.Quran) FontWeight.Bold else FontWeight.Normal) }
                                )

                                NavigationBarItem(
                                    selected = currentScreen == Screen.Library,
                                    onClick = { viewModel.navigateTo(Screen.Library) },
                                    icon = {
                                        NoorLifeIcon(
                                            type = NoorLifeIconType.Library,
                                            size = 24.dp,
                                            tint = if (currentScreen == Screen.Library) EmeraldPrimary else Color.Gray,
                                            accentTint = BrightGold
                                        )
                                    },
                                    label = { Text("লাইব্রেরি", fontWeight = if (currentScreen == Screen.Library) FontWeight.Bold else FontWeight.Normal) }
                                )

                                NavigationBarItem(
                                    selected = currentScreen == Screen.MoreMenu,
                                    onClick = { viewModel.navigateTo(Screen.MoreMenu) },
                                    icon = {
                                        NoorLifeIcon(
                                            type = NoorLifeIconType.Settings,
                                            size = 24.dp,
                                            tint = if (currentScreen == Screen.MoreMenu) EmeraldPrimary else Color.Gray,
                                            accentTint = BrightGold
                                        )
                                    },
                                    label = { Text("আরও", fontWeight = if (currentScreen == Screen.MoreMenu) FontWeight.Bold else FontWeight.Normal) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = if (showBottomBar) innerPadding.calculateBottomPadding() else 0.dp)
                    ) {
                        when (currentScreen) {
                            Screen.Home -> HomeScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                            Screen.Prayer -> PrayerScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                            Screen.Quran -> QuranScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                            Screen.SurahDetail -> SurahDetailScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Library -> LibraryScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                            Screen.BookReader -> BookReaderScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.MoreMenu -> MoreMenuScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                            Screen.Qibla -> QiblaScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Hadith -> HadithScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Dua -> DuaZikrScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Tasbih -> TasbihScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Calendar -> CalendarScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Ramadan -> RamadanScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Hajj -> HajjScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Zakat -> ZakatScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Inheritance -> InheritanceScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Jumuah -> JumuahScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Purification -> PurificationScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Kids -> KidsScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Arabic -> ArabicScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Education -> EducationScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.MosqueFinder -> MosqueFinderScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.AIAssistant -> AIAssistantScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.PersonalDeeds -> PersonalDeedsScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.GlobalSearch -> SearchScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) }, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Bookmarks -> BookmarksScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.AdminCMS -> AdminCMSScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            Screen.Settings -> SettingsScreen(viewModel = viewModel, onNavigateBack = { viewModel.navigateBack() })
                            else -> HomeScreen(viewModel = viewModel, onNavigate = { viewModel.navigateTo(it) })
                        }
                    }
                }
            }
        }
    }
}
