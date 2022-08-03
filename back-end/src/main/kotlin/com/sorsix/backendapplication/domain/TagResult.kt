package com.sorsix.backendapplication.domain


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