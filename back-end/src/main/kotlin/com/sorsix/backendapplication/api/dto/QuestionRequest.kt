package com.sorsix.backendapplication.api.dto

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.domain.Question
import com.sorsix.backendapplication.domain.Tag

data class QuestionRequest(
    val title: String,
    val questionText: String,
    val parentQuestionId: Long?,
    val appUserId: Long,
    val tagsId: List<Long>?
)
