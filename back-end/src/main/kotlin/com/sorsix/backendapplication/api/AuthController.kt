package com.sorsix.backendapplication.api

import com.sorsix.backendapplication.security.jwt.JwtUtils
import com.sorsix.backendapplication.service.AppUserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val appUserService: AppUserService,
    val authenticationManager: AuthenticationManager,
    val jwtUtils: JwtUtils,
) {

    @PostMapping("/login")
    fun login(){

    }

}