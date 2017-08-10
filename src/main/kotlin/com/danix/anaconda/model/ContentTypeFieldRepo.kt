package com.danix.anaconda.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.concurrent.CompletableFuture
import java.util.stream.Stream


interface ContentTypeFieldRepo : JpaRepository<ContentTypeFieldEntity, Long> {

    fun findByName(name: String): Iterable<ContentTypeFieldEntity>

    fun findByIdentifier(identifier: String): CompletableFuture<ContentTypeFieldEntity>

    fun findByType(identifier: String): Stream<ContentTypeFieldEntity>
}
