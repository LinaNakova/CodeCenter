package com.sorsix.backendapplication.api

import com.sorsix.backendapplication.domain.Tag
import com.sorsix.backendapplication.service.TagService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tag")
class TagController(
    val tagService: TagService
) {

    @GetMapping
    fun getTags(): List<Tag>? {
        return tagService.getAllTags();
    }

}