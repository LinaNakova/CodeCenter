package com.sorsix.backendapplication.inputSpacePartitioning

import com.sorsix.backendapplication.domain.Tag
import com.sorsix.backendapplication.domain.TagCreated
import com.sorsix.backendapplication.domain.TagFailed
import com.sorsix.backendapplication.domain.TagResult
import com.sorsix.backendapplication.repository.QuestionTagRepository
import com.sorsix.backendapplication.repository.TagRepository
import com.sorsix.backendapplication.service.TagService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class TagServiceTest {

    private lateinit var tagRepository: TagRepository
    private lateinit var questionTagRepository: QuestionTagRepository
    private lateinit var tagService: TagService

    /*
    Functionality Based Characteristics

    C1: name is an empty string -> true, false
    C2: description is an empty -> true, false

    Base Case: C1C2 -> FF -> TC1
    C1C2 -> FT -> TC2
    C1C2 -> TF -> TC3
    All tests are feasible
     */

    @BeforeEach
    fun setUp() {
        tagRepository = mockk()
        questionTagRepository = mockk()
        tagService = TagService(tagRepository, questionTagRepository)
    }

    @Test
    fun `base Case Test - TC1 - FF`() {
        val tagName = "ExampleTag"
        val tagDescription = "Example Description"
        val tag = Tag(0L, tagName, tagDescription)
        every { tagRepository.save(tag) } returns tag

        val result: TagResult = tagService.createTag(tagName, tagDescription)

        Assertions.assertEquals(tag, (result as TagCreated).tag)
    }

    @Test
    fun `TC2 - FT`() {
        val tagName = "ExampleTag"
        val tagDescription = ""
        val result: TagResult = tagService.createTag(tagName, tagDescription)
        Assertions.assertEquals(TagFailed("error"), result)
    }

    @Test
    fun `TC3 - TF`() {
        val tagName = ""
        val tagDescription = "Example Description"
        val result: TagResult = tagService.createTag(tagName, tagDescription)
        Assertions.assertEquals(TagFailed("error"), result)
    }

}