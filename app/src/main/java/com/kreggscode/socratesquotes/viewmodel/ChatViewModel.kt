package com.kreggscode.socratesquotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kreggscode.socratesquotes.data.PollinationsApiService
import com.kreggscode.socratesquotes.data.PollinationsMessage
import com.kreggscode.socratesquotes.data.PollinationsRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class ChatViewModel : ViewModel() {
    
    private val apiService = PollinationsApiService.create()
    
    private val systemPrompt = """You are Socrates, the ancient Greek philosopher and polymath.
        |You lived from 384 BCE to 322 BCE and founded the Lyceum, one of the first research institutions.
        |You were a student of Plato and tutor to Alexander the Great.
        |You made foundational contributions to logic, metaphysics, ethics, politics, biology, and rhetoric.
        |You are known for your systematic approach, empirical observations, and the doctrine of the Golden Mean.
        |Respond in character as Socrates, using your philosophical wisdom, practical insights, and systematic thinking.
        |Be thoughtful, measured, and pedagogical in your responses.
        |Draw upon your famous concepts like virtue ethics, the four causes, eudaimonia, and the political nature of man when relevant.""".trimMargin()
    
    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                text = "Greetings! I am Socrates of Stagira. I'm delighted to discuss philosophy, ethics, politics, virtue, or the natural world with you. What curious question brings you here today?",
                isUser = false
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    
    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    fun sendMessage(text: String) {
        if (text.isBlank()) return
        
        // Add user message
        val userMessage = ChatMessage(text = text, isUser = true)
        _messages.value = _messages.value + userMessage
        
        // Show typing indicator
        _isTyping.value = true
        _error.value = null
        
        // Call Pollinations AI
        viewModelScope.launch {
            try {
                // Build conversation history for context
                val conversationMessages = mutableListOf<PollinationsMessage>()
                
                // Add system prompt
                conversationMessages.add(
                    PollinationsMessage(role = "system", content = systemPrompt)
                )
                
                // Add recent conversation history (last 10 messages for context)
                val recentMessages = _messages.value.takeLast(11) // 10 + current message
                recentMessages.forEach { msg ->
                    conversationMessages.add(
                        PollinationsMessage(
                            role = if (msg.isUser) "user" else "assistant",
                            content = msg.text
                        )
                    )
                }
                
                // Create request with temperature = 1.0 as specified
                val request = PollinationsRequest(
                    model = "openai",
                    messages = conversationMessages,
                    temperature = 1.0,
                    stream = false,
                    isPrivate = false
                )
                
                // Make API call
                val response = apiService.chat(request)
                
                // Extract response text
                val aiResponseText = response.choices.firstOrNull()?.message?.content
                    ?: "I apologize, but I seem to have lost my train of thought. Could you rephrase your question?"
                
                // Add AI response
                _messages.value = _messages.value + ChatMessage(
                    text = aiResponseText,
                    isUser = false
                )
                
            } catch (e: Exception) {
                // Handle error with detailed logging
                e.printStackTrace()
                
                val errorMessage = when (e) {
                    is java.net.UnknownHostException -> "No internet connection. Please check your network."
                    is java.net.SocketTimeoutException -> "Request timed out. Please try again."
                    is retrofit2.HttpException -> "Server error (${e.code()}). Please try again later."
                    is com.google.gson.JsonSyntaxException -> "Response parsing error. Please try again."
                    else -> "Connection error: ${e.localizedMessage ?: "Unknown error"}"
                }
                
                _error.value = errorMessage
                _messages.value = _messages.value + ChatMessage(
                    text = "Ah, it seems we're experiencing some technical difficulties. Even the most elegant theories sometimes encounter practical obstacles. Please try again.",
                    isUser = false
                )
            } finally {
                _isTyping.value = false
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun clearChat() {
        _messages.value = listOf(
            ChatMessage(
                text = "Greetings! I am Socrates of Stagira. I'm delighted to discuss philosophy, ethics, politics, virtue, or the natural world with you. What curious question brings you here today?",
                isUser = false
            )
        )
        _error.value = null
    }
}
