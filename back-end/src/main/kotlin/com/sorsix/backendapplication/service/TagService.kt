package com.sorsix.backendapplication.service

import com.sorsix.backendapplication.domain.*
import com.sorsix.backendapplication.repository.QuestionTagRepository
import com.sorsix.backendapplication.repository.TagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TagService(
    val tagRepository: TagRepository,
    val questionTagRepository: QuestionTagRepository
) {

    fun getAllTags(): List<Tag>? {
        return tagRepository.findAll()
    }

    fun getSearchTags(term: String): List<Tag>? {
        return tagRepository.findAll()
            .filter { tag -> tag.name.contains(term) }
    }

    fun createTag(name: String, description: String): TagResult {
        return if (name == "" || description == "") {
            TagFailed("error :)")
        } else {
            val tag = Tag(
                name = name,
                description = description
            )
            tagRepository.save(tag)
            TagCreated(tag = tag)
        }
    }

    fun getAllQuestionsWithTag(tag: Long): List<Question>? {
        return this.questionTagRepository.findAll()
            .filter { questionTag -> questionTag.tag == tagRepository.findByIdOrNull(tag) }
            .map { questionTag -> questionTag.question }
            .filter { question -> question.parentQuestion == null }
    }

    fun getTagById(id: Long): Tag? {
        return this.tagRepository.findByIdOrNull(id)
    }

    fun getTagsFromUser(id: Long): Set<Tag>? {
        return this.questionTagRepository.findAll()
            .filter { questionTag ->
                questionTag.question.user?.id == id && questionTag.question.parentQuestion == null}
            .map { questionTag -> questionTag.tag }
            .toSet()
    }


}