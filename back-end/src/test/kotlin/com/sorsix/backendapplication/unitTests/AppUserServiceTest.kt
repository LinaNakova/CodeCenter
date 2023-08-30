package com.sorsix.backendapplication.unitTests

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.domain.enum.AppUserRole
import com.sorsix.backendapplication.repository.AppUserRepository
import com.sorsix.backendapplication.service.AppUserService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class AppUserServiceTest {
    private lateinit var appUserRepository: AppUserRepository
    private lateinit var appUserService: AppUserService

    @BeforeEach
    fun setup() {
        appUserRepository = mockk()
        appUserService = AppUserService(appUserRepository)
    }

    @Test
    fun `test findAll method`() {
        val users = listOf(
            AppUser(
                0L, "test.test", "test", "test", "test@mail.com",
                "pw", AppUserRole.USER, ""
            ),
            AppUser(
                1L, "test1.test", "test1", "test1", "test1@mail.com",
                "pw1", AppUserRole.USER, ""
            ),
            AppUser(
                2L, "test2.test", "test2", "test2", "test2@mail.com",
                "pw2", AppUserRole.USER, ""
            )
        )
        every { appUserRepository.findAll() } returns users
        assertEquals(appUserService.findAll(), users)
    }

    @Test
    fun loadUserByUsername() {
        val user = AppUser(
            0L, "test.test", "test", "test", "test@mail.com",
            "pw", AppUserRole.USER, ""
        )
        every { appUserRepository.findByUsername(any()) } returns user
        assertEquals(appUserService.loadUserByUsername("test.test"), user)
    }

    @Test
    fun findAppUserByIdOrNull() {
        val user = AppUser(
            0L, "test.test", "test", "test", "test@mail.com",
            "pw", AppUserRole.USER, ""
        )
        every { appUserRepository.findByIdOrNull(any()) } returns user
        assertEquals(appUserService.findAppUserByIdOrNull(0L), user)
    }
}