package com.kreggscode.socratesquotes.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.kreggscode.socratesquotes.ui.components.*
import com.kreggscode.socratesquotes.ui.theme.*
import com.kreggscode.socratesquotes.viewmodel.QuoteViewModel
import kotlinx.coroutines.launch

data class SettingItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String? = null,
    val action: SettingAction
)

sealed class SettingAction {
    object Toggle : SettingAction()
    object Navigate : SettingAction()
    object External : SettingAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumSettingsScreen(
    viewModel: QuoteViewModel,
    onAboutClick: () -> Unit
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isDailyNotificationEnabled by viewModel.isDailyNotificationEnabled.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
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
        // Animated gradient orbs
        AnimatedOrbs(modifier = Modifier.fillMaxSize())
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                SettingsHeader()
            }
            
            // Theme Section
            item {
                ThemeSection(
                    isDarkMode = isDarkMode,
                    onToggle = { viewModel.toggleDarkMode() }
                )
            }
            
            // Notifications Section
            item {
                NotificationSection(
                    isEnabled = isDailyNotificationEnabled,
                    onToggle = { viewModel.toggleDailyNotification(!isDailyNotificationEnabled) }
                )
            }
            
            // About Section
            item {
                AboutSection(onAboutClick = onAboutClick)
            }
            
            // Share App Section
            item {
                ShareAppSection(
                    onShareClick = {
                        try {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Socrates - Insights & Wisdom")
                                putExtra(Intent.EXTRA_TEXT, "Check out Socrates - Insights & Wisdom app! Get inspired by Socrates's wisdom daily.\n\nhttps://play.google.com/store/apps/details?id=com.kreggscode.socratesquotes")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                )
            }
            
            // Rate App Section
            item {
                RateAppSection(
                    onRateClick = {
                        try {
                            // Try to open in Play Store app first
                            val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.kreggscode.socratesquotes"))
                            playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(playStoreIntent)
                        } catch (e: Exception) {
                            // If Play Store app is not available, open in browser
                            try {
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.kreggscode.socratesquotes"))
                                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(browserIntent)
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }
                    }
                )
            }
            
            // More Apps Section
            item {
                MoreAppsSection(
                    onKreggClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Kregg"))
                        context.startActivity(intent)
                    }
                )
            }
            
            // Data & Privacy Section
            item {
                DataPrivacySection(context = context)
            }
            
            // Help & Support Section
            item {
                HelpSupportSection(context = context)
            }
            
            // Premium Features
            item {
                PremiumSection()
            }
            
            // App Info
            item {
                AppInfoSection()
            }
            
            // Social Links
            item {
                SocialLinksSection(context = context)
            }
        }
    }
}

@Composable
fun SettingsHeader() {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.CosmicIndigo,
        animateGlow = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Settings icon with glow
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                PremiumColors.CosmicIndigo.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Text(
                "Customize your experience",
                style = MaterialTheme.typography.bodyMedium,
                color = PremiumColors.CosmicIndigo
            )
        }
    }
}

@Composable
fun ThemeSection(
    isDarkMode: Boolean,
    onToggle: () -> Unit
) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.ElectricPurple
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "🎨 Appearance",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PremiumColors.ElectricPurple
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Dark Mode Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.05f))
                    .clickable { onToggle() }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (isDarkMode) PremiumColors.MidnightBlue 
                                else Color(0xFFFFD700)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            if (isDarkMode) "Dark Mode" else "Light Mode",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                        Text(
                            if (isDarkMode) "Easy on the eyes" else "Bright and clear",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
                
                // Animated Toggle Switch
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PremiumColors.ElectricPurple,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.White.copy(alpha = 0.3f)
                    )
                )
            }
        }
    }
}

@Composable
fun NotificationSection(
    isEnabled: Boolean,
    onToggle: () -> Unit
) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.CyberBlue
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "🔔 Notifications",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PremiumColors.CyberBlue
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Daily Quote Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.05f))
                    .clickable { onToggle() }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(PremiumColors.CyberBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.NotificationAdd,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            "Daily Quote",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                        Text(
                            "Receive a quote every day",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
                
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PremiumColors.CyberBlue,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.White.copy(alpha = 0.3f)
                    )
                )
            }
        }
    }
}

@Composable
fun AboutSection(onAboutClick: () -> Unit) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.NeonPink,
        onClick = onAboutClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(PremiumColors.NeonPink),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        "About Socrates",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Text(
                        "Learn about the genius",
                        style = MaterialTheme.typography.bodySmall,
                        color = PremiumColors.NeonPink
                    )
                }
            }
            
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun ShareAppSection(onShareClick: () -> Unit) {
    GlassmorphicCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onShareClick),
        glowColor = PremiumColors.CyberBlue
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(PremiumColors.CyberBlue.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        "Share App",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Text(
                        "Share Socrates wisdom with friends",
                        style = MaterialTheme.typography.bodySmall,
                        color = PremiumColors.CyberBlue
                    )
                }
            }
            
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun RateAppSection(onRateClick: () -> Unit) {
    GlassmorphicCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onRateClick),
        glowColor = Color(0xFFFFD700)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFD700).copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        "Rate App",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Text(
                        "Love the app? Rate us on Play Store",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFFD700)
                    )
                }
            }
            
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun MoreAppsSection(onKreggClick: () -> Unit) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.QuantumTeal,
        onClick = onKreggClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(PremiumColors.QuantumTeal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Apps,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        "More Apps",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Text(
                        "Discover other amazing apps",
                        style = MaterialTheme.typography.bodySmall,
                        color = PremiumColors.QuantumTeal
                    )
                }
            }
            
            Icon(
                Icons.Default.OpenInNew,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun PremiumSection() {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = Color(0xFFFFD700),
        animateGlow = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "👑",
                fontSize = 48.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "Socrates Premium",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )
            
            Text(
                "Unlock all features",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Upgrade Now",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AppInfoSection() {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.CosmicIndigo
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Socrates Insights",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Text(
                "Version 1.0.0",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "Made with ❤️ by Kreggscode",
                style = MaterialTheme.typography.bodySmall,
                color = PremiumColors.NeonPink
            )
        }
    }
}

@Composable
fun SocialLinksSection(context: android.content.Context) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.NeonPink
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "🚀 Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PremiumColors.NeonPink,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SocialButton(
                    icon = "📧",
                    label = "Email",
                    onClick = {
                        try {
                            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:kreg9da@gmail.com")
                                putExtra(Intent.EXTRA_SUBJECT, "Socrates App Feedback")
                            }
                            context.startActivity(emailIntent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                
                SocialButton(
                    icon = "⭐",
                    label = "Rate",
                    onClick = {
                        try {
                            // Try to open in Play Store app first
                            val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.kreggscode.socratesquotes"))
                            playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(playStoreIntent)
                        } catch (e: Exception) {
                            // If Play Store app is not available, open in browser
                            try {
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.kreggscode.socratesquotes"))
                                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(browserIntent)
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                
                SocialButton(
                    icon = "🔗",
                    label = "Share",
                    onClick = {
                        try {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Socrates - Insights & Wisdom")
                                putExtra(Intent.EXTRA_TEXT, "Check out Socrates - Insights & Wisdom app! Get inspired by Socrates's wisdom daily.\n\nhttps://play.google.com/store/apps/details?id=com.kreggscode.socratesquotes")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun SocialButton(
    icon: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .clickable(onClick = onClick)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(icon, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun DataPrivacySection(context: android.content.Context) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.ElectricPurple
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "🔒 Data & Privacy",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PremiumColors.ElectricPurple
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Clear Cache
            SettingRow(
                icon = Icons.Default.CleaningServices,
                title = "Clear Cache",
                subtitle = "Free up storage space",
                onClick = {
                    try {
                        context.cacheDir.deleteRecursively()
                        android.widget.Toast.makeText(context, "Cache cleared successfully", android.widget.Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        android.widget.Toast.makeText(context, "Failed to clear cache", android.widget.Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Composable
fun HelpSupportSection(context: android.content.Context) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        glowColor = PremiumColors.QuantumTeal
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "💬 Help & Support",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PremiumColors.QuantumTeal
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Report a Bug
            SettingRow(
                icon = Icons.Default.BugReport,
                title = "Report a Bug",
                subtitle = "Help us improve the app",
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:kreg9da@gmail.com")
                        putExtra(Intent.EXTRA_SUBJECT, "Socrates App - Bug Report")
                        putExtra(Intent.EXTRA_TEXT, "Please describe the bug:\n\n")
                    }
                    context.startActivity(emailIntent)
                }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Contact Us
            SettingRow(
                icon = Icons.Default.ContactSupport,
                title = "Contact Us",
                subtitle = "Get in touch with support",
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:kreg9da@gmail.com")
                        putExtra(Intent.EXTRA_SUBJECT, "Socrates App - Support Request")
                    }
                    context.startActivity(emailIntent)
                }
            )
        }
    }
}

@Composable
fun SettingRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
        
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.4f),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun AnimatedOrbs(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "orbs")
    
    val orb1Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1"
    )
    
    val orb2Offset by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2"
    )
    
    Canvas(modifier = modifier) {
        // Orb 1
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    PremiumColors.ElectricPurple.copy(alpha = 0.2f),
                    Color.Transparent
                )
            ),
            radius = 200.dp.toPx(),
            center = Offset(
                x = size.width * orb1Offset,
                y = size.height * 0.3f
            )
        )
        
        // Orb 2
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    PremiumColors.CyberBlue.copy(alpha = 0.2f),
                    Color.Transparent
                )
            ),
            radius = 150.dp.toPx(),
            center = Offset(
                x = size.width * orb2Offset,
                y = size.height * 0.7f
            )
        )
    }
}
