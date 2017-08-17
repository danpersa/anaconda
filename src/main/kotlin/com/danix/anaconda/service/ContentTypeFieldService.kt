package com.danix.anaconda.service

import com.danix.anaconda.dtos.ContentTypeField
import com.danix.anaconda.model.ContentTypeFieldRepo
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import rx.Observable

@Service
class ContentTypeFieldService
@Autowired
constructor(private val contentFieldTypeFieldRepo: ContentTypeFieldRepo) {

    @HystrixCommand(
            commandKey = "getContentTypeFields",
            groupKey = contentTypeFieldService,
            commandProperties = arrayOf(
                    HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")),
            fallbackMethod = "getContentTypeFieldsFallback"
    )
    fun getContentTypeFields(pageable: Pageable): Page<ContentTypeField> =
            contentFieldTypeFieldRepo.findAll(pageable).map {
                ContentTypeField(
                        it.id,
                        it.identifier,
                        it.name,
                        it.type)
            }

    @HystrixCommand(
            commandKey = "getContentTypeFiledByIdentifier",
            groupKey = contentTypeFieldService,
            commandProperties = arrayOf(
                    HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")),
            fallbackMethod = "getContentTypeFiledByIdentifierFallback"
    )
    fun getContentTypeFiledByIdentifier(identifier: String): Observable<ContentTypeField> {

        return Observable.from(contentFieldTypeFieldRepo.findByIdentifier(identifier).thenApply {
            ContentTypeField(
                    it.id,
                    it.identifier,
                    it.name,
                    it.type)
        })
    }

    fun getContentTypeFieldsFallback(pageable: Pageable): Page<ContentTypeField> {
        logger.info("ContentTypeFieldService fallback")
        throw DatabaseTimeoutException()
    }

    fun getContentTypeFiledByIdentifierFallback(identifier: String): Observable<ContentTypeField> {
        logger.info("ContentTypeFieldService fallback")
        throw DatabaseTimeoutException()
    }

    private companion object {
        const val contentTypeFieldService = "ContentTypeFieldService"
        private val logger = LoggerFactory.getLogger(ContentTypeFieldService::class.java)
    }
}
