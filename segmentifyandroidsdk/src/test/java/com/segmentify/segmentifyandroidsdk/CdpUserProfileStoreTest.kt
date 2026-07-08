package com.segmentify.segmentifyandroidsdk

import com.segmentify.segmentifyandroidsdk.model.CdpEventsPayload
import com.segmentify.segmentifyandroidsdk.model.Consent
import com.segmentify.segmentifyandroidsdk.utils.CdpUserProfileStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CdpUserProfileStoreTest {

    private class FakeStorage : CdpUserProfileStore.ProfileSnapshotStorage {
        var value: String? = null
        override fun read(): String? = value
        override fun write(json: String) { value = json }
        override fun clear() { value = null }
    }

    private fun newStore(): Pair<CdpUserProfileStore, FakeStorage> {
        val storage = FakeStorage()
        return CdpUserProfileStore(storage) to storage
    }

    @Test
    fun firstIdentifyShouldSend() {
        val (store, _) = newStore()
        assertTrue(store.shouldSendIdentify(CdpEventsPayload(email = "user@example.com")))
    }

    @Test
    fun identicalPayloadIsSkippedAfterSent() {
        val (store, _) = newStore()
        val payload = CdpEventsPayload(email = "user@example.com", emailConsent = Consent.SUBSCRIBED)

        assertTrue(store.shouldSendIdentify(payload))
        store.markIdentifySent(payload)

        assertFalse(store.shouldSendIdentify(payload.copy()))
    }

    @Test
    fun inFlightDuplicateIsSkipped() {
        val (store, _) = newStore()
        val payload = CdpEventsPayload(email = "user@example.com")

        assertTrue(store.shouldSendIdentify(payload))
        // Reserved but not yet marked sent: a duplicate must not be sent again.
        assertFalse(store.shouldSendIdentify(payload.copy()))
    }

    @Test
    fun changedPayloadIsSent() {
        val (store, _) = newStore()
        val first = CdpEventsPayload(email = "user@example.com")

        assertTrue(store.shouldSendIdentify(first))
        store.markIdentifySent(first)

        val changed = first.copy(email = "changed@example.com")
        assertTrue(store.shouldSendIdentify(changed))
    }

    @Test
    fun failedSendClearsInFlightSoRetryIsAllowed() {
        val (store, _) = newStore()
        val payload = CdpEventsPayload(email = "user@example.com")

        assertTrue(store.shouldSendIdentify(payload))
        store.clearInFlightIdentify(payload)

        assertTrue(store.shouldSendIdentify(payload.copy()))
    }

    @Test
    fun snapshotIsPersistedToStorageOnMarkSent() {
        val (store, storage) = newStore()
        val payload = CdpEventsPayload(email = "user@example.com")

        store.shouldSendIdentify(payload)
        store.markIdentifySent(payload)

        assertTrue(storage.value != null)

        // A brand-new store instance reading the same storage must dedup.
        val restored = CdpUserProfileStore(storage)
        assertFalse(restored.shouldSendIdentify(payload.copy()))
    }
}
