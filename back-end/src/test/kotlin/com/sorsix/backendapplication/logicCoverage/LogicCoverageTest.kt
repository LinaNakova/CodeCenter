package com.sorsix.backendapplication.logicCoverage

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


class LogicCoverageTest {

    private lateinit var tagRepository: TagRepository
    private lateinit var questionTagRepository: QuestionTagRepository
    private lateinit var tagService: TagService

    /*
    (2,4), (3,4)
    2 -> TF
    3 -> FT
    4 -> FF
    * */

    @BeforeEach
    fun setUp() {
        tagRepository = mockk()
        questionTagRepository = mockk()
        tagService = TagService(tagRepository, questionTagRepository)
    }

    @Test
    fun `TC2 - TF`() {
        val tagName = ""
        val tagDescription = "Example Description"
        val result: TagResult = tagService.createTag(tagName, tagDescription)
        Assertions.assertEquals(TagFailed("error"), result)
    }

    @Test
    fun `TC3 - FT`() {
        val tagName = "ExampleTag"
        val tagDescription = ""
        val result: TagResult = tagService.createTag(tagName, tagDescription)
        Assertions.assertEquals(TagFailed("error"), result)
    }

    @Test
    fun `TC4 - FF`() {
        val tagName = "ExampleTag"
        val tagDescription = "Example Description"
        val tag = Tag(0L, tagName, tagDescription)
        every { tagRepository.save(tag) } returns tag

        val result: TagResult = tagService.createTag(tagName, tagDescription)

        Assertions.assertEquals(tag, (result as TagCreated).tag)
    }


}