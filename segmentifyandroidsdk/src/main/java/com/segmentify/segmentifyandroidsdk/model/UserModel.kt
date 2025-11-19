package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName

class UserModel : SegmentifyObject() {
        var externalId:String? = null
        var email:String? = null
        var age:String? = null
        var birthDate:String? = null
        var gender:String? = null
        var fullName:String? = null
        var mobilePhone:String? = null
        var isRegistered:String? = null
        var isLogin:Boolean? = null
        var lastSearchDeletedKeywords:String? = null

        @SerializedName("step")
        var userOperationStep:String? = null

        var memberSince:String? = null
        var oldUserId:String? = null
        var location:String? = null
        var segments:ArrayList<String>? = null
        //var step:String? = null
}