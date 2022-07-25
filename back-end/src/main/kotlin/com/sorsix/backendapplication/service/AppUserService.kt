package com.sorsix.backendapplication.service

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.repository.AppUserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AppUserService(
    val appUserRepository: AppUserRepository,
    val activationTokenService: ActivationTokenService,
) : UserDetailsService {

    fun saveUser(appUser: AppUser) {
        appUserRepository.save(appUser);

    }

    override fun loadUserByUsername(username: String): UserDetails? {
        return appUserRepository.findByUsername(username);

    }

    fun findAppUserByIdOrNull(appUserId: Long): AppUser? = appUserRepository.findByIdOrNull(appUserId)


}