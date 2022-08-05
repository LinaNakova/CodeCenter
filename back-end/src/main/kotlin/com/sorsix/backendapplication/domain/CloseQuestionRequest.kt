package com.sorsix.backendapplication.domain

data class CloseQuestionRequest(
    val questionId: Long,
    val userId: Long,
)