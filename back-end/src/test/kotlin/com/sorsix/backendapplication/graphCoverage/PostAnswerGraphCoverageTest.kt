package com.sorsix.backendapplication.graphCoverage

import com.sorsix.backendapplication.domain.AppUser
import com.sorsix.backendapplication.domain.Question
import com.sorsix.backendapplication.domain.QuestionCreated
import com.sorsix.backendapplication.domain.QuestionFailed
import com.sorsix.backendapplication.domain.enum.AppUserRole
import com.sorsix.backendapplication.repository.*
import com.sorsix.backendapplication.service.QuestionService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.sql.Timestamp

class PostAnswerGraphCoverageTest {
    /*
    [1,2,4,5]
    [1,2,3]
    */

    private lateinit var questionRepository: QuestionRepository
    private lateinit var tagRepository: TagRepository
    private lateinit var questionTagRepository: QuestionTagRepository
    private lateinit var appUserRepository: AppUserRepository
    private lateinit var likeUnlikeRepository: LikeUnlikeRepository
    lateinit var questionService: QuestionService

    @BeforeEach
    fun setUp() {
        questionRepository = mockk()
        questionTagRepository = mockk()
        tagRepository = mockk()
        appUserRepository = mockk()
        likeUnlikeRepository = mockk()
        questionService = QuestionService(
            questionRepository,
            tagRepository,
            questionTagRepository,
            appUserRepository,
            likeUnlikeRepository
        )
    }

    @Test
    fun `test scenario - 1,2,4,5`() {
        val user = AppUser(
            5L, "test.test", "test", "test", "test@mail.com",
            "pw", AppUserRole.USER, ""
        )

        val parentQuestion = Question(
            id = 1L, title = "Title 1", questionText = "Dummy text 1",
            parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-10 11:30:13.416664")
        )

        val answer = Question(
            id = 3L, title = "Answer 1", questionText = "Text 1", parentQuestion = parentQuestion,
            user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")
        )

        every { appUserRepository.findByIdOrNull(any()) } returns user
        every { questionRepository.findByIdOrNull(any()) } returns parentQuestion
        every { questionRepository.save(any()) } returns answer

        val result = questionService.postAnswer(
            answer.title, answer.questionText,
            answer.parentQuestion!!.id, answer.user!!.id
        )

        assert(result is QuestionCreated)
    }

    @Test
    fun `test scenario - 1,2,3`() {
        val title = "Title"
        val questionText = "Dummy Text"
        val parentQuestionId = 1L
        val appUserId = 2L

        every { appUserRepository.findByIdOrNull(any()) } returns null

        every { questionRepository.findByIdOrNull(any()) } returns null

        val result = questionService.postAnswer(title, questionText, parentQuestionId, appUserId)

        assert(result is QuestionFailed)
    }
}