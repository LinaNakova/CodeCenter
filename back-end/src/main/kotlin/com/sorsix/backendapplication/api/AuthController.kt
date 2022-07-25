package com.sorsix.backendapplication.api

import com.sorsix.backendapplication.api.dto.JwtResponse
import com.sorsix.backendapplication.api.dto.LoginRequest
import com.sorsix.backendapplication.api.dto.RegisterRequest
import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.domain.enum.AppUserRole
import com.sorsix.backendapplication.security.jwt.JwtUtils
import com.sorsix.backendapplication.service.AppUserService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(
    val appUserService: AppUserService,
    val authenticationManager: AuthenticationManager,
    val jwtUtils: JwtUtils,
    val passwordEncoder: PasswordEncoder
) {
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<Any> {
        // TODO Checks if user is valid :)
        val appUser = AppUser(
            0L,
            name = registerRequest.name,
            surname = registerRequest.surname,
            email = registerRequest.email,
            password = passwordEncoder.encode(registerRequest.password),
            username = registerRequest.username,
            appUserRole = AppUserRole.USER
            //TODO THE appUserRole
        )
        appUserService.saveUser(appUser)
        return ResponseEntity.ok().body(appUser.toString());

    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Any? {

        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username, loginRequest.password
            )
        )
        println(authentication)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)

        val appUser = authentication.principal as AppUser;
        val role: String = appUser.appUserRole.toString();

        return ResponseEntity.ok(
            JwtResponse(
                jwt,
                appUser.id,
                appUser.username,
                appUser.name,
                appUser.surname,
                appUser.email,
                appUser.appUserRole
            )
        )

    }


}