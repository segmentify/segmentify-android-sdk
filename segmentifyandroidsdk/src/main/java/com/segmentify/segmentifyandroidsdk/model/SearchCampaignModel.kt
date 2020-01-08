package com.segmentify.segmentifyandroidsdk.model

class SearchCampaignModel {
        var instanceId : String? = null
        var name:String? = null
        var accountId:String? = null
        var status:String? = null
        var devices:ArrayList<String>? = null
        var searchDelay:Int? = null
        var minCharacterCount:Int? = null
        var searchUrlPrefix:String? = null
        var searchInputSelector:String? = null
        var hideCurrentSelector:String? = null
        var desktopItemCount:Int? = null
        var mobileItemCount:Int? = null
        var searchAssets:ArrayList<SearchAssetModel>? = null
        var stringSearchAssetTextMap:HashMap<String,SearchAssetTextModel>? = null
        var html:String? = null
        var preJs:String? = null
        var postJs:String? = null
        var css:String? = null
        var triggerSelector:String? = null
        var openingDirection:String? = null
}