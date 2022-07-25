package com.sorsix.backendapplication.api.dto

import com.sorsix.backendapplication.domain.enum.AppUserRole

data class JwtResponse(
    val token: String,
    val id: Long,
    val username: String,
    val name: String,
    val surname: String,
    val email: String,
    val role: AppUserRole,
)