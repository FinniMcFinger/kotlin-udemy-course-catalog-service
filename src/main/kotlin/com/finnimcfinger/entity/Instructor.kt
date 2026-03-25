package com.finnimcfinger.entity

import jakarta.persistence.*

@Entity
@Table(name = "instructor")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    @OneToMany(
        mappedBy = "instructor",
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true
    )
    var courses: MutableList<Course> = mutableListOf(),
)