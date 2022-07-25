package com.sorsix.backendapplication.repository

import com.sorsix.backendapplication.domain.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {

    @Modifying
    @Query("update Question q set q.title = :name where q.id = :id")
    fun changeName(name: String, id: Long)
}