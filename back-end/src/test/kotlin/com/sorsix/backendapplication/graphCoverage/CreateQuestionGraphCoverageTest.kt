package com.sorsix.backendapplication.graphCoverage

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

class CreateQuestionGraphCoverageTest {

    /*
    [1,2,3,5,6,7,6,7,6,8]
    [2,3,4]
    [5,6,8] -> infeasible
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
    fun `test scenario - 1,2,3,5,6,7,6,7,8`() {
        val tags = listOf(
            Tag(id = 3L, name = "Tag 0", description = "Tag description 0"),
            Tag(id = 4L, name = "Tag 1", description = "Tag description 1")
        )

        val user = AppUser(
            5L, "test.test", "test", "test", "test@mail.com",
            "pw", AppUserRole.USER, ""
        )

        val parentQuestion = Question(
            id = 1L, title = "Title 1", questionText = "Dummy text 1",
            parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-10 11:30:13.416664")
        )

        val question = Question(
            id = 0L, title = "Title 2", questionText = "Dummy text 2",
            parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-10 11:30:13.416664")
        )

        val questionTags = listOf(
            QuestionTag(question = question, tag = tags[0]),
            QuestionTag(question = question, tag = tags[1])
        )

        every { tagRepository.findAllById(any()) } returns tags
        every { appUserRepository.findByIdOrNull(any()) } returns user
        every { questionRepository.findByIdOrNull(any()) } returns parentQuestion
        every { questionRepository.save(any()) } returns question

        every { questionTagRepository.save(match { it.question.id == question.id && it.tag.id == tags[0].id }) } returns questionTags[0]
        every { questionTagRepository.save(match { it.question.id == question.id && it.tag.id == tags[1].id }) } returns questionTags[1]

        val result = questionService.createQuestion(
            question.title, question.questionText, null,
            question.user!!.id, tags.map { it.id }.toList()
        )

        assert(result is QuestionCreated)
    }

    @Test
    fun `test scenario - 1,2,3,4`() {
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