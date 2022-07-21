package com.sorsix.backendapplication.domain

sealed interface QuestionResult{
    fun success(): Boolean
}

data class QuestionCreated(val question: Question):QuestionResult{
    override fun success(): Boolean {
        return true
    }
}

data class QuestionFailed(val errorMessage: String):QuestionResult{
    override fun success(): Boolean {
        return false
    }

}