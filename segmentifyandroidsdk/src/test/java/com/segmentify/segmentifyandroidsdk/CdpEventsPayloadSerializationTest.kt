package com.segmentify.segmentifyandroidsdk

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.segmentify.segmentifyandroidsdk.model.CdpEventsPayload
import com.segmentify.segmentifyandroidsdk.model.Consent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CdpEventsPayloadSerializationTest {

    private val gson = Gson()

    private fun toJson(payload: CdpEventsPayload): JsonObject =
        JsonParser.parseString(gson.toJson(payload)).asJsonObject

    @Test
    fun nullFieldsAreOmitted() {
        val json = toJson(CdpEventsPayload(email = "user@example.com"))

        assertTrue(json.has("email"))
        assertFalse(json.has("phone"))
        assertFalse(json.has("username"))
        assertFalse(json.has("callConsent"))
        assertFalse(json.has("custom"))
    }

    @Test
    fun fieldNamesUseCamelCase() {
        val json = toJson(
            CdpEventsPayload(
                fullName = "John Doe",
                emailConsent = Consent.SUBSCRIBED
            )
        )

        assertTrue(json.has("fullName"))
        assertTrue(json.has("emailConsent"))
    }

    @Test
    fun consentSerializesViaSerializedName() {
        val json = toJson(
            CdpEventsPayload(
                callConsent = Consent.SUBSCRIBED,
                emailConsent = Consent.UNSUBSCRIBED,
                whatsappConsent = Consent.NOT_SET
            )
        )

        assertEquals("SUBSCRIBED", json.get("callConsent").asString)
        assertEquals("UNSUBSCRIBED", json.get("emailConsent").asString)
        assertEquals("NOT_SET", json.get("whatsappConsent").asString)
    }

    @Test
    fun nestedCustomObjectIsPreserved() {
        val json = toJson(
            CdpEventsPayload(
                email = "user@example.com",
                custom = mapOf("loyalty_level" to "gold")
            )
        )

        assertTrue(json.has("custom"))
        assertEquals("gold", json.getAsJsonObject("custom").get("loyalty_level").asString)
    }
}
