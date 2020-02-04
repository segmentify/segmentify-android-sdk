package com.segmentify.segmentifyandroidsdk.controller

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.model.*
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback
import com.segmentify.segmentifyandroidsdk.utils.Constant
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyLogger
import org.json.JSONArray
import org.json.JSONObject

internal object EventController {

    var productRecommendationGlobalList = arrayListOf<ProductRecommendationModel>()

    fun sendPageView(pageModel : PageModel,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>){

        if(!pageModel.userId.isNullOrEmpty() && !pageModel.sessionId.isNullOrEmpty()){

            ConnectionManager.getEventFactory().sendPageView(pageModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<EventResponseModel>(){
                        override fun onSuccess(response: EventResponseModel) {
                            segmentifyCallback.onDataLoaded(reformatResponse(response))
                        }
                    })
        }

        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing pageview event")
            return
        }

    }

    fun sendSearchView(pageModel: SearchPageModel, segmentifyCallback: SegmentifyCallback<SearchResponseModel>){
        if(!pageModel.userId.isNullOrEmpty() && !pageModel.sessionId.isNullOrEmpty()){
            ConnectionManager.getEventFactory().sendSearchView(pageModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<SearchEventResponseModel>(){
                        override fun onSuccess(response: SearchEventResponseModel) {
                            segmentifyCallback.onDataLoaded(reformatSearchResponse(response))
                        }
                    })
        }

        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing pageview event")
            return
        }
    }

    fun reformatSearchResponse(response: SearchEventResponseModel): SearchResponseModel {
        var returnVal = SearchResponseModel()
        var list = response.search?.get(0)
        if(list != null){
            returnVal = list[0]
        }

        SegmentifyManager.sendWidgetView("SEARCH", "static")
        SegmentifyManager.sendImpression("SEARCH", "static")

        return returnVal
    }

    fun sendCustomEvent(customEventModel: CustomEventModel, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {

        if(!customEventModel.userId.isNullOrEmpty() && !customEventModel.sessionId.isNullOrEmpty()){


            ConnectionManager.getEventFactory().sendCustomEvent(customEventModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<EventResponseModel>() {
                        override fun onSuccess(response: EventResponseModel) {
                            segmentifyCallback.onDataLoaded(reformatResponse(response))
                        }
                    })
        }
        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing custom event")
            return
        }


    }

    fun sendProductView(productModel: ProductModel, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>){


        if(!productModel.userId.isNullOrEmpty() && !productModel.sessionId.isNullOrEmpty()){


            ConnectionManager.getEventFactory().sendProductView(productModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<EventResponseModel>() {
                        override fun onSuccess(response: EventResponseModel) {
                            segmentifyCallback.onDataLoaded(reformatResponse(response))
                        }
                    })
        }

        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing productview event")
            return
        }
    }

    fun sendAddOrRemoveBasket(basketModel: BasketModel){

        if(!basketModel.userId.isNullOrEmpty() && !basketModel.sessionId.isNullOrEmpty()){

            ConnectionManager.getEventFactory().sendAddOrRemoveBasket(basketModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<Any>(){
                        override fun onSuccess(response: Any) {
                        }
                    })
        }
        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing addorremove event")
            return
        }

    }

    fun sendCheckout(checkoutModel: CheckoutModel,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {

        if(!checkoutModel.userId.isNullOrEmpty() && !checkoutModel.sessionId.isNullOrEmpty()){
            ConnectionManager.getEventFactory().sendPurchase(checkoutModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<EventResponseModel>(){
                        override fun onSuccess(response: EventResponseModel) {
                            segmentifyCallback.onDataLoaded(reformatResponse(response))
                        }
                    })
        }

        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing checkout event")
            return
        }

    }

    fun sendUserOperation(userModel: UserModel){

        if(!userModel.userId.isNullOrEmpty() && !userModel.sessionId.isNullOrEmpty()){

            ConnectionManager.getEventFactory().sendUserOperation(userModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<Any>(){
                        override fun onSuccess(response: Any) {
                        }
                    })
        }
        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing useroperation event")
            return
        }
    }

    fun sendChangeUser(userChangeModel: UserChangeModel,segmentifyCallback : SegmentifyCallback<Boolean>){

        if(!userChangeModel.userId.isNullOrEmpty() && !userChangeModel.sessionId.isNullOrEmpty()){

            ConnectionManager.getEventFactory().sendChangeUser(userChangeModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<Any>(){
                        override fun onSuccess(response: Any) {
                            segmentifyCallback.onDataLoaded(true)
                        }
                    })
        }
        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing changeuser event")
            return
        }


    }

    fun sendInteractionEvent(interactionModel: InteractionModel){

        if(!interactionModel.userId.isNullOrEmpty() && !interactionModel.sessionId.isNullOrEmpty()){
            ConnectionManager.getEventFactory().sendInteractionEvent(interactionModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<Any>(){
                        override fun onSuccess(response: Any) {
                        }
                    })
        }
        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing interaction event")
            return
        }

    }

    fun sendBannerOperations(bannerOperationOperationsModel: BannerOperationsModel){

        if(!bannerOperationOperationsModel.userId.isNullOrEmpty() && !bannerOperationOperationsModel.sessionId.isNullOrEmpty()){
            ConnectionManager.getEventFactory().sendBannerOperations(bannerOperationOperationsModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<Any>(){
                        override fun onSuccess(response: Any) {
                        }
                    })
        }
        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing banner operation event")
            return
        }

    }

    fun sendBannerGroupView(bannerGroupViewModel: BannerGroupViewModel){

        if(!bannerGroupViewModel.userId.isNullOrEmpty() && !bannerGroupViewModel.sessionId.isNullOrEmpty()){
            ConnectionManager.getEventFactory().sendBannerGroupView(bannerGroupViewModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<Any>(){
                        override fun onSuccess(response: Any) {
                            try {
                                var resp = (response as LinkedTreeMap<String, Any>)
                                if ((resp.get("statusCode") as String).equals("SUCCESS")) {
                                    var responses = (resp.get("responses") as ArrayList<Any>)
                                    if (responses.size > 0) {
                                        var response = (responses.get(0) as ArrayList<Any>)
                                        if (response.size > 0) {
                                            var responseDetail = (response.get(0) as LinkedTreeMap<String, Any>)
                                            if ((responseDetail.get("type") as String).equals("sendBannerDetails")) {
                                                SegmentifyManager.sendInternalBannerGroupEvent(bannerGroupViewModel)
                                            }
                                        }
                                    }
                                }
                            } catch (err: Exception) {
                            }
                        }
                    })
        }
        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing banner group view event")
            return
        }
    }

    fun sendInternalBannerGroup(bannerGroupViewModel: BannerGroupViewModel){

        if(!bannerGroupViewModel.userId.isNullOrEmpty() && !bannerGroupViewModel.sessionId.isNullOrEmpty()){
            ConnectionManager.getEventFactory().sendInternalBannerGroup(bannerGroupViewModel,SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<Any>(){
                        override fun onSuccess(response: Any) {
                        }
                    })
        }
        else{
            SegmentifyLogger.printErrorLog("You must fill userid&sessionid before accessing internal banner group event")
            return
        }
    }

    private fun reformatResponse(eventResponseModel: EventResponseModel) : ArrayList<RecommendationModel> {

        var reformatedReponseModelList = ArrayList<RecommendationModel>()

        if(eventResponseModel != null && eventResponseModel.responses != null && eventResponseModel?.responses?.size!!>0){
        for(response in eventResponseModel.responses?.get(0)!!){

            var recommendationModel = RecommendationModel()
            recommendationModel.notificationTitle = response.params?.notificationTitle
            recommendationModel.instanceId = response.params?.instanceId
            recommendationModel.actionId = response.params?.actionId

            var interactionModel = InteractionModel()
            interactionModel.eventName = Constant.interactionEventName
            interactionModel.type = "impression"
            interactionModel.instanceId = recommendationModel.instanceId
            interactionModel.interactionId = recommendationModel.actionId
            sendInteractionEvent(interactionModel)

            try {
                val recommendedProductsString = Gson().toJson(response.params?.recommendedProducts)
                var recommendedProductsJSON = JSONObject(recommendedProductsString)
                var recommendedProductItemArray: JSONArray? = recommendedProductsJSON.getJSONArray("RECOMMENDATION_SOURCE_STATIC_ITEMS")
                for (i in 0 until recommendedProductItemArray!!.length()) {
                    recommendationModel.products?.add(parseJsonToProductModel(recommendedProductItemArray.getJSONObject(i)))
                }
            } catch (ex: Exception) {
                SegmentifyLogger.printErrorLog(ex.message.toString())
            }
            var dynamicItemString = response.params?.dynamicItems?.replace("\"", "*")
            dynamicItemString = dynamicItemString?.replace("*", "\"")
            val listType = object : TypeToken<ArrayList<DynamicItemsModel>>() {}.type
            var dynamicItems: ArrayList<DynamicItemsModel> = Gson().fromJson<ArrayList<DynamicItemsModel>>(dynamicItemString, listType)

            for (dynamicItem in dynamicItems) {
                var recommendationProductListName: String = ""
                if (!dynamicItem.recommendationSource.isNullOrEmpty()) {
                    recommendationProductListName = dynamicItem.recommendationSource!!
                    if (!dynamicItem.timeFrame.isNullOrBlank() || !dynamicItem.timeFrame.isNullOrEmpty()) {
                        recommendationProductListName = recommendationProductListName + "|" + dynamicItem.timeFrame

                        if (!dynamicItem.score.isNullOrBlank() || !dynamicItem.score.isNullOrEmpty()) {
                            recommendationProductListName = recommendationProductListName + "|" + dynamicItem.score
                        }
                    }


//                if (dynamicItem.timeFrame.isNullOrBlank() && dynamicItem.score.isNullOrBlank()) {
//                    recommendationProductListName = dynamicItem.recommendationSource + "|null"
//                } else if (!dynamicItem.timeFrame.isNullOrBlank() && dynamicItem.score.isNullOrBlank()) {
//                    recommendationProductListName = dynamicItem.recommendationSource + "|" + dynamicItem.timeFrame
//                } else if (dynamicItem.timeFrame.isNullOrBlank() && !dynamicItem.score.isNullOrBlank()) {
//                    recommendationProductListName = dynamicItem.recommendationSource + "|null|" + dynamicItem.score
//                } else if (!dynamicItem.timeFrame.isNullOrBlank() && !dynamicItem.score.isNullOrBlank()) {
//                    recommendationProductListName = dynamicItem.recommendationSource + "|" + dynamicItem.timeFrame + "|" + dynamicItem.score
//                }

                    if (!recommendationProductListName.isNullOrBlank()) {
                        val recommendedProductsString = Gson().toJson(response.params?.recommendedProducts)
                        var recommendedProductsJSON = JSONObject(recommendedProductsString)
                        var recommendedProductItemArray: JSONArray? = recommendedProductsJSON.getJSONArray(recommendationProductListName)

                        var i = 0
                        for (k in 0 until recommendedProductItemArray!!.length()) {
                            var productModel = parseJsonToProductModel(recommendedProductItemArray!!.getJSONObject(k))
                            if (!isProductExistInGlobalProductList(productModel)) {
                                recommendationModel.products?.add(productModel)
                                i = i + 1
                            } else {
                                if (i > 0) {
                                    i = i - 1
                                }
                            }

                            if (i == dynamicItem.itemCount!!) {
                                break
                            }
                        }
                    }
                }
            }

            reformatedReponseModelList.add(recommendationModel)
        }

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
        if(productJson.has("priceText")){
            productRecommendationModel.priceText = productJson.getString("priceText")
        }
        if(productJson.has("oldPriceText")){
            productRecommendationModel.oldPriceText = productJson.getString("oldPriceText")
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
        if(productJson.has("language")){
            productRecommendationModel.language = productJson.getString("language")
        }
        if(productJson.has("currency")){
            productRecommendationModel.currency = productJson.getString("currency")
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
        if(productJson.has("category")) {
            var categoryJSONArray = productJson.getJSONArray("category")
            var category = ArrayList<String>()
            if(categoryJSONArray != null) {
                for(i in 0 until categoryJSONArray!!.length()) {
                    category.add(categoryJSONArray.getString(i))
                }
            }
            productRecommendationModel.category = category
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



        if(productJson.has("params")){
            var obj =  productJson.getString("params");
            var json  = JSONObject(obj);
            var paramsMap: Map<String, Any> =Gson().fromJson(obj, object : TypeToken<Map<String, Any>>() {}.type)
            productRecommendationModel.params =paramsMap
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