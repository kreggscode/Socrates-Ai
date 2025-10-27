package com.kreggscode.socratesquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kreggscode.socratesquotes.data.PreferencesManager
import com.kreggscode.socratesquotes.data.QuoteDatabase
import com.kreggscode.socratesquotes.data.QuoteRepository
import com.kreggscode.socratesquotes.model.Category
import com.kreggscode.socratesquotes.model.Quote
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class QuoteViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = QuoteDatabase.getDatabase(application)
    private val repository = QuoteRepository(database.quoteDao(), application)
    private val preferencesManager = PreferencesManager(application)
    
    val allQuotes: StateFlow<List<Quote>> = repository.allQuotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val favoriteQuotes: StateFlow<List<Quote>> = repository.favoriteQuotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val categories: StateFlow<List<Category>> = allQuotes.map { quotes ->
        quotes.groupBy { it.Category }
            .map { (category, quoteList) ->
                Category(
                    name = category,
                    quoteCount = quoteList.size,
                    icon = getCategoryIcon(category)
                )
            }
            .sortedByDescending { it.quoteCount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val works: StateFlow<List<String>> = allQuotes.map { quotes ->
        quotes.map { it.Work }.distinct().sorted()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val isDarkMode: StateFlow<Boolean> = preferencesManager.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    
    val isDailyNotificationEnabled: StateFlow<Boolean> = preferencesManager.isDailyNotificationEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    
    private val _selectedQuote = MutableStateFlow<Quote?>(null)
    val selectedQuote: StateFlow<Quote?> = _selectedQuote.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadQuotes()
    }
    
    private fun loadQuotes() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.loadQuotesFromAssets()
            _isLoading.value = false
        }
    }
    
    fun getQuotesByCategory(category: String): Flow<List<Quote>> {
        return repository.getQuotesByCategory(category)
    }
    
    fun getQuotesByWork(work: String): Flow<List<Quote>> {
        return repository.getQuotesByWork(work)
    }
    
    fun selectQuote(quoteId: Int) {
        viewModelScope.launch {
            _selectedQuote.value = repository.getQuoteById(quoteId)
        }
    }
    
    fun toggleFavorite(quote: Quote) {
        viewModelScope.launch {
            repository.toggleFavorite(quote)
        }
    }
    
    fun toggleDarkMode() {
        viewModelScope.launch {
            preferencesManager.setDarkMode(!isDarkMode.value)
        }
    }
    
    fun toggleDailyNotification(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setDailyNotification(enabled)
        }
    }
    
    fun setSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }
    
    fun getRandomQuote(): Quote? {
        return allQuotes.value.randomOrNull()
    }
    
    private fun getCategoryIcon(category: String): String {
        return when (category.lowercase()) {
            "wisdom" -> "??"
            "freedom" -> "??"
            "justice" -> "??"
            "religion" -> "?"
            "philosophy" -> "??"
            "politics" -> "???"
            "science" -> "??"
            "education" -> "??"
            "humanity" -> "??"
            "tolerance" -> "??"
            "reason" -> "??"
            "truth" -> "?"
            "love" -> "??"
            "work" -> "??"
            "morality" -> "??"
            "society" -> "??"
            "government" -> "??"
            "history" -> "??"
            "death" -> "?"
            "women" -> "??"
            "men" -> "??"
            "success" -> "??"
            "nature" -> "??"
            "art" -> "??"
            "time" -> "?"
            "happiness" -> "??"
            "money" -> "??"
            "knowledge" -> "??"
            "friendship" -> "??"
            "courage" -> "??"
            "freedom of speech" -> "???"
            "war & peace" -> "??"
            else -> "??"
        }
    }
}
