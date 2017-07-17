package com.danix.anaconda

import com.fasterxml.jackson.annotation.JsonProperty

data class Greeting(
        @JsonProperty("id")
        val id: Long,

        @JsonProperty("content")
        val content: String)
