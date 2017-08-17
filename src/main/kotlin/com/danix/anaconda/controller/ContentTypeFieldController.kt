package com.danix.anaconda.controller

import com.codahale.metrics.annotation.Timed
import com.danix.anaconda.dtos.ContentTypeField
import com.danix.anaconda.service.ContentTypeFieldService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono


@RestController
internal class ContentTypeFieldController
@Autowired
constructor(private val contentTypeFieldService: ContentTypeFieldService) {

    @Timed(name = timerContentTypeFields, absolute = true)
    @RequestMapping("/content-type-fields")
    fun contentTypeFields(pageable: Pageable): Page<ContentTypeField> {

        logger.debug("All the content type fields")
        return contentTypeFieldService.getContentTypeFields(pageable)
    }

    @Timed(name = timerContentTypeFields, absolute = true)
    @RequestMapping("/content-type-fields/{identifier}")
    fun contentTypeFieldByIdentifier(@PathVariable(value = "identifier") identifier: String):
            Mono<ContentTypeField> {

        logger.debug("All the content type fields")
        return contentTypeFieldService
                .getContentTypeFiledByIdentifier(identifier)
                .toFuture()
                .toMono()
    }

    private companion object {

        private val logger = LoggerFactory.getLogger(ContentTypeFieldController::class.java)
        const val timerContentTypeFields = "timer.contentTypeFields"
    }
}
