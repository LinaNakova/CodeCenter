package com.sorsix.backendapplication.api

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.service.AppUserService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/users")
class AppUserController(
    val userService: AppUserService,
) {
    @GetMapping()
    fun getAllUsers(): List<AppUser>? {
        return this.userService.findAll()
    }
}