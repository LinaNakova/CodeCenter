package com.sorsix.backendapplication.domain

import javax.persistence.*

@Entity
@Table(name = "question_tag")
data class QuestionTag(
    @Id
    @GeneratedValue
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "question_id")
    val question: Question,

    @ManyToOne
    @JoinColumn(name = "tag_id")
    val tag: Tag
)
