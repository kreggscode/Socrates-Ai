package com.kreggscode.socratesquotes.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.kreggscode.socratesquotes.ui.components.*
import com.kreggscode.socratesquotes.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedAboutScreen(
    onBackClick: () -> Unit,
    onWorkClick: ((String) -> Unit)? = null
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Biography", "Works", "Legacy", "Timeline", "Policies")
    
    // Handle back button - if on another tab, go to Biography first, then exit
    BackHandler(enabled = selectedTab != 0) {
        if (selectedTab != 0) {
            selectedTab = 0
        } else {
            onBackClick()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PremiumColors.DeepSpace,
                        PremiumColors.MidnightBlue,
                        PremiumColors.DeepSpace
                    )
                )
            )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "About Socrates",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Tabs
                PrimaryScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    edgePadding = 16.dp
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
                
                // Content
                when (selectedTab) {
                    0 -> BiographyTab()
                    1 -> WorksTab(onWorkClick = onWorkClick)
                    2 -> LegacyTab()
                    3 -> TimelineTab()
                    4 -> PoliciesTab()
                }
            }
        }
    }
}

@Composable
fun BiographyTab() {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            GlassmorphicCard(
                modifier = Modifier.fillMaxWidth(),
                glowColor = PremiumColors.ElectricPurple,
                animateGlow = true
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🏛️", fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Socrates",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "470 BC - 399 BC",
                        style = MaterialTheme.typography.titleMedium,
                        color = PremiumColors.CyberBlue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Father of Western Philosophy",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
        
        item {
            InfoCard(
                title = "Early Life",
                icon = "👶",
                content = "Born around 470 BC in Athens, Greece. Son of Sophroniscus (a stonemason) and Phainarete (a midwife). Grew up in Athens during its Golden Age. Received basic education in music, gymnastics, and grammar."
            )
        }
        
        item {
            InfoCard(
                title = "Marriage and Family",
                icon = "👨‍👩‍👧‍👦",
                content = "Married Xanthippe, known for her sharp temper. Had three sons: Lamprocles, Sophroniscus, and Menexenus. Despite family responsibilities, devoted his life to philosophy and teaching."
            )
        }
        
        item {
            InfoCard(
                title = "Military Service (432-422 BC)",
                icon = "⚔️",
                content = "Served as a hoplite soldier in the Peloponnesian War. Fought at Potidaea, Delium, and Amphipolis. Known for his courage and endurance. Saved Alcibiades' life at Potidaea."
            )
        }
        
        item {
            InfoCard(
                title = "Philosophical Mission (440-399 BC)",
                icon = "🏛️",
                content = "Spent his life questioning Athenians in the agora (marketplace). Developed the Socratic Method of inquiry through dialogue. Attracted followers including Plato. Called himself the 'gadfly' of Athens."
            )
        }
        
        item {
            InfoCard(
                title = "Final Years",
                icon = "📜",
                content = "Charged with impiety and corrupting youth in 399 BC. Refused to compromise his principles or flee Athens. Sentenced to death by drinking hemlock. Died at age 70, leaving no writings but inspiring Western philosophy."
            )
        }
    }
}

@Composable
fun WorksTab(onWorkClick: ((String) -> Unit)? = null) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HighlightCard(
                title = "The Golden Mean",
                subtitle = "Virtue as the Middle Path",
                description = "Socrates's doctrine that moral virtue is a mean between extremes of excess and deficiency. Courage, for example, lies between cowardice and recklessness.",
                gradient = listOf(PremiumColors.PlasmaOrange, PremiumColors.NeonPink)
            )
        }
        
        item {
            Text(
                "Major Works",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        val works = listOf(
            Work("Nicomachean Ethics", "-340 BC", "Comprehensive treatise on virtue, happiness, and the good life through practical wisdom", "⭐", "nicomachean_ethics"),
            Work("Politics", "-350 BC", "Analysis of political communities, constitutions, and the ideal state", "🏛️", "politics"),
            Work("Metaphysics", "-350 BC", "Investigation into being as being, substance, causality, and fundamental principles of reality", "🔍", "metaphysics"),
            Work("Physics", "-350 BC", "Foundational work on natural philosophy, exploring motion, change, time, and principles of the physical world", "🌌", "physics"),
            Work("Poetics", "-335 BC", "Analysis of poetry and drama, defining the elements of tragedy and its psychological impact", "🎭", "poetics"),
            Work("On the Soul (De Anima)", "-350 BC", "Investigation into the nature of the soul as the form and actuality of living bodies", "🧠", "on_the_soul"),
            Work("Rhetoric", "-350 BC", "Systematic analysis of persuasive speech as essential for civic life and discovering truth", "💬", "rhetoric"),
            Work("Categories", "-340 BC", "Analysis of the basic types of being and the structure of predication", "📂", "categories"),
            Work("Posterior Analytics", "-350 BC", "Analysis of scientific demonstration and how we gain certain knowledge from first principles", "🧮", "posterior_analytics"),
            Work("Prior Analytics", "-350 BC", "Creation of syllogistic logic - the first formal system of deductive reasoning", "⚙️", "prior_analytics"),
            Work("Generation of Animals", "-350 BC", "Detailed study of animal reproduction, heredity, and embryonic development", "🧬", "generation_of_animals"),
            Work("Parts of Animals", "-350 BC", "Analysis of animal anatomy and the functional purposes of biological structures", "🔬", "parts_of_animals"),
            Work("History of Animals", "-350 BC", "Massive compilation of observations about animal behavior, anatomy, and classification", "🐘", "history_of_animals"),
            Work("On Interpretation", "-350 BC", "Analysis of language, truth, and meaning, focusing on propositions and their relationship to reality", "💭", "on_interpretation"),
            Work("Eudemian Ethics", "-350 BC", "Alternative ethical system with different emphases than Nicomachean Ethics", "⚖️", "eudemian_ethics"),
            Work("Constitution of Athens", "-320 BC", "Historical analysis of Athenian constitutional development - only surviving work from 158 constitutions", "📜", "constitution_of_athens"),
            Work("Topics", "-350 BC", "Dialectical reasoning and argumentation", "🎯", null),
            Work("On the Heavens", "-350 BC", "Cosmology and astronomy", "🌟", null),
            Work("Meteorology", "-340 BC", "Weather, climate, and atmospheric phenomena", "⛈️", null),
            Work("On Memory", "-350 BC", "Psychology of memory and recollection", "🧩", null)
        )
        
        items(works) { work ->
            WorkCard(work = work, onClick = {
                work.id?.let { id -> onWorkClick?.invoke(id) }
            })
        }
    }
}

@Composable
fun LegacyTab() {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            InfoCard(
                title = "Philosophical Contributions",
                icon = "🧠",
                content = "Developed the Socratic Method of inquiry through questioning. Pioneered ethical philosophy focused on virtue and the good life. Established the examined life as essential to human flourishing. Introduced intellectual humility."
            )
        }
        
        item {
            InfoCard(
                title = "Ethical Contributions",
                icon = "⚖️",
                content = "Argued that virtue is knowledge and no one does wrong willingly. Emphasized self-knowledge as the foundation of wisdom. Taught that the unexamined life is not worth living. Influenced all subsequent moral philosophy."
            )
        }
        
        item {
            InfoCard(
                title = "Educational Legacy",
                icon = "📚",
                content = "The Socratic Method became foundational to Western education. His emphasis on self-examination and virtue influenced all subsequent philosophy. Inspired Plato's Academy and shaped ethical inquiry for millennia."
            )
        }
        
        item {
            InfoCard(
                title = "Modern Influence",
                icon = "🎓",
                content = "The Socratic Method remains central to law school education and critical thinking. His emphasis on questioning authority influenced the Enlightenment. Inspired existentialism and humanistic psychology."
            )
        }
        
        item {
            InfoCard(
                title = "Modern Relevance",
                icon = "💡",
                content = "Socratic questioning used in therapy, coaching, and education. His emphasis on self-knowledge influences mindfulness and personal development. The examined life remains a model for authentic living."
            )
        }
    }
}

@Composable
fun TimelineTab() {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val timeline = listOf(
            TimelineEvent("470 BC", "Born in Athens, Greece", "👶"),
            TimelineEvent("450 BC", "Receives basic Athenian education", "🎓"),
            TimelineEvent("432 BC", "Serves at Battle of Potidaea", "⚔️"),
            TimelineEvent("424 BC", "Fights at Battle of Delium", "🛡️"),
            TimelineEvent("422 BC", "Serves at Battle of Amphipolis", "⚔️"),
            TimelineEvent("420 BC", "Marries Xanthippe", "💑"),
            TimelineEvent("410 BC", "Develops Socratic Method", "💭"),
            TimelineEvent("405 BC", "Plato becomes his student", "🎓"),
            TimelineEvent("399 BC", "Trial and execution by hemlock", "⚖️"),
            TimelineEvent("399 BC", "Dies at age 70 in Athens", "⭐")
        )
        
        items(timeline) { event ->
            TimelineCard(event)
        }
    }
}

data class Work(
    val title: String,
    val year: String,
    val description: String,
    val icon: String,
    val id: String? = null
)

data class TimelineEvent(
    val year: String,
    val event: String,
    val icon: String
)

@Composable
fun InfoCard(title: String, icon: String, content: String) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.CyberBlue
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(icon, fontSize = 32.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                content,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun HighlightCard(
    title: String,
    subtitle: String,
    description: String,
    gradient: List<Color>
) {
    PremiumGlassCard(
        modifier = Modifier.fillMaxWidth(),
        gradientColors = gradient
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                title,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                subtitle,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun WorkCard(work: Work, onClick: (() -> Unit)? = null) {
    val modifier = if (onClick != null && work.id != null) {
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    } else {
        Modifier.fillMaxWidth()
    }
    
    GlassmorphicCard(
        modifier = modifier,
        glowColor = if (work.id != null) PremiumColors.ElectricPurple else PremiumColors.ElectricPurple.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(work.icon, fontSize = 32.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        work.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        work.year,
                        style = MaterialTheme.typography.labelMedium,
                        color = PremiumColors.CyberBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        work.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
            
            if (work.id != null) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "View details",
                    tint = PremiumColors.ElectricPurple,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun TimelineCard(event: TimelineEvent) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(PremiumColors.ElectricPurple),
                contentAlignment = Alignment.Center
            ) {
                Text(event.icon, fontSize = 20.sp)
            }
            if (event != TimelineEvent("399 BC", "Dies at age 70 in Athens", "⭐")) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(60.dp)
                        .background(PremiumColors.ElectricPurple.copy(alpha = 0.3f))
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        GlassmorphicCard(
            modifier = Modifier.weight(1f),
            glowColor = PremiumColors.CyberBlue
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    event.year,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = PremiumColors.CyberBlue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    event.event,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun PoliciesTab() {
    var selectedPolicy by rememberSaveable { mutableStateOf<String?>(null) }
    
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (selectedPolicy != null) {
            // Back button
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedPolicy = null }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = PremiumColors.ElectricPurple
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Back to Policies",
                        style = MaterialTheme.typography.titleMedium,
                        color = PremiumColors.ElectricPurple,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Show selected policy content
            if (selectedPolicy == "privacy") {
                privacyPolicyContent()
            } else if (selectedPolicy == "terms") {
                termsAndConditionsContent()
            }
        } else {
            // Show policy selection
            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    glowColor = PremiumColors.ElectricPurple,
                    animateGlow = true
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📋", fontSize = 64.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Policies & Legal",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Read our privacy policy and terms of service",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            item {
                PolicyCard(
                    title = "Privacy Policy",
                    icon = "🔒",
                    description = "Learn how we protect your data and respect your privacy",
                    onClick = { selectedPolicy = "privacy" }
                )
            }
            
            item {
                PolicyCard(
                    title = "Terms & Conditions",
                    icon = "📜",
                    description = "Read the terms of service for using Socrates AI",
                    onClick = { selectedPolicy = "terms" }
                )
            }
            
            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    glowColor = PremiumColors.CyberBlue
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("ℹ️", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "App Version",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "1.0",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PolicyCard(
    title: String,
    icon: String,
    description: String,
    onClick: () -> Unit
) {
    GlassmorphicCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        glowColor = PremiumColors.CyberBlue
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(icon, fontSize = 32.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View",
                tint = PremiumColors.ElectricPurple,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

fun LazyListScope.privacyPolicyContent() {
    item {
        GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "🔒 Privacy Policy",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Last Updated: October 18, 2025",
                    style = MaterialTheme.typography.labelMedium,
                    color = PremiumColors.CyberBlue
                )
            }
        }
    }
    
    item {
        PolicySection(
            title = "1. Information We Collect",
            content = "Socrates AI is designed with privacy in mind. We collect minimal information:\n\n• Favorites Data: Your saved favorite quotes are stored locally on your device\n• App Preferences: Settings like dark mode and notification preferences\n• Usage Analytics: Anonymous data about app crashes and performance to improve the app"
        )
    }
    
    item {
        PolicySection(
            title = "2. Information We Don't Collect",
            content = "We respect your privacy and do NOT collect:\n\n• Personal identification information (name, email, phone number)\n• Location data\n• Contacts or photos\n• Any data that can personally identify you"
        )
    }
    
    item {
        PolicySection(
            title = "3. Data Storage",
            content = "All your data (favorites, preferences) is stored locally on your device. We do not store your personal data on external servers. Your information stays with you."
        )
    }
    
    item {
        PolicySection(
            title = "4. Third-Party Services",
            content = "Socrates AI may use Google Play Services for app distribution and updates, and Android WorkManager for scheduling daily notifications. These services have their own privacy policies."
        )
    }
    
    item {
        PolicySection(
            title = "5. Children's Privacy",
            content = "Socrates AI is suitable for all ages. We do not knowingly collect personal information from children under 13. The app does not require any personal information to function."
        )
    }
    
    item {
        PolicySection(
            title = "6. Your Rights",
            content = "You have the right to:\n\n• Access your data (all stored locally on your device)\n• Delete your data (uninstall the app or clear app data)\n• Disable notifications at any time\n• Clear cache from the app settings"
        )
    }
    
    item {
        PolicySection(
            title = "7. Contact",
            content = "If you have any questions about this Privacy Policy, please contact us at: kreg9da@gmail.com"
        )
    }
}

fun LazyListScope.termsAndConditionsContent() {
    item {
        GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "📜 Terms & Conditions",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Last Updated: October 18, 2025",
                    style = MaterialTheme.typography.labelMedium,
                    color = PremiumColors.CyberBlue
                )
            }
        }
    }
    
    item {
        PolicySection(
            title = "1. Acceptance of Terms",
            content = "By downloading, installing, or using Socrates AI, you agree to be bound by these Terms and Conditions. If you do not agree to these terms, please do not use the app."
        )
    }
    
    item {
        PolicySection(
            title = "2. License to Use",
            content = "We grant you a limited, non-exclusive, non-transferable, revocable license to use Socrates AI for personal, non-commercial purposes. You may not:\n\n• Modify, reverse engineer, or decompile the app\n• Remove any copyright or proprietary notices\n• Use the app for any illegal purposes\n• Attempt to gain unauthorized access to the app's systems"
        )
    }
    
    item {
        PolicySection(
            title = "3. Content Ownership",
            content = "All quotes, texts, and content related to Socrates's works are in the public domain. The app's design, code, and original content are © 2025 Kreggscode. All rights reserved."
        )
    }
    
    item {
        PolicySection(
            title = "4. User Conduct",
            content = "You agree to use Socrates AI responsibly and in accordance with all applicable laws. You will not use the app to:\n\n• Harass, abuse, or harm others\n• Distribute malware or harmful code\n• Violate any intellectual property rights\n• Engage in any fraudulent activity"
        )
    }
    
    item {
        PolicySection(
            title = "5. Disclaimer of Warranties",
            content = "Socrates AI is provided \"as is\" without warranties of any kind, either express or implied. We do not guarantee that the app will be error-free, secure, or uninterrupted."
        )
    }
    
    item {
        PolicySection(
            title = "6. Limitation of Liability",
            content = "To the maximum extent permitted by law, Kreggscode shall not be liable for any indirect, incidental, special, consequential, or punitive damages arising from your use of Socrates AI."
        )
    }
    
    item {
        PolicySection(
            title = "7. Changes to Terms",
            content = "We reserve the right to modify these Terms and Conditions at any time. Continued use of the app after changes constitutes acceptance of the modified terms."
        )
    }
    
    item {
        PolicySection(
            title = "8. Termination",
            content = "We may terminate or suspend your access to Socrates AI at any time, without prior notice, for conduct that we believe violates these Terms or is harmful to other users."
        )
    }
    
    item {
        PolicySection(
            title = "9. Governing Law",
            content = "These Terms shall be governed by and construed in accordance with applicable laws, without regard to conflict of law provisions."
        )
    }
    
    item {
        PolicySection(
            title = "10. Contact Information",
            content = "For questions about these Terms and Conditions, please contact us at: kreg9da@gmail.com"
        )
    }
}

@Composable
fun PolicySection(title: String, content: String) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.CyberBlue.copy(alpha = 0.3f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PremiumColors.QuantumGold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 22.sp
            )
        }
    }
}
