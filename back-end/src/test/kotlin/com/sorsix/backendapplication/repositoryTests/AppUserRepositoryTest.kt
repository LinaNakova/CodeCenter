package com.sorsix.backendapplication.repositoryTests

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.domain.enum.AppUserRole
import com.sorsix.backendapplication.repository.AppUserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.junit.jupiter.api.Assertions.assertEquals

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = ["com.sorsix.backendapplication"])
@EntityScan(basePackages = ["com.sorsix.backendapplication"])
class AppUserRepositoryTest {

     @Autowired
    private lateinit var userRepository: AppUserRepository

    var user : AppUser? = null

    @BeforeEach
    fun addTestUser(){
        val userAppRole = AppUserRole.USER
        user = AppUser(
            id = 1L,
            username = "test.kotlin",
            name = "test",
            surname = "kotlin",
            email = "test.kotlin@mail.com",
            password = "password",
            appUserRole = userAppRole,
            link_img = "linkToProfilePicHere"
        )
        userRepository.saveAndFlush(user!!)
    }

    @Test
    fun findByUsernameTest() {
        assertEquals(userRepository.findByUsername("test.kotlin"), user)
    }
}