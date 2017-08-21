package com.danix.anaconda.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "CONTENT_TYPE_FIELD")
data class ContentTypeFieldEntity(

        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Long? = null,

        val identifier: String,
        val name: String,
        val type: String
)
