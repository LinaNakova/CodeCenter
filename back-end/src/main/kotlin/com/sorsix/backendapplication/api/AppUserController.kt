package com.sorsix.backendapplication.api

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.service.AppUserService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/users")
class AppUserController(
    val userService : AppUserService
) {
    @GetMapping()
    fun getAllUsers() : List<AppUser>?
    {
        return this.userService.findAll()
    }
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id : Long) : AppUser?
    {
        return this.userService.findAppUserByIdOrNull(id);
    }
}