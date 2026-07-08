package com.segmentify.segmentifyandroidsdk

import com.segmentify.segmentifyandroidsdk.model.CdpEventsPayload
import com.segmentify.segmentifyandroidsdk.model.Consent
import com.segmentify.segmentifyandroidsdk.utils.CdpConsent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CdpConsentTest {

    @Test
    fun emptyPayloadReturnsNull() {
        assertNull(CdpConsent.withConsentDefaults(CdpEventsPayload()))
    }

    @Test
    fun emptyPayloadWithEmptyCustomReturnsNull() {
        assertNull(CdpConsent.withConsentDefaults(CdpEventsPayload(custom = emptyMap())))
    }

    @Test
    fun payloadWithoutConsentIsReturnedUnchanged() {
        val payload = CdpEventsPayload(email = "user@example.com", username = "johndoe")

        val result = CdpConsent.withConsentDefaults(payload)!!

        assertEquals("user@example.com", result.email)
        assertEquals("johndoe", result.username)
        assertNull(result.callConsent)
        assertNull(result.emailConsent)
        assertNull(result.whatsappConsent)
        assertNull(result.smsConsent)
    }

    @Test
    fun singleConsentBackfillsRemainingConsentFieldsWithNotSet() {
        val payload = CdpEventsPayload(
            email = "user@example.com",
            emailConsent = Consent.SUBSCRIBED
        )

        val result = CdpConsent.withConsentDefaults(payload)!!

        assertEquals(Consent.SUBSCRIBED, result.emailConsent)
        assertEquals(Consent.NOT_SET, result.callConsent)
        assertEquals(Consent.NOT_SET, result.whatsappConsent)
        assertEquals(Consent.NOT_SET, result.smsConsent)
    }

    @Test
    fun existingConsentValuesArePreserved() {
        val payload = CdpEventsPayload(
            callConsent = Consent.SUBSCRIBED,
            emailConsent = Consent.UNSUBSCRIBED
        )

        val result = CdpConsent.withConsentDefaults(payload)!!

        assertEquals(Consent.SUBSCRIBED, result.callConsent)
        assertEquals(Consent.UNSUBSCRIBED, result.emailConsent)
        assertEquals(Consent.NOT_SET, result.whatsappConsent)
        assertEquals(Consent.NOT_SET, result.smsConsent)
    }

    @Test
    fun doesNotMutateInputPayload() {
        val payload = CdpEventsPayload(emailConsent = Consent.SUBSCRIBED)

        CdpConsent.withConsentDefaults(payload)

        assertNull(payload.callConsent)
        assertNull(payload.whatsappConsent)
        assertNull(payload.smsConsent)
    }
}
