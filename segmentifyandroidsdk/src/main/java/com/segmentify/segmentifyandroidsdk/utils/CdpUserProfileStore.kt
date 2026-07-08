package com.segmentify.segmentifyandroidsdk.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.model.CdpEventsPayload
import java.util.TreeMap

/**
 * Android equivalent of the web SDK's secure-user-profile-store.ts.
 *
 * Deduplicates USER_IDENTITY events: an identify is only sent when the profile
 * snapshot changed since the last successfully sent one. The last-sent snapshot
 * is persisted (as canonical JSON) via [ProfileSnapshotStorage] and mirrored to an
 * in-memory field, matching the web behavior of comparing sorted-key JSON.
 */
class CdpUserProfileStore(private val storage: ProfileSnapshotStorage) {

    interface ProfileSnapshotStorage {
        fun read(): String?
        fun write(json: String)
        fun clear()
    }

    private val gson = Gson()
    private var lastSent: String? = null
    private var inFlight: String? = null

    @Synchronized
    fun shouldSendIdentify(payload: CdpEventsPayload): Boolean {
        val incoming = canonicalize(payload)
        val baseline = storage.read() ?: lastSent

        if (incoming == baseline || incoming == inFlight) {
            return false
        }

        inFlight = incoming
        return true
    }

    @Synchronized
    fun markIdentifySent(payload: CdpEventsPayload) {
        val canonical = canonicalize(payload)
        inFlight = null
        lastSent = canonical
        storage.write(canonical)
    }

    @Synchronized
    fun clearInFlightIdentify(payload: CdpEventsPayload) {
        if (inFlight == canonicalize(payload)) {
            inFlight = null
        }
    }

    private fun canonicalize(payload: CdpEventsPayload): String {
        return sortElement(gson.toJsonTree(payload)).toString()
    }

    private fun sortElement(element: JsonElement): JsonElement {
        return when {
            element.isJsonObject -> {
                val sorted = TreeMap<String, JsonElement>()
                for ((key, value) in element.asJsonObject.entrySet()) {
                    sorted[key] = sortElement(value)
                }
                JsonObject().apply { sorted.forEach { (k, v) -> add(k, v) } }
            }
            element.isJsonArray -> {
                JsonArray().apply { element.asJsonArray.forEach { add(sortElement(it)) } }
            }
            else -> element
        }
    }

    companion object {
        /** Default instance backed by the SDK's private SharedPreferences. */
        val default: CdpUserProfileStore by lazy {
            CdpUserProfileStore(object : ProfileSnapshotStorage {
                override fun read(): String? =
                    SegmentifyManager.clientPreferences?.getUserProfileSnapshot()

                override fun write(json: String) {
                    SegmentifyManager.clientPreferences?.setUserProfileSnapshot(json)
                }

                override fun clear() {
                    SegmentifyManager.clientPreferences?.clearUserProfileSnapshot()
                }
            })
        }
    }
}
