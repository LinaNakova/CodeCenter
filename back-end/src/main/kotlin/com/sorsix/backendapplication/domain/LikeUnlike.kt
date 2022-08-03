package com.sorsix.backendapplication.domain

import javax.persistence.*

@Entity
@Table(name = "like_unlike_question")
data class LikeUnlike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    val appUser: AppUser,

    @ManyToOne
    @JoinColumn(name = "question_id")
    val question: Question,

    @Column(name = "like_unlike")
    val like_unlike: Boolean
) {}