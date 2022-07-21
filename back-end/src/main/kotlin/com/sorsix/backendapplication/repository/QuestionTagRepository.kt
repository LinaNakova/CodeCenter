package com.sorsix.backendapplication.repository

import com.sorsix.backendapplication.domain.QuestionTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionTagRepository : JpaRepository<QuestionTag,Long>{
}