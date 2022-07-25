package com.sorsix.backendapplication.service

import com.sorsix.backendapplication.domain.*
import com.sorsix.backendapplication.repository.TagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TagService(
    val tagRepository: TagRepository
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
        }
        else {
            val tag = Tag(
                name = name,
                description = description
            )
            println(tag)
            tagRepository.save(tag);
            TagCreated(tag = tag);
        }
    }


}