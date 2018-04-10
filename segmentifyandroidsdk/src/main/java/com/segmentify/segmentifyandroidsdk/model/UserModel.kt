package com.segmentify.segmentifyandroidsdk.model

class UserModel : SegmentifyObject() {
        var username:String? = null
        var email:String? = null
        var age:String? = null
        var birthdate:String? = null
        var gender:String? = null
        var fullName:String? = null
        var mobilePhone:String? = null
        var isRegistered:Boolean? = null
        var isLogin:Boolean? = null
        var userOperationStep:String? = null
        var memberSince:String? = null
        var oldUserId:String? = null
        var location:String? = null
        var segments:ArrayList<String>? = null
        var step:String? = null
}