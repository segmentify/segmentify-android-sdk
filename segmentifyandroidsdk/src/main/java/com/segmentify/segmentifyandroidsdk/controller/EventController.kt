package com.segmentify.segmentifyandroidsdk.controller

import com.google.gson.Gson
import com.segmentify.segmentifyandroidsdk.model.*
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyLogger
import com.google.gson.reflect.TypeToken
import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import org.json.JSONArray
import org.json.JSONObject

internal object EventController {

    var productRecommendationGlobalList = arrayListOf<ProductRecommendationModel>()

    fun sendPageView(pageModel : PageModel,apiKey : String,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>){
        ConnectionManager.getEventFactory().sendPageView(pageModel,apiKey)
                .enqueue(object : NetworkCallback<EventResponseModel>(){
                    override fun onSuccess(response: EventResponseModel) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        segmentifyCallback.onDataLoaded(reformatResponse(response))
                    }
                })
    }

    fun sendCustomEvent(customEventModel: CustomEventModel,apiKey : String, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>){
        ConnectionManager.getEventFactory().sendCustomEvent(customEventModel,apiKey)
                .enqueue(object : NetworkCallback<EventResponseModel>(){
                    override fun onSuccess(response: EventResponseModel) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        //segmentifyCallback.onDataLoaded(response)
                    }
                })
    }

    fun sendProductView(productModel: ProductModel,apiKey : String, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>){
        ConnectionManager.getEventFactory().sendProductView(productModel,apiKey)
                .enqueue(object : NetworkCallback<EventResponseModel>(){
                    override fun onSuccess(response: EventResponseModel) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        //segmentifyCallback.onDataLoaded(response)
                    }
                })
    }

    fun sendAddOrRemoveBasket(basketModel: BasketModel,apiKey : String){
        ConnectionManager.getEventFactory().sendAddOrRemoveBasket(basketModel,apiKey)
                .enqueue(object : NetworkCallback<Any>(){
                    override fun onSuccess(response: Any) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                    }
                })
    }

    fun sendPurchase(checkoutModel: CheckoutModel,apiKey : String,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        ConnectionManager.getEventFactory().sendPurchase(checkoutModel,apiKey)
                .enqueue(object : NetworkCallback<EventResponseModel>(){
                    override fun onSuccess(response: EventResponseModel) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        //segmentifyCallback.onDataLoaded(response)
                    }
                })
    }

    fun sendUserOperation(userModel: UserModel,apiKey : String){
        ConnectionManager.getEventFactory().sendUserOperation(userModel,apiKey)
                .enqueue(object : NetworkCallback<Any>(){
                    override fun onSuccess(response: Any) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                    }
                })
    }

    fun sendChangeUser(userChangeModel: UserChangeModel,apiKey : String,segmentifyCallback : SegmentifyCallback<Boolean>){
        ConnectionManager.getEventFactory().sendChangeUser(userChangeModel,apiKey)
                .enqueue(object : NetworkCallback<Any>(){
                    override fun onSuccess(response: Any) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        segmentifyCallback.onDataLoaded(true)
                    }
                })
    }

    fun sendInteractionEvent(interactionModel: InteractionModel,apiKey : String){
        ConnectionManager.getEventFactory().sendInteractionEvent(interactionModel,apiKey)
                .enqueue(object : NetworkCallback<Any>(){
                    override fun onSuccess(response: Any) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                    }
                })
    }

    private fun reformatResponse(eventResponseModel: EventResponseModel) : ArrayList<RecommendationModel> {

        var reformatedReponseModelList = ArrayList<RecommendationModel>()

        for(response in eventResponseModel.responses?.get(0)!!){

            var recommendationModel = RecommendationModel()
            recommendationModel.notificationTitle = response.params?.notificationTitle
            recommendationModel.instanceId = response.params?.instanceId
            recommendationModel.actionId = response.params?.actionId

            var interactionModel = InteractionModel()
            interactionModel.type = "impression"
            interactionModel.instanceId = recommendationModel.instanceId
            interactionModel.interactionId = recommendationModel.actionId
            sendInteractionEvent(interactionModel, SegmentifyManager.configModel.apiKey!!)

            try {
                val recommendedProductsString = Gson().toJson(response.params?.recommendedProducts)
                var recommendedProductsJSON = JSONObject(recommendedProductsString)
                var recommendedProductItemArray : JSONArray? = recommendedProductsJSON.getJSONArray("RECOMMENDATION_SOURCE_STATIC_ITEMS")
                for(i in 0 until recommendedProductItemArray!!.length()){
                    recommendationModel.products?.add(parseJsonToProductModel(recommendedProductItemArray.getJSONObject(i)))
                }
            }catch (ex : Exception){
                SegmentifyLogger.printErrorLog(ex.message.toString())
            }
            var dynamicItemString = response.params?.dynamicItems?.replace("\"","*")
            dynamicItemString = dynamicItemString?.replace("*","\"")
            val listType = object : TypeToken<ArrayList<DynamicItemsModel>>() {}.type
            var dynamicItems : ArrayList<DynamicItemsModel> = Gson().fromJson<ArrayList<DynamicItemsModel>>(dynamicItemString, listType)

            for(dynamicItem in dynamicItems){
                var recommendationProductListName : String = ""
                if(dynamicItem.timeFrame.isNullOrBlank() && dynamicItem.score.isNullOrBlank()){
                    recommendationProductListName = dynamicItem.recommendationSource + "|null"
                }
                else if(!dynamicItem.timeFrame.isNullOrBlank() && dynamicItem.score.isNullOrBlank()){
                    recommendationProductListName = dynamicItem.recommendationSource + "|" + dynamicItem.timeFrame
                }
                else if(dynamicItem.timeFrame.isNullOrBlank() && !dynamicItem.score.isNullOrBlank()){
                    recommendationProductListName = dynamicItem.recommendationSource + "|null|" + dynamicItem.score
                }
                else if(!dynamicItem.timeFrame.isNullOrBlank() && !dynamicItem.score.isNullOrBlank()){
                    recommendationProductListName = dynamicItem.recommendationSource + "|" + dynamicItem.timeFrame + "|" + dynamicItem.score
                }

                if(!recommendationProductListName.isNullOrBlank()){
                    val recommendedProductsString = Gson().toJson(response.params?.recommendedProducts)
                    var recommendedProductsJSON = JSONObject(recommendedProductsString)
                    var recommendedProductItemArray : JSONArray? = recommendedProductsJSON.getJSONArray(recommendationProductListName)

                    var i = 0
                    for(k in 0 until recommendedProductItemArray!!.length()){
                        var productModel = parseJsonToProductModel(recommendedProductItemArray!!.getJSONObject(k))
                        if(!isProductExistInGlobalProductList(productModel)){
                            recommendationModel.products?.add(productModel)
                            i = i + 1
                        }
                        else{
                            if(i > 0){
                                i = i - 1
                            }
                        }

                        if(i == dynamicItem.itemCount!!){
                            break
                        }
                    }
                }
            }

            reformatedReponseModelList.add(recommendationModel)
        }

        productRecommendationGlobalList.clear()

        return reformatedReponseModelList
    }

    private fun parseJsonToProductModel(productJson : JSONObject) : ProductRecommendationModel {
        var productRecommendationModel = ProductRecommendationModel()

        if(productJson.has("productId")){
            productRecommendationModel.productId = productJson.getString("productId")
        }
        if(productJson.has("name")){
            productRecommendationModel.name = productJson.getString("name")
        }
        if(productJson.has("url")){
            productRecommendationModel.url = productJson.getString("url")
        }
        if(productJson.has("image")){
            productRecommendationModel.image = productJson.getString("image")
        }
        if(productJson.has("price")){
            productRecommendationModel.price = productJson.getDouble("price")
        }

        if(productJson.has("categories")) {
            var categoriesJSONArray = productJson.getJSONArray("categories")
            var categories = ArrayList<String>()
            if(categoriesJSONArray != null) {
                for(i in 0 until categoriesJSONArray!!.length()) {
                    categories.add(categoriesJSONArray.getString(i))
                }
            }
            productRecommendationModel.categories = categories
        }

        if(productJson.has("inStock")) {
            productRecommendationModel.inStock = productJson.getBoolean("inStock")
        }
        if(productJson.has("brand")){
            productRecommendationModel.brand = productJson.getString("brand")
        }
        if(productJson.has("oldPrice")){
            productRecommendationModel.oldPrice = productJson.getDouble("oldPrice")
        }
        if(productJson.has("mUrl")){
            productRecommendationModel.mUrl = productJson.getString("mUrl")
        }
        if(productJson.has("imageXS")) {
            productRecommendationModel.imageXS = productJson.getString("imageXS")
        }
        if(productJson.has("imageS")) {
            productRecommendationModel.imageS = productJson.getString("imageS")
        }
        if(productJson.has("imageM")){
            productRecommendationModel.imageS = productJson.getString("imageM")
        }
        if(productJson.has("imageL")){
            productRecommendationModel.imageL = productJson.getString("imageL")
        }
        if(productJson.has("imageXL")){
            productRecommendationModel.imageXL = productJson.getString("imageXL")
        }
        if(productJson.has("category")){
            productRecommendationModel.category = productJson.getString("category")
        }
        if(productJson.has("gender")){
            productRecommendationModel.gender = productJson.getString("gender")
        }

        if(productJson.has("colors")){
            var colorsJSONArray = productJson.getJSONArray("colors")
            var colors = ArrayList<String>()
            if(colorsJSONArray != null){
                for(i in 0 until colorsJSONArray!!.length()) {
                    colors.add(colorsJSONArray.getString(i))
                }
            }
            productRecommendationModel.colors = colors
        }

        if(productJson.has("sizes")){
            var sizesJSONArray = productJson.getJSONArray("sizes")
            var sizes = ArrayList<String>()
            if(sizesJSONArray != null){
                for(i in 0 until sizesJSONArray!!.length()) {
                    sizes.add(sizesJSONArray.getString(i))
                }
            }
            productRecommendationModel.sizes = sizes
        }

        if(productJson.has("labels")){
            var labelsJSONArray = productJson.getJSONArray("labels")
            var labels = ArrayList<String>()
            if(labelsJSONArray != null){
                for(i in 0 until labelsJSONArray!!.length()) {
                    labels.add(labelsJSONArray.getString(i))
                }
            }
            productRecommendationModel.labels = labels
        }

        if(productJson.has("noUpdate")){
            productRecommendationModel.noUpdate = productJson.getBoolean("noUpdate")
        }

        return productRecommendationModel
    }

    private fun isProductExistInGlobalProductList(productRecommendationModel: ProductRecommendationModel) : Boolean {
        for(productModel in productRecommendationGlobalList){
            if(productModel.productId == productRecommendationModel.productId){
                return true
                break
            }
        }
        productRecommendationGlobalList.add(productRecommendationModel)
        return false
    }
}