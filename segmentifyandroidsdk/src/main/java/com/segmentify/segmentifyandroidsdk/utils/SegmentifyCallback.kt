package com.segmentify.segmentifyandroidsdk.utils

interface SegmentifyCallback<T> {
    fun onDataLoaded(data: T)
}