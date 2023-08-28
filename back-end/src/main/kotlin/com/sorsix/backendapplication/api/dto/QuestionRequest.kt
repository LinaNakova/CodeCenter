package com.sorsix.backendapplication.api.dto

data class QuestionRequest(
    val title: String,
    val questionText: String,
    val parentQuestionId: Long?,
    val appUserId: Long,
    val tagsId: List<Long>?
)
