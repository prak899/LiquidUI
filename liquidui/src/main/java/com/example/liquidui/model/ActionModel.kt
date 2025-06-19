package com.example.liquidui.model

data class ActionModel(
    val type: String,
    val url: String? = null,
    val screen: String? = null, // NEW: for intent navigation
    val extras: Map<String, String>? = null // optional extras
)