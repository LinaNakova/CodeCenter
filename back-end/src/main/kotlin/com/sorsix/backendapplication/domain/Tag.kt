package com.sorsix.backendapplication.domain

import javax.persistence.*

@Entity
@Table(name = "tag")
data class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val description: String
)
