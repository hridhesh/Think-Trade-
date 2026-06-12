package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.TradingViewModel
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SimulatorScreen
import com.example.ui.theme.MyApplicationTheme

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContainer()
            }
        }
    }
}

@Composable
fun MainAppContainer() {
    val viewModel: TradingViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    var currentTab by remember { mutableStateOf("learn") } // "learn", "quiz", "simulator", "profile"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF8FAFC), // Slate 50 - Vibrant Palette Screen background
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Trader Academy",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Trade Academy Icon",
                        tint = Color(0xFFFBBF24), // Amber 400 star color accent
                        modifier = Modifier
                            .padding(start = 12.dp, end = 8.dp)
                            .size(24.dp)
                    )
                },
                actions = {
                    // Modern Translucent rounded white bubble badge for XP/Points as in design spec
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .background(
                                color = Color(0x33FFFFFF), // Translucent white (white/20)
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "⭐",
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = "${state.points} XP",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.testTag("xp_badge")
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4F46E5), // Indigo 600
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 6.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                NavigationBarItem(
                    selected = currentTab == "learn",
                    onClick = { currentTab = "learn" },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Learn") },
                    label = { Text("Learn", fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4F46E5),
                        selectedTextColor = Color(0xFF4F46E5),
                        unselectedIconColor = Color(0xFF94A3B8), // slate-400
                        unselectedTextColor = Color(0xFF94A3B8),
                        indicatorColor = Color(0xFFEEF2FF) // Indigo 50
                    ),
                    modifier = Modifier.testTag("nav_learn")
                )

                NavigationBarItem(
                    selected = currentTab == "quiz",
                    onClick = { currentTab = "quiz" },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Quizzes") },
                    label = { Text("Ranks", fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4F46E5),
                        selectedTextColor = Color(0xFF4F46E5),
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8),
                        indicatorColor = Color(0xFFEEF2FF)
                    ),
                    modifier = Modifier.testTag("nav_quiz")
                )

                NavigationBarItem(
                    selected = currentTab == "simulator",
                    onClick = { currentTab = "simulator" },
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Simulator") },
                    label = { Text("Practice", fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4F46E5),
                        selectedTextColor = Color(0xFF4F46E5),
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8),
                        indicatorColor = Color(0xFFEEF2FF)
                    ),
                    modifier = Modifier.testTag("nav_simulator")
                )

                NavigationBarItem(
                    selected = currentTab == "profile",
                    onClick = { currentTab = "profile" },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile", fontWeight = FontWeight.Bold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4F46E5),
                        selectedTextColor = Color(0xFF4F46E5),
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8),
                        indicatorColor = Color(0xFFEEF2FF)
                    ),
                    modifier = Modifier.testTag("nav_profile")
                )
            }
        }
    ) { innerPadding ->
        InteractiveAnimatedContent(
            targetState = currentTab,
            modifier = Modifier.padding(innerPadding)
        ) { tab ->
            when (tab) {
                "learn" -> LearnScreen(
                    state = state,
                    onCategorySelected = { viewModel.setCategory(it) },
                    onLevelSelected = { viewModel.setLevel(it) },
                    onLessonCompleted = { id, reward -> viewModel.completeLesson(id, reward) }
                )
                "quiz" -> QuizScreen(
                    state = state,
                    onStartQuiz = { level, cat -> viewModel.startQuizForLevel(level, cat) },
                    onSelectAnswer = { viewModel.selectQuizAnswer(it) },
                    onSubmitAnswer = { viewModel.submitQuizAnswer() },
                    onNextQuestion = { viewModel.nextQuizQuestion() }
                )
                "simulator" -> SimulatorScreen(
                    state = state,
                    onMakeChoice = { viewModel.makeSimulatorChoice(it) },
                    onNextChallenge = { viewModel.generateNewSimulatorChallenge() }
                )
                "profile" -> ProfileScreen(
                    state = state
                )
            }
        }
    }
}

@Composable
fun InteractiveAnimatedContent(
    targetState: String,
    modifier: Modifier = Modifier,
    content: @Composable (String) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        modifier = modifier,
        label = "tabChangeTransition"
    ) { stateVal ->
        content(stateVal)
    }
}
