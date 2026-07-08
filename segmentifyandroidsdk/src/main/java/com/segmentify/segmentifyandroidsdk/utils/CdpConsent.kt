package com.segmentify.segmentifyandroidsdk.utils

import com.segmentify.segmentifyandroidsdk.model.CdpEventsPayload
import com.segmentify.segmentifyandroidsdk.model.Consent

/**
 * Port of the web SDK's cdp-consent.ts.
 *
 * - Returns null when the payload carries no data (caller should skip sending).
 * - When the payload contains no consent field, it is returned unchanged.
 * - When the payload contains at least one consent field, the remaining consent
 *   fields are backfilled with [Consent.NOT_SET].
 */
object CdpConsent {

    fun withConsentDefaults(params: CdpEventsPayload): CdpEventsPayload? {
        if (isEmptyPayload(params)) return null

        val cloned = params.copy(custom = params.custom?.toMap())

        val includesConsent = cloned.callConsent != null ||
                cloned.emailConsent != null ||
                cloned.whatsappConsent != null ||
                cloned.smsConsent != null

        if (!includesConsent) return cloned

        if (cloned.callConsent == null) cloned.callConsent = Consent.NOT_SET
        if (cloned.emailConsent == null) cloned.emailConsent = Consent.NOT_SET
        if (cloned.whatsappConsent == null) cloned.whatsappConsent = Consent.NOT_SET
        if (cloned.smsConsent == null) cloned.smsConsent = Consent.NOT_SET

        return cloned
    }

    private fun isEmptyPayload(params: CdpEventsPayload): Boolean {
        return params.phone == null &&
                params.email == null &&
                params.username == null &&
                params.source == null &&
                params.gender == null &&
                params.callConsent == null &&
                params.emailConsent == null &&
                params.whatsappConsent == null &&
                params.smsConsent == null &&
                params.fullName == null &&
                params.custom.isNullOrEmpty()
    }
}
