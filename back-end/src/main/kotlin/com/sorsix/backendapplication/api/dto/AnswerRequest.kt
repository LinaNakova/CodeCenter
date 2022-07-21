package com.sorsix.backendapplication.api.dto

data class AnswerRequest(
    val title: String,
    val questionText: String,
    val parentQuestionId: Long,
    val appUserId: Long,
)
