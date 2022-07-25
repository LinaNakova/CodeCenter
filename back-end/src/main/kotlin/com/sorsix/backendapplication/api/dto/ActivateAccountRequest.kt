package com.sorsix.backendapplication.api.dto

data class ActivateAccountRequest(
    val token: String,
    val firstName: String,
    val lastName: String,
    val username: String?,
    val password: String,
    val email: String?,
)