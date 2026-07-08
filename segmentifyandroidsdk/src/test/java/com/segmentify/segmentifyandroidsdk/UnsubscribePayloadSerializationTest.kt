package com.segmentify.segmentifyandroidsdk

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.segmentify.segmentifyandroidsdk.model.Channel
import com.segmentify.segmentifyandroidsdk.model.Purpose
import com.segmentify.segmentifyandroidsdk.model.UnsubscribePayload
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UnsubscribePayloadSerializationTest {

    private val gson = Gson()

    private fun toJson(payload: UnsubscribePayload): JsonObject =
        JsonParser.parseString(gson.toJson(payload)).asJsonObject

    @Test
    fun channelOnlyOmitsNullFields() {
        val json = toJson(UnsubscribePayload(channel = Channel.WEB_PUSH))

        assertEquals("web_push", json.get("channel").asString)
        assertFalse(json.has("email"))
        assertFalse(json.has("phone"))
        assertFalse(json.has("purpose"))
    }

    @Test
    fun emailChannelIncludesEmail() {
        val json = toJson(
            UnsubscribePayload(
                channel = Channel.EMAIL,
                email = "user@example.com",
                purpose = listOf(Purpose.ALL)
            )
        )

        assertEquals("email", json.get("channel").asString)
        assertEquals("user@example.com", json.get("email").asString)
        assertTrue(json.has("purpose"))
        assertEquals("ALL", json.getAsJsonArray("purpose").get(0).asString)
    }
}
