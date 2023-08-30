package com.sorsix.backendapplication.unitTests

import com.sorsix.backendapplication.domain.*
import com.sorsix.backendapplication.domain.enum.AppUserRole
import com.sorsix.backendapplication.repository.QuestionTagRepository
import com.sorsix.backendapplication.repository.TagRepository
import com.sorsix.backendapplication.service.TagService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.sql.Timestamp

class TagServiceTest {

    private lateinit var tagRepository: TagRepository
    private lateinit var questionTagRepository: QuestionTagRepository
    private lateinit var tagService: TagService

    @BeforeEach
    fun setUp() {
        tagRepository = mockk()
        questionTagRepository = mockk()
        tagService = TagService(tagRepository, questionTagRepository)
    }

    @Test
    fun `test createTag method with valid inputs`() {
        val tagName = "ExampleTag"
        val tagDescription = "Example Description"
        val tag = Tag(0L, tagName, tagDescription)
        every { tagRepository.save(tag) } returns tag

        val result: TagResult = tagService.createTag(tagName, tagDescription)

        assertEquals(tag, (result as TagCreated).tag)
    }

    @Test
    fun `test createTag method with empty inputs`() {
        val result: TagResult = tagService.createTag("", "")
        assertEquals(TagFailed("error"), result)
    }

    @Test
    fun `test getAllTags method`() {
        val tags = listOf(
            Tag(0L, "Tag1", "Descr1"),
            Tag(1L, "Tag2", "Descr2"),
            Tag(2L, "Tag3", "Descr3")
        )
        every { tagRepository.findAll() } returns tags
        assertEquals(tagService.getAllTags(), tags)
    }

    @Test
    fun `test getTagById method`() {
        val tag = Tag(0L, "Tag1", "Descr1")
        every { tagRepository.findByIdOrNull(any()) } returns tag
        assertEquals(tagService.getTagById(0L), tag)
    }

    @Test
    fun `test getSearchTags method`() {
        val tags = listOf(
            Tag(0L, "Tag1", "Descr1"),
            Tag(1L, "Tag2", "Descr2"),
            Tag(2L, "Test", "Test")
        )
        every { tagRepository.findAll() } returns tags
        assertEquals(tagService.getSearchTags("Tag"), tags
            .filter { tag -> tag.name.contains("Tag") })
    }

    @Test
    fun `test getTagsFromUser method`() {
        val tags = listOf(
            Tag(0L, "Tag1", "Descr1"),
            Tag(1L, "Tag2", "Descr2"),
            Tag(2L, "Test", "Test")
        )
        val user = AppUser(5L, "test.test", "test", "test",
            "test@mail.com", "pw", AppUserRole.USER, "")
        val questions = listOf(
            Question(
                title = "Title 1", questionText = "Dummy text 1",
                parentQuestion = null, user = user,
                date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1
            ),
            Question(
                title = "Title 2", questionText = "Dummy text 2",
                parentQuestion = null, user = user,
                date = Timestamp.valueOf("2023-08-15 11:30:13.416664"), views = 1
            )
        )
        val questionTags = listOf(
            QuestionTag(question = questions[0], tag = tags[0]),
            QuestionTag(question = questions[0], tag = tags[1]),
            QuestionTag(question = questions[1], tag = tags[2]),
        )
        every { questionTagRepository.findAll() } returns questionTags
        assertEquals(tagService.getTagsFromUser(user.id), questionTags.filter { questionTag ->
            questionTag.question.user?.id == user.id && questionTag.question.parentQuestion == null}
            .map { questionTag -> questionTag.tag }
            .toSet())
    }

    @Test
    fun `test getAllQuestionsWithTag method`() {
        val tags = listOf(
            Tag(0L, "Tag1", "Descr1"),
            Tag(1L, "Tag2", "Descr2"),
            Tag(2L, "Test", "Test")
        )
        val user = AppUser(5L, "test.test", "test", "test",
            "test@mail.com", "pw", AppUserRole.USER, "")
        val questions = listOf(
            Question(
                title = "Title 1", questionText = "Dummy text 1",
                parentQuestion = null, user = user,
                date = Timestamp.valueOf("2023-08-11 11:30:13.416664"), views = 1
            ),
            Question(
                title = "Title 2", questionText = "Dummy text 2",
                parentQuestion = null, user = user,
                date = Timestamp.valueOf("2023-08-15 11:30:13.416664"), views = 1
            )
        )
        val questionTags = listOf(
            QuestionTag(question = questions[0], tag = tags[0]),
            QuestionTag(question = questions[0], tag = tags[1]),
            QuestionTag(question = questions[1], tag = tags[2]),
        )
        every { questionTagRepository.findAll() } returns questionTags
        every { tagRepository.findByIdOrNull(any()) } returns tags[0]
        assertEquals( tagService.getAllQuestionsWithTag(user.id), questionTags
            .filter { questionTag -> questionTag.tag == tagRepository.findByIdOrNull(tags[0].id) }
            .map { questionTag -> questionTag.question }
            .filter { question -> question.parentQuestion == null })
    }

}