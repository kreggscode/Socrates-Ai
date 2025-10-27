# 🏛️ Socrates AI - Insights & Wisdom

<div align="center">

![Socrates AI Banner](https://img.shields.io/badge/Socrates-Insights%20%26%20Wisdom-5D4E37?style=for-the-badge&logo=android&logoColor=white)

**Experience the wisdom of ancient philosophy in a modern, AI-powered Android app**

[![Get it on Google Play](https://img.shields.io/badge/Get%20it%20on-Google%20Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/details?id=com.kreggscode.socratesquotes)
[![GitHub](https://img.shields.io/badge/GitHub-Socrates--AI-181717?style=for-the-badge&logo=github)](https://github.com/kreggscode/Socrates-Ai)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](LICENSE)

[Features](#-features) • [Screenshots](#-screenshots) • [Tech Stack](#-tech-stack) • [Installation](#-installation) • [Documentation](#-documentation)

</div>

---

## 📖 About

**Socrates AI** brings the timeless wisdom of Socrates, the father of Western philosophy, to your fingertips. Explore profound quotes, engage in philosophical discussions with AI, and embark on a journey of self-examination and wisdom.

> *"The unexamined life is not worth living."* - Socrates

### Why Socrates AI?

- 🎓 **Educational Excellence**: Learn from one of history's greatest philosophers
- 🤖 **AI-Powered Insights**: Chat with an AI trained in Socratic philosophy
- 🎨 **Premium Design**: Beautiful, modern UI with glassmorphic effects
- 📚 **Comprehensive Content**: Thousands of quotes, dialogues, and philosophical works
- 🌙 **Dark Mode**: Elegant dark theme for comfortable reading
- 📱 **Offline Access**: Access wisdom anytime, anywhere

---

## ✨ Features

### 🏠 **Home & Discovery**
- **Daily Wisdom**: Curated quotes to inspire your day
- **Category Explorer**: Browse quotes by themes (Wisdom, Justice, Truth, Courage, etc.)
- **Time-Based Greetings**: Personalized philosophical greetings
- **Quick Actions**: Fast access to key features

### 💬 **AI Chat Assistant**
- **Socratic Dialogue**: Engage in philosophical discussions
- **Intelligent Responses**: AI trained in Socratic method
- **Question Everything**: Learn through inquiry and dialogue
- **Context-Aware**: Remembers conversation history

### 📚 **Extensive Library**
- **10,000+ Quotes**: Organized by category and theme
- **Major Works**: Access to Socratic dialogues (via Plato)
- **Biography**: Comprehensive life story and timeline
- **Letters & Papers**: Historical documents and writings

### 🎯 **Interactive Features**
- **Daily Affirmations**: Philosophical affirmations for personal growth
- **Favorites**: Save and organize your favorite quotes
- **Search**: Powerful search across all content
- **Share**: Share wisdom with friends and social media

### 🎨 **Premium UI/UX**
- **Glassmorphic Design**: Modern frosted glass effects
- **Smooth Animations**: Spring physics and fluid transitions
- **Gradient Themes**: Beautiful Socratic color palette
- **Edge-to-Edge**: Immersive full-screen experience
- **Responsive**: Optimized for all screen sizes

### 🔧 **Customization**
- **Dark/Light Mode**: Choose your preferred theme
- **Text-to-Speech**: Listen to quotes and dialogues
- **Font Sizing**: Adjust text for comfortable reading
- **Notification Settings**: Daily wisdom reminders

---

## 📱 Screenshots

<div align="center">

| Home Screen | AI Chat | Quote Library | About Socrates |
|------------|---------|---------------|----------------|
| ![Home](docs/screenshots/home.png) | ![Chat](docs/screenshots/chat.png) | ![Library](docs/screenshots/library.png) | ![About](docs/screenshots/about.png) |

</div>

---

## 🛠️ Tech Stack

### **Core Technologies**
- **Language**: Kotlin 100%
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Minimum SDK**: Android 8.0 (API 26)
- **Target SDK**: Android 14 (API 34)

### **Jetpack Components**
```kotlin
- Compose UI (Material 3)
- Navigation Component
- Room Database
- ViewModel & LiveData
- Lifecycle Components
- DataStore (Preferences)
```

### **AI & Networking**
```kotlin
- Gemini AI API (Google)
- Ktor Client (HTTP)
- Kotlinx Serialization
- Coroutines & Flow
```

### **UI Libraries**
```kotlin
- Material 3 Design
- Accompanist (System UI Controller)
- Coil (Image Loading)
- Lottie (Animations)
```

### **Build Tools**
```kotlin
- Gradle 8.7
- Kotlin 1.9.20
- KSP (Kotlin Symbol Processing)
- ProGuard (Code Obfuscation)
```

### **Architecture Pattern**
```
app/
├── data/
│   ├── local/          # Room Database
│   ├── remote/         # API Services
│   └── repository/     # Data Layer
├── domain/
│   ├── model/          # Domain Models
│   └── usecase/        # Business Logic
├── ui/
│   ├── screens/        # Compose Screens
│   ├── components/     # Reusable Components
│   └── theme/          # Theme & Colors
└── viewmodel/          # ViewModels
```

---

## 🚀 Installation

### **From Google Play Store** (Recommended)

<a href="https://play.google.com/store/apps/details?id=com.kreggscode.socratesquotes">
  <img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" alt="Get it on Google Play" height="80">
</a>

### **Build from Source**

#### Prerequisites
- Android Studio Hedgehog or later
- JDK 17 or later
- Android SDK 34
- Git

#### Steps

1. **Clone the repository**
```bash
git clone https://github.com/kreggscode/Socrates-Ai.git
cd Socrates-Ai
```

2. **Open in Android Studio**
```bash
# Open Android Studio and select "Open an Existing Project"
# Navigate to the cloned directory
```

3. **Add API Keys** (Optional - for AI features)
```kotlin
// In local.properties
GEMINI_API_KEY=your_api_key_here
```

4. **Build the project**
```bash
./gradlew assembleDebug
```

5. **Run on device/emulator**
```bash
./gradlew installDebug
```

---

## 📚 Documentation

Comprehensive documentation is available in the `docs/` folder:

- **[Privacy Policy](docs/privacy.html)** - How we handle your data
- **[Terms & Conditions](docs/terms.html)** - Terms of use
- **[Landing Page](docs/index.html)** - Web landing page

---

## 🎨 Design Philosophy

### **Socratic Color Palette**

The app uses an earthy, philosophical color scheme inspired by ancient Athens:

**Light Mode**
- Primary: `#5D4E37` (Philosophical Brown)
- Secondary: `#2C5F2D` (Olive Green)
- Tertiary: `#8B7355` (Terracotta)
- Background: `#F5F1E8` (Parchment Beige)

**Dark Mode**
- Primary: `#B8956A` (Golden Wisdom)
- Secondary: `#6B8E23` (Olive Branch)
- Tertiary: `#CD853F` (Warm Clay)
- Background: `#1A1410` (Ancient Stone)

### **Design Principles**
1. **Clarity**: Clean, readable typography
2. **Depth**: Glassmorphic layers and shadows
3. **Motion**: Smooth, meaningful animations
4. **Consistency**: Unified design language
5. **Accessibility**: High contrast, scalable text

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Developer

**Kregg's Code**

- 📧 Email: [kreg9da@gmail.com](mailto:kreg9da@gmail.com)
- 🌐 GitHub: [@kreggscode](https://github.com/kreggscode)
- 📱 More Apps: [Kregg on Google Play](https://play.google.com/store/apps/developer?id=Kregg)

---

## 🙏 Acknowledgments

- **Socrates** (470-399 BC) - For the timeless wisdom
- **Plato** - For preserving Socratic dialogues
- **Google Gemini AI** - For powering the AI chat
- **Material Design** - For design guidelines
- **Jetpack Compose** - For modern UI framework

---

## 📊 Project Stats

![GitHub repo size](https://img.shields.io/github/repo-size/kreggscode/Socrates-Ai?style=flat-square)
![GitHub code size](https://img.shields.io/github/languages/code-size/kreggscode/Socrates-Ai?style=flat-square)
![GitHub language count](https://img.shields.io/github/languages/count/kreggscode/Socrates-Ai?style=flat-square)
![GitHub top language](https://img.shields.io/github/languages/top/kreggscode/Socrates-Ai?style=flat-square&color=orange)

---

<div align="center">

### ⭐ Star this repository if you find it helpful!

**Made with ❤️ and ☕ by Kregg's Code**

[⬆ Back to Top](#-socrates-ai---insights--wisdom)

</div>
