package com.unsoed.buktrackz.core.helper

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.unsoed.buktrackz.core.data.response.Results
import java.lang.reflect.Type

class ResultsDeserializer : JsonDeserializer<Results?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Results? {
        return if (json != null && json.isJsonObject) {
            context?.deserialize(json, Results::class.java)
        } else {
            null
        }
    }
}