package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName

class ProductModel {
        var productId : String? = null
        var name:String? = null
        var title:String? = null
        var inStock:Boolean? = null
        var url:String? = null
        var mUrl:String? = null
        var image:String? = null
        var imageXS:String? = null
        var imageS:String? = null
        var imageM:String? = null
        var imageL:String? = null
        var imageXL: String? = null
        var category:String? = null
        var categories:ArrayList<String>? = null
        var brand:String? = null
        var price:Double? = null
        var oldPrice:Double? = null
        var quantity:Int? = null
        var gender:String? = null
        var colors:ArrayList<String>? = null
        var sizes:ArrayList<String>? = null
        var labels:ArrayList<String>? = null
        var noUpdate:Boolean? = null
}