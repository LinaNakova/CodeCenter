package com.sorsix.backendapplication.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "question")
data class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "title")
    val title: String,

    @Column(name = "question_text")
    val questionText: String,

    @ManyToOne
    @JoinColumn(name = "parent_question_id")
    @JsonManagedReference
    val parentQuestion: Question?,

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    @JsonManagedReference
    val user: AppUser?,
)
