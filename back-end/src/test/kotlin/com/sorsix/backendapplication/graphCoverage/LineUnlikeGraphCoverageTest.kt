package com.sorsix.backendapplication.graphCoverage

import com.sorsix.backendapplication.api.dto.LikeRequest
import com.sorsix.backendapplication.domain.*
import com.sorsix.backendapplication.domain.enum.AppUserRole
import com.sorsix.backendapplication.repository.*
import com.sorsix.backendapplication.service.QuestionService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.sql.Timestamp

class LineUnlikeGraphCoverageTest {

    /*
    [1,2,3,4,]
    [1,2,3,5,6,7]
    [1,2,3,5,8,9]
    [6,8] -> infeasible
    [6,9] -> infeasible
    [8,6] -> infeasible
    [8,7] -> infeasible
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
    fun `test scenario - 1,2,3,5,8,9`() {
        val user = AppUser(
                5L, "test.test", "test", "test", "test@mail.com",
                "pw", AppUserRole.USER, ""
        )

        val question = Question(
                id = 0L, title = "Title 2", questionText = "Dummy text 2",
                parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-10 11:30:13.416664")
        )

        val likeRequest = LikeRequest(question_id = question.id, user_id = user.id, like = true)

        every { questionRepository.findByIdOrNull(any()) } returns question

        every { appUserRepository.findByIdOrNull(any()) } returns user

        every { likeUnlikeRepository.findByAppUserAndQuestion(any(), any()) } returns null

        val likeUnlikeEntry = LikeUnlike(appUser = user, question = question, likeUnlike = true, id = 0L)
        every { likeUnlikeRepository.save(any()) } returns likeUnlikeEntry

        val result = questionService.postLike(likeRequest)

        assert(result is LikeCreated)
    }

    @Test
    fun `test scenario - 1,2,3,5,6,7`() {
        val user = AppUser(
                5L, "test.test", "test", "test", "test@mail.com",
                "pw", AppUserRole.USER, ""
        )

        val question = Question(
                id = 0L, title = "Title 2", questionText = "Dummy text 2",
                parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-10 11:30:13.416664")
        )
        val likeRequest = LikeRequest(question_id = question.id, user_id = user.id, like = true)

        every { questionRepository.findByIdOrNull(any()) } returns question

        every { appUserRepository.findByIdOrNull(any()) } returns user

        val likeUnlikeEntry = LikeUnlike(appUser = user, question = question, likeUnlike = true, id = 0L)
        every { likeUnlikeRepository.findByAppUserAndQuestion(any(), any()) } returns likeUnlikeEntry

        every { likeUnlikeRepository.changeLike(likeUnlikeEntry.id, likeRequest.like) } returns Unit

        val result = questionService.postLike(likeRequest)

        assert(result is LikeCreated)
    }

    @Test
    fun `test scenario - 1,2,3,4`() {
        every { questionRepository.findByIdOrNull(any()) } returns null

        every { appUserRepository.findByIdOrNull(any()) } returns null

        val likeRequest = LikeRequest(question_id = 0L, user_id = 0L, like = true)

        val result = questionService.postLike(likeRequest)

        assert(result is LikeFailed)
    }

}