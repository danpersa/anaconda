package com.danix.anaconda

import com.fasterxml.jackson.annotation.JsonProperty

data class Value(
        @JsonProperty("id")
        val id: Long,

        @JsonProperty("quote")
        val quote: String)

data class Quote(
        @JsonProperty("type")
        val type: String,

        @JsonProperty("value")
        val value: Value)
