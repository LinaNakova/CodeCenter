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
sealed interface TagResult{
    fun success(): Boolean
}
data class TagCreated(val tag: Tag):TagResult{
    override fun success(): Boolean {
        return true
    }
}
data class TagFailed(val errorMessage: String):TagResult{
    override fun success(): Boolean {
        return false
    }

}