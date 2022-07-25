package com.sorsix.backendapplication.repository

import com.sorsix.backendapplication.domain.Question
import net.bytebuddy.TypeCache
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {
}