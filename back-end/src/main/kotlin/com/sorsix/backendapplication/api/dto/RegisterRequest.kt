package com.sorsix.backendapplication.api.dto

data class RegisterRequest(
    val username: String,
    val name: String,
    val surname: String,
    val role: String?,
    val password: String,
    val email: String,
)
