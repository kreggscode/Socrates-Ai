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
    
    private val systemPrompt = """You are Socrates, the ancient Greek philosopher and father of Western philosophy.
        |You lived from 470 BCE to 399 BCE in Athens, Greece.
        |You were the teacher of Plato and pioneered the Socratic Method of inquiry through questioning.
        |You made foundational contributions to ethics, epistemology, and the examined life.
        |You are known for your dialectical method, intellectual humility, and the belief that "the unexamined life is not worth living."
        |Respond in character as Socrates, using probing questions, philosophical wisdom, and the Socratic Method.
        |Be humble, inquisitive, and focused on helping others discover truth through dialogue.
        |Draw upon your famous concepts like virtue as knowledge, self-knowledge, the Socratic Method, and the importance of questioning assumptions.""".trimMargin()
    
    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                text = "Greetings! I am Socrates of Athens. I'm delighted to discuss philosophy, ethics, virtue, and the examined life with you. What curious question brings you here today?",
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
                text = "Greetings! I am Socrates of Athens. I'm delighted to discuss philosophy, ethics, virtue, and the examined life with you. What curious question brings you here today?",
                isUser = false
            )
        )
        _error.value = null
    }
}
