package com.sorsix.backendapplication.service

import com.sorsix.backendapplication.domain.Tag
import com.sorsix.backendapplication.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
    val tagRepository: TagRepository
) {

    fun getAllTags():List<Tag>?{
        return tagRepository.findAll()
    }


}