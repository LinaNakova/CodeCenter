package com.sorsix.backendapplication.repository

import com.sorsix.backendapplication.domain.Question
import net.bytebuddy.TypeCache
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {

    @Modifying
    @Query("update Question q set q.title = :name where q.id = :id")
    fun changeName(name: String, id: Long)

    @Modifying
    @Query("update Question q set q.views = q.views+1 where q.id = :id")
    fun increaseViews(id: Long)

//    @Modifying
//    @Query("update Question q set q.is_answered = true where q.id = :id")
//    fun closeQuestion(id: Long)

    fun findAllByParentQuestion(parentQuestion: Question?, pageable: Pageable): Page<Question>?
}