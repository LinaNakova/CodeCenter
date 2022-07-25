package com.sorsix.backendapplication.repository

import com.sorsix.backendapplication.domain.ActivationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivationTokenRepository : JpaRepository<ActivationToken, String>
