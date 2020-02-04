package com.segmentify.segmentifyandroidsdk.model

class SearchCampaignModel {
        var instanceId : String? = null
        var name:String? = null
        var status:String? = null
        var devices:ArrayList<String>? = null
        var searchDelay:Int? = null
        var minCharacterCount:Int? = null
        var searchUrlPrefix:String? = null
        var mobileItemCount:Int? = null
        var stringSearchAssetTextMap:HashMap<String,SearchAssetTextModel>? = null
}