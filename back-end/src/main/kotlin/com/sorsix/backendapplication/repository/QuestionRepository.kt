package com.sorsix.backendapplication.repository

import com.sorsix.backendapplication.domain.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {
}