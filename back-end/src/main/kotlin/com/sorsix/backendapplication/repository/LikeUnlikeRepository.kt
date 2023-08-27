package com.sorsix.backendapplication.repository

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.domain.LikeUnlike
import com.sorsix.backendapplication.domain.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LikeUnlikeRepository : JpaRepository<LikeUnlike, Long> {

    fun findByAppUserAndQuestion(appUser: AppUser, question: Question): LikeUnlike?

    @Modifying
    @Query("update LikeUnlike l set l.likeUnlike = :like where l.id = :id")
    fun changeLike(id: Long, like: Boolean)
}