package com.sorsix.backendapplication.api

import com.sorsix.backendapplication.domain.Question
import com.sorsix.backendapplication.repository.AppUserRepository
import com.sorsix.backendapplication.repository.QuestionRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestApi(
    val questionRepository: QuestionRepository,
    val appUserRepository: AppUserRepository
) {

    @GetMapping
    fun getList(): MutableList<Question> = questionRepository.findAll();

}