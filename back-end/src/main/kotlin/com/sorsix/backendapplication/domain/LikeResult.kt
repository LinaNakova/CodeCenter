package com.sorsix.backendapplication.domain

sealed interface LikeResult{
    fun success(): Boolean
}
data class LikeCreated(val like: LikeUnlike):LikeResult{
    override fun success(): Boolean {
        return true
    }
}
data class LikeFailed(val errorMessage: String):LikeResult{
    override fun success(): Boolean {
        return false
    }
}