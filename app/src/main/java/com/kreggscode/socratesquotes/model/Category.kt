package com.kreggscode.socratesquotes.model

data class Category(
    val name: String,
    val quoteCount: Int,
    val icon: String = "??"
)
