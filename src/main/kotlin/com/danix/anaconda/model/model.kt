package com.danix.anaconda.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "CONTENT_TYPE_FIELD")
data class ContentTypeFieldEntity(

        @Id
        @GeneratedValue
        val id: Long? = null,

        val identifier: String,
        val name: String,
        val type: String
)
