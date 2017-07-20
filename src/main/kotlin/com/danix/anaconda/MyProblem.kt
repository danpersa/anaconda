package com.danix.anaconda

import com.fasterxml.jackson.annotation.JsonProperty
import javax.ws.rs.core.Response.StatusType

data class MyProblem(
        @JsonProperty("title")
        val title: String?,

        @JsonProperty("status")
        val status: StatusType?)
