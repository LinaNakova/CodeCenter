package com.sorsix.backendapplication.unitTests

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
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.data.domain.Sort


class QuestionServiceTest {

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
        questionService = QuestionService(questionRepository, tagRepository, questionTagRepository, appUserRepository, likeUnlikeRepository)
    }

    @Test
    fun testFindById() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val question = Question(id = 0L, title = "Title 0", questionText = "Dummy text 0", parentQuestion = null, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1)

        every { questionRepository.findByIdOrNull(question.id) } returns question

        val resultQuestion = questionService.findById(question.id)

        assertEquals(question, resultQuestion);
    }

    @Test
    fun testGetAnswersForQuestion() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val question = Question(id = 0L, title = "Title 0", questionText = "Dummy text 0", parentQuestion = null, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1)

        val questions = listOf(
                question,
                Question(id = 1L, title = "Title 1", questionText = "Dummy text 1", parentQuestion = question, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1),
                Question(id = 2L, title = "Title 2", questionText = "Dummy text 2", parentQuestion = question, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1),
        )

        every { questionRepository.findAll() } returns questions

        val resultQuestions = questionService.getAnswersForQuestion(question.id)

        assertEquals(resultQuestions, questions.filter { question.id == it.parentQuestion?.id })

    }

    @Test
    fun testFindAllQuestionsWithMentionedWord() {
        val word = "word"

        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val question = Question(id = 0L, title = "Title 0", questionText = "Dummy text 0", parentQuestion = null, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1)

        val questions = listOf(
                question,
                Question(id = 1L, title = "Title 1", questionText = "Dummy text 1", parentQuestion = question, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1),
                Question(id = 2L, title = "Title 2", questionText = "Dummy text 2", parentQuestion = question, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1),
        )

        every { questionRepository.findAll() } returns questions

        val resultQuestions = questionService.findAllQuestionsWithMentionedWord(word)

        assertEquals(resultQuestions, questions.filter { it.title.contains(word) || it.questionText.contains(word) })

    }


    @Test
    fun testGetAllQuestionTags() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val question = Question(id = 0L, title = "Title 0", questionText = "Dummy text 0", parentQuestion = null, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1)

        val questions = listOf(
                question,
                Question(id = 1L, title = "Title 1", questionText = "Dummy text 1", parentQuestion = question, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1),
                Question(id = 2L, title = "Title 2", questionText = "Dummy text 2", parentQuestion = question, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1),
        )

        val tags = listOf(
                Tag(id = 3L, name = "Tag 0", description = "Tag description 0"),
                Tag(id = 4L, name = "Tag 1", description = "Tag description 1" )
        )

        val questionTag1 = QuestionTag(5L, questions[0], tags[0])
        val questionTag2 = QuestionTag(6L, questions[0], tags[0])

        val questionTags = listOf(
                questionTag1,
                questionTag2
        )

        every { questionTagRepository.findAll() } returns questionTags

        val resultQuestionTags = questionService.getAllQuestionTags(1L)

        assertEquals(resultQuestionTags, questionTags)
    }

    @Test
    fun testGetQuestionTags() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")
        val question = Question(id = 0L, title = "Title 0", questionText = "Dummy text 0", parentQuestion = null, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1)

        val questionTags = listOf(
                QuestionTag(1L, question, Tag(2L, "Tag 1", "Description 1")),
                QuestionTag(3L, question, Tag(4L, "Tag 2", "Description 2"))
        )

        every { questionTagRepository.findAll() } returns questionTags

        val resultQuestionTags = questionService.getQuestionTags(question.id)


        assertEquals(questionTags.filter { it.question.id == question.id }, resultQuestionTags)
    }

    @Test
    fun testGetSortedByTitle() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questions = listOf(
                Question(id = 1L, title = "Title 1", questionText = "Dummy text 1", parentQuestion = null, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1),
                Question(id = 2L, title = "Title 2", questionText = "Dummy text 2", parentQuestion = null, user = user, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1),
        )

        every { questionRepository.findAll(any<Sort>()) } returns questions

        val resultQuestions = questionService.getSortedByTitle()

        val sortedQuestions = questions.sortedBy { it.title }

        assertEquals(sortedQuestions, resultQuestions)
    }

    @Test
    fun testGetSortedByTitleDescending() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questions = listOf(
                Question(id = 1L, title = "B Question", questionText = "Dummy text 1", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 2L, title = "A Question", questionText = "Dummy text 2", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
        )

        every { questionRepository.findAll(Sort.by("title").descending()) } returns questions

        val resultQuestions = questionService.getSortedByTitleDescending()

        val sortedQuestions = questions.sortedByDescending { it.title }

        assertEquals(sortedQuestions, resultQuestions)
    }

    @Test
    fun testGetSortedByViewsDescending() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questions = listOf(
                Question(id = 1L, title = "B Question", questionText = "Dummy text 1", parentQuestion = null, user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 2L, title = "A Question", questionText = "Dummy text 2", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
        )

        every { questionRepository.findAll(Sort.by("views").descending()) } returns questions

        val resultQuestions = questionService.getSortedByViewsDescending()

        val sortedQuestions = questions.sortedByDescending { it.views }

        assertEquals(sortedQuestions, resultQuestions)
    }

    @Test
    fun testGetSortedByViewsAscending() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questions = listOf(
                Question(id = 1L, title = "B Question", questionText = "Dummy text 1", parentQuestion = null, user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 2L, title = "A Question", questionText = "Dummy text 2", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
        )

        every { questionRepository.findAll(Sort.by("views").ascending()) } returns questions

        val resultQuestions = questionService.getSortedByViewsAscending()

        val sortedQuestions = questions.sortedByDescending { it.views }

        assertEquals(sortedQuestions, resultQuestions)
    }

    @Test
    fun testGetLikes() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questionId = 3L
        val likeUnlikes = listOf(
                LikeUnlike(id = 1L, question = Question(id = questionId, title = "Title", questionText = "Text", parentQuestion = null, user = user, views = 0, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")), appUser = user, likeUnlike = true),
                LikeUnlike(id = 2L, question = Question(id = questionId, title = "Title", questionText = "Text", parentQuestion = null, user = user, views = 0, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")), appUser = user, likeUnlike = false),
        )

        every { likeUnlikeRepository.findAll() } returns likeUnlikes

        val resultLikes = questionService.getLikes(questionId)

        val (likes, dislikes) = likeUnlikes.partition { it.likeUnlike }
        val expectedLikes = likes.size - dislikes.size

        assertEquals(expectedLikes, resultLikes)
    }

    @Test
    fun testGetViews() {
        val questionId = 1L
        val viewsCount = 5

        val question = Question(id = questionId, title = "Title", questionText = "Text", parentQuestion = null, user = null, views = viewsCount, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))

        every { questionRepository.findByIdOrNull(questionId) } returns question

        val resultViews = questionService.getViews(questionId)

        assertEquals(viewsCount, resultViews)
    }

    @Test
    fun testGetQuestionsFromUser() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val question = Question(id = 1L, title = "Title", questionText = "Text", parentQuestion = null, user = null, views = 5, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))

        val questions = listOf(
                Question(id = 1L, title = "Question 1", parentQuestion = question, questionText = "Text 1", user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 2L, title = "Question 2", parentQuestion = question, questionText = "Text 2", user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
        )

        every { questionRepository.findAll() } returns questions

        val resultQuestions = questionService.getQuestionsFromUser(user.id)

        assertEquals(questions.filter { it.parentQuestion == null }, resultQuestions)
    }

    @Test
    fun testGetAnswersFromUser() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val question = Question(id = 1L, title = "Title", questionText = "Text", parentQuestion = null, user = null, views = 5, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))

        val questions = listOf(
                Question(id = 2L, title = "Answer 1", parentQuestion = question, questionText = "Text 1", user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 3L, title = "Answer 2", parentQuestion = question, questionText = "Text 2", user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))
        )

        every { questionRepository.findAll() } returns questions

        val resultAnswers = questionService.getAnswersFromUser(user.id)

        assertEquals(questions.filter { it.parentQuestion == question }, resultAnswers)
    }

    @Test
    fun testSortByLikes() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val question = Question(id = 1L, title = "Title", questionText = "Text", parentQuestion = null, user = user, views = 5, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))

        val answers = listOf(
                Question(id = 2L, title = "Answer 1", questionText = "Text 1", parentQuestion = question, user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 3L, title = "Answer 2", questionText = "Text 2", parentQuestion = question, user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))
                // Add more answers as needed
        )

        every { questionRepository.findAll() } returns answers
        every { questionRepository.findByIdOrNull(question.id) } returns question
        every { likeUnlikeRepository.findAll() } returns listOf(
                LikeUnlike(id = 1L, question = question, appUser = user, likeUnlike = true),
                LikeUnlike(id = 2L, question = answers[0], appUser = user, likeUnlike = true),
                LikeUnlike(id = 3L, question = answers[1], appUser = user, likeUnlike = false)
        )

        val resultSorted = questionService.sortByLikes(question.id)

        val sortedByLikes = answers.sortedBy { questionService.getLikes(it.id) }

        assertEquals(sortedByLikes, resultSorted)
    }

    @Test
    fun testSortByLikesDescending() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val question = Question(id = 1L, title = "Title", questionText = "Text", parentQuestion = null, user = user, views = 5, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))

        val answers = listOf(
                Question(id = 2L, title = "Answer 1", questionText = "Text 1", parentQuestion = question, user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 3L, title = "Answer 2", questionText = "Text 2", parentQuestion = question, user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))
        )

        every { questionRepository.findAll() } returns answers
        every { questionRepository.findByIdOrNull(question.id) } returns question
        every { likeUnlikeRepository.findAll() } returns listOf(
                LikeUnlike(id = 1L, question = question, appUser = user, likeUnlike = true),
                LikeUnlike(id = 2L, question = answers[0], appUser = user, likeUnlike = true),
                LikeUnlike(id = 3L, question = answers[1], appUser = user, likeUnlike = false)
        )

        val resultSorted = questionService.sortByLikesDescending(question.id)

        val sortedByLikesDescending = answers.sortedByDescending { questionService.getLikes(it.id) }

        assertEquals(sortedByLikesDescending, resultSorted)
    }

    @Test
    fun testSortByAnswersAscending() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questions = listOf(
                Question(id = 2L, title = "Answer 1", questionText = "Text 1", parentQuestion = null, user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 3L, title = "Answer 2", questionText = "Text 2", parentQuestion = null, user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))
        )

        every { questionRepository.findAll() } returns questions
        every { questionRepository.findByIdOrNull(questions[0].id) } returns questions[0]
        every { questionRepository.findByIdOrNull(questions[1].id) } returns questions[1]
        every { questionService.getAnswersForQuestion(questions[0].id) } returns emptyList()
        every { questionService.getAnswersForQuestion(questions[1].id) } returns listOf(questions[1])

        val resultSorted = questionService.sortByAnswersAscending()

        val sortedByAnswersAscending = questions.sortedBy { questionService.getAnswersForQuestion(it.id)?.size }

        assertEquals(sortedByAnswersAscending, resultSorted)
    }

    @Test
    fun testSortByAnswersDescending() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questions = listOf(
                Question(id = 1L, title = "Title 1", questionText = "Dummy text 1", parentQuestion = null, user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 2L, title = "Title 2", questionText = "Dummy text 2", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
        )

        val answers = listOf(
                Question(id = 3L, title = "Answer 1", questionText = "Text 1", parentQuestion = questions[0], user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 4L, title = "Answer 2", questionText = "Text 2", parentQuestion = questions[0], user = user, views = 2, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
                Question(id = 5L, title = "Answer 3", questionText = "Text 3", parentQuestion = questions[1], user = user, views = 1, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")),
        )

        every { questionRepository.findAll() } returns (questions + answers)

        val resultSorted = questionService.sortByAnswersDescending()

        val sortedByAnswersDescending = questions.sortedByDescending { question -> questionService.getAnswersForQuestion(question.id)?.size }

        assertEquals(sortedByAnswersDescending, resultSorted)
    }

    @Test
    fun testSortByDateAscending() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questions = listOf(
                Question(id = 1L, title = "Title 1", questionText = "Dummy text 1", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-10 11:30:13.416664")),
                Question(id = 2L, title = "Title 2", questionText = "Dummy text 2", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-09 11:30:13.416664")),
                Question(id = 3L, title = "Title 3", questionText = "Dummy text 3", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))
        )

        every { questionRepository.findAll(Sort.by("date")) } returns questions

        val resultSorted = questionService.sortByDateAscending()

        val sortedByDateAscending = questions.sortedBy { it.date }

        assertEquals(sortedByDateAscending, resultSorted)
    }

    @Test
    fun testSortByDateDescending() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questions = listOf(
                Question(id = 1L, title = "Title 1", questionText = "Dummy text 1", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-10 11:30:13.416664")),
                Question(id = 2L, title = "Title 2", questionText = "Dummy text 2", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-09 11:30:13.416664")),
                Question(id = 3L, title = "Title 3", questionText = "Dummy text 3", parentQuestion = null, user = user, views = 1, date = Timestamp.valueOf("2023-08-11 11:30:13.416664"))
        )

        every { questionRepository.findAll(Sort.by("date").descending()) } returns questions

        val resultSorted = questionService.sortByDateDescending()

        val sortedByDateDescending = questions.sortedByDescending { it.date }

        assertEquals(sortedByDateDescending, resultSorted)
    }

    @Test
    fun testCheckIfLikedByUser() {
        val user = AppUser(5L, "test.test", "test", "test", "test@mail.com", "pw", AppUserRole.USER, "")

        val questionId = 1L
        val userId = 2L

        val likeUnlikes = listOf(
                LikeUnlike(id = 1L, question = Question(id = questionId, title = "Title", questionText = "Text", parentQuestion = null, user = user, views = 0, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")), appUser = user, likeUnlike = true),
                LikeUnlike(id = 2L, question = Question(id = questionId, title = "Title", questionText = "Text", parentQuestion = null, user = user, views = 0, date = Timestamp.valueOf("2023-08-11 11:30:13.416664")), appUser = user, likeUnlike = false),
        )

        every { likeUnlikeRepository.findAll() } returns likeUnlikes

        val result = questionService.checkIfLikedByUser(questionId, userId)

        val userLike = likeUnlikes.find { it.question.id == questionId && it.appUser.id == userId }

        assertEquals(userLike?.likeUnlike, result)
    }


}