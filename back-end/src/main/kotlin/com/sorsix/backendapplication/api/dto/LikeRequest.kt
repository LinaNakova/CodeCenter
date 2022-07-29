package com.sorsix.backendapplication.api.dto

data class LikeRequest(
    val question_id : Long,
    val user_id : Long,
    val like : Boolean
){
}