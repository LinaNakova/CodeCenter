package com.sorsix.backendapplication.repository

import com.sorsix.backendapplication.domain.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : JpaRepository<AppUser,Long> {
}