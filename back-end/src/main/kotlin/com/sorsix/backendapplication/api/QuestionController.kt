package com.sorsix.backendapplication.api

import com.sorsix.backendapplication.api.dto.AnswerRequest
import com.sorsix.backendapplication.api.dto.LikeRequest
import com.sorsix.backendapplication.api.dto.QuestionRequest
import com.sorsix.backendapplication.domain.*
import com.sorsix.backendapplication.service.QuestionService
import com.sorsix.backendapplication.service.TagService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin("*")
@RequestMapping("/api/questions")
class QuestionController(
    val questionService: QuestionService,
    val tagService: TagService,
) {

    @GetMapping
    fun getAll(): List<Question>? {
        return questionService.findAll()
    }

    @GetMapping("/withoutAnswers")
    fun getAllQuestionsWithoutAnswers(): List<Question>? {
        return questionService.findAllQuestionsWithoutAnswers()
    }

    @PostMapping
    fun createQuestion(@RequestBody request: QuestionRequest): ResponseEntity<String> {
        val result = questionService.createQuestion(
            request.title,
            request.questionText,
            request.parentQuestionId,
            request.appUserId,
            request.tagsId
        )

        val resultString = when (result) {
            is QuestionCreated -> {
                result.question.toString()
            }
            is QuestionFailed -> {
                "Failed because " + result.errorMessage
            }
        }
        return if (result.success()) {
            ResponseEntity.ok(resultString)
        } else {
            ResponseEntity.badRequest().body(resultString)
        }
    }

    @PostMapping("/postAnswer/{id}")
    fun postAnswerToQuestion(@PathVariable id: Long, @RequestBody request: AnswerRequest): ResponseEntity<Any> {
        val result = questionService.postAnswer(
            request.title,
            request.questionText,
            request.parentQuestionId,
            request.appUserId
        )
        val resultString = when (result) {
            is QuestionCreated -> {
                result.question
            }
            is QuestionFailed -> {
                "Failed because " + result.errorMessage
            }
        }
        return if (result.success()) {
            ResponseEntity.ok(resultString)
        } else {
            ResponseEntity.badRequest().body(resultString)
        }
    }

    @GetMapping("/{id}")
    fun getQuestionById(@PathVariable id: Long): Question? {
        return questionService.findById(id)
    }

    @GetMapping("/answers/{id}")
    fun getAnswersForQuestion(@PathVariable id: Long): List<Question>? {
        return questionService.getAnswersForQuestion(id)
    }

    @GetMapping("/tagged/{word}")
    fun getAllQuestionsWithMentionedWord(@PathVariable("word") word: String): List<Question>? {
        return questionService.findAllQuestionsWithMentionedWord(word)
    }

    @GetMapping("/tags/{id}")
    fun getQuestionTags(@PathVariable id: Long): List<Tag> {
        return questionService.getQuestionTags(id).map { it.tag }
    }

    @GetMapping("/sortedByTitle")
    fun getSortedByTitle(): List<Question> {
        return this.questionService.getSortedByTitle()
    }

    @GetMapping("/sortedByTitleDescending")
    fun getSortedByTitleDescending(): List<Question> {
        return this.questionService.getSortedByTitleDescending()
    }

    @GetMapping("/sortedByViewsAscending")
    fun getSortedByViewsAscending(): List<Question> {
        return this.questionService.getSortedByViewsAscending()
    }

    @GetMapping("/sortedByViewsDescending")
    fun getSortedByViewsDescending(): List<Question> {
        return this.questionService.getSortedByViewsDescending()
    }

    @GetMapping("/likes/{id}")
    fun getLikes(@PathVariable id: Long): Int {
        return this.questionService.getLikes(id)
    }

    @PostMapping("/likes")
    fun like(@RequestBody body: LikeRequest): ResponseEntity<Any> {
        val result = this.questionService.postLike(body)
        val resultString = when (result) {
            is LikeCreated -> {
                result.like
            }
            is LikeFailed -> {
                "Failed because " + result.errorMessage
            }
        }
        return if (result.success()) {
            ResponseEntity.ok(resultString)
        } else {
            ResponseEntity.badRequest().body(resultString)
        }
    }

    @PostMapping("/increase/{id}")
    fun increaseViews(@PathVariable id: Long) {
        this.questionService.increaseViews(id)
    }

    @GetMapping("/views/{id}")
    fun getViews(@PathVariable id: Long): Int {
        return this.questionService.getViews(id)
    }

    @GetMapping("/fromUser/{id}")
    fun getQuestionsFromUser(@PathVariable id: Long): List<Question>? {
        return this.questionService.getQuestionsFromUser(id)
    }

    @GetMapping("/answersFromUser/{id}")
    fun getAnswersFromUser(@PathVariable id: Long): List<Question>? {
        return this.questionService.getAnswersFromUser(id)
    }

    @GetMapping("/sortedByLikesAscending/{id}")
    fun getSortByLikes(@PathVariable id: Long): List<Question>? {
        return this.questionService.sortByLikes(id)
    }

    @GetMapping("/sortedByLikesDescending/{id}")
    fun getSortByLikesDescending(@PathVariable id: Long): List<Question>? {
        return this.questionService.sortByLikesDescending(id)
    }

    @GetMapping("/sortedByAnswersAscending")
    fun getSortedByAnswersAscending(): List<Question>? {
        return this.questionService.sortByAnswersAscending()
    }
    @GetMapping("/sortedByAnswersDescending")
    fun getSortedByAnswersDescending(): List<Question>? {
        return this.questionService.sortByAnswersDescending()
    }
    @GetMapping("/sortedByTimestamp")
    fun sortByDateAscending() : List<Question>?
    {
        return this.questionService.sortByDateAscending()
    }
    @GetMapping("/sortedByTimestampDescending")
    fun sortByDateDescending() : List<Question>?
    {
        return this.questionService.sortByDateDescending()
    }

    @GetMapping("/checkIfLikedByUser/{qid}/{uid}")
    fun getCheckIfLikedByUser(@PathVariable qid : Long, @PathVariable uid: Long) : Boolean?
    {
        return this.questionService.checkIfLikedByUser(qid, uid)
    }

}