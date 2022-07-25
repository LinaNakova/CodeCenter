package com.sorsix.backendapplication.api

import com.sorsix.backendapplication.api.dto.TagRequest
import com.sorsix.backendapplication.domain.QuestionFailed
import com.sorsix.backendapplication.domain.Tag
import com.sorsix.backendapplication.domain.TagCreated
import com.sorsix.backendapplication.domain.TagFailed
import com.sorsix.backendapplication.service.TagService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tag")
class TagController(
    val tagService: TagService
) {

    @GetMapping
    fun getTags(): List<Tag>? {
        return tagService.getAllTags();
    }
    @GetMapping("/search/{tag}")
    fun getSearchTags(@PathVariable tag : String) : List<Tag>?
    {
        return this.tagService.getSearchTags(tag)
    }
    @PostMapping
    fun createTag(@RequestBody request: TagRequest): ResponseEntity<String> {
        val result = tagService.createTag(
            request.name,
            request.description
        )

        val resultString = when (result) {
            is TagCreated -> {
                result.tag.toString();
            }
            is TagFailed -> {
                "Failed because " + result.errorMessage;
            }
        }
        return if (result.success()) {
            ResponseEntity.ok(resultString);
        } else {
            ResponseEntity.badRequest().body(resultString);
        }
    }

}