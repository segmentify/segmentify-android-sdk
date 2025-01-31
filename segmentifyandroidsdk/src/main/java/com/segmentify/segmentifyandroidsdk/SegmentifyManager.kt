package com.segmentify.segmentifyandroidsdk

import android.content.Context
import com.segmentify.segmentifyandroidsdk.controller.EventController
import com.segmentify.segmentifyandroidsdk.controller.KeyController
import com.segmentify.segmentifyandroidsdk.controller.PushController
import com.segmentify.segmentifyandroidsdk.model.*
import com.segmentify.segmentifyandroidsdk.model.faceted.SearchFacetedPageModel
import com.segmentify.segmentifyandroidsdk.model.faceted.SearchFacetedResponseModel
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.utils.ClientPreferences
import com.segmentify.segmentifyandroidsdk.utils.Constant
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyLogger
import java.util.*


object SegmentifyManager {

    var clientPreferences: ClientPreferences? = null
    var configModel = ConfigModel()
    var segmentifyObject = SegmentifyObject()
    var clickedBanners: LinkedList<ClickedBannerObject> = LinkedList()

    private fun addClickedBanner(banner: BannerOperationsModel) {

        clickedBanners.forEach {
            if (it.group.equals(banner.group) &&
                    it.title.equals(banner.title) &&
                    it.order!!.equals(banner.order)) {
                return
            }
        }

        if (clickedBanners.size > 20) {
            clickedBanners.removeFirst()
        }

        val cbo = ClickedBannerObject()
        cbo.group = banner.group
        cbo.order = banner.order
        cbo.title = banner.title


        clickedBanners.add(cbo)
    }

    fun setSessionKeepSecond(sessionKeepSecond: Int) {
        clientPreferences?.setSessionKeepSeconds(sessionKeepSecond)
    }

    fun setConfig(apiKey: String, dataCenterUrl: String, subDomain: String) {
        this.configModel.apiKey = apiKey
        this.configModel.dataCenterUrl = dataCenterUrl
        this.configModel.subDomain = subDomain
        setBaseApiUrl()
        ConnectionManager.rebuildServices()
    }

    fun setPushConfig(dataCenterUrlPush: String) {
        this.configModel.dataCenterUrlPush = dataCenterUrlPush
    }

    fun logStatus(isVisible: Boolean) {
        clientPreferences?.setLogVisible(isVisible)
    }

    fun config(context: Context, appKey: String, dataCenterUrl: String, subDomain: String) {

        if (appKey.isBlank() || dataCenterUrl.isBlank() || subDomain.isBlank()) {
            SegmentifyLogger.printErrorLog("Api is not initialized, you can not enter null or empty parameter to config, please recheck your config parameters")
            return
        }

        clientPreferences = ClientPreferences(context)
        clientPreferences?.getSessionKeepSeconds()!!
        this.configModel.apiKey = appKey
        this.configModel.dataCenterUrl = dataCenterUrl
        this.configModel.subDomain = subDomain
        setBaseApiUrl()

        if (clientPreferences?.getSessionId().isNullOrBlank()) {
            if (clientPreferences?.getUserId().isNullOrBlank())
                KeyController.getUserIdAndSessionId()
            else
                KeyController.getSessionId()
        }
        KeyController.controlSessionTimeout()
    }

    private fun setBaseApiUrl() {
        configModel.dataCenterUrl?.let { clientPreferences?.setApiUrl(it) }
    }

    fun sendPageView(pageModel: PageModel, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        if (pageModel.category.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("you must fill category before accessing sendPageView event method")
            return
        }

        if (pageModel.subCategory != null) {
            pageModel.subCategory = pageModel.subCategory
        }

        pageModel.eventName = Constant.pageViewEventName
        EventController.sendPageView(pageModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendSearchPageView(pageModel: SearchPageModel, segmentifyCallback: SegmentifyCallback<SearchResponseModel>) {
        pageModel.eventName = Constant.searchViewEventName
        EventController.sendSearchView(pageModel, object : SegmentifyCallback<SearchResponseModel> {
            override fun onDataLoaded(data: SearchResponseModel) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }
   
    fun sendFacetedSearchPageView(pageModel: SearchFacetedPageModel, segmentifyCallback: SegmentifyCallback<SearchFacetedResponseModel>){
        pageModel.eventName = Constant.searchViewEventName
        EventController.sendFacetedSearchView(pageModel, object : SegmentifyCallback<SearchFacetedResponseModel>{
            override fun onDataLoaded(data: SearchFacetedResponseModel) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    /* bannerify events */

    fun sendBannerImpressionEvent(bannerOperationsModel: BannerOperationsModel) {
        if (bannerOperationsModel == null) {
            SegmentifyLogger.printErrorLog("you must fill banneroperationsmodel before accessing sendBannerOperationEvent method")
            return
        }
        bannerOperationsModel.eventName = Constant.bannerOperationsEventName
        bannerOperationsModel.bannerType = Constant.bannerImpressionStep

        EventController.sendBannerOperations(bannerOperationsModel)
    }

    fun sendBannerClickEvent(bannerOperationsModel: BannerOperationsModel) {
        if (bannerOperationsModel == null) {
            SegmentifyLogger.printErrorLog("you must fill banneroperationsmodel before accessing bannerGroupViewEvent method")
            return
        }

        addClickedBanner(bannerOperationsModel)
        bannerOperationsModel.eventName = Constant.bannerOperationsEventName
        bannerOperationsModel.bannerType = Constant.bannerClickStep

        EventController.sendBannerOperations(bannerOperationsModel)
    }

    fun sendBannerUpdateEvent(bannerOperationsModel: BannerOperationsModel) {
        if (bannerOperationsModel == null) {
            SegmentifyLogger.printErrorLog("you must fill banneroperationsmodel before accessing bannerGroupViewEvent method")
            return
        }
        bannerOperationsModel.eventName = Constant.bannerOperationsEventName
        bannerOperationsModel.bannerType = Constant.bannerUpdateStep

        EventController.sendBannerOperations(bannerOperationsModel)
    }

    fun sendBannerGroupViewEvent(bannerGroupViewModel: BannerGroupViewModel) {
        if (bannerGroupViewModel == null) {
            SegmentifyLogger.printErrorLog("you must fill bannergroupviewmodel before accessing bannerGroupViewEvent method")
            return
        }
        bannerGroupViewModel.eventName = Constant.bannerGroupViewEventName

        EventController.sendBannerGroupView(bannerGroupViewModel)
    }

    fun sendInternalBannerGroupEvent(bannerGroupViewModel: BannerGroupViewModel) {
        if (bannerGroupViewModel == null) {
            SegmentifyLogger.printErrorLog("you must fill type before accessing InternalBannerGroupEvent method")
            return
        }
        bannerGroupViewModel.eventName = Constant.internalBannerGroupEventName

        EventController.sendInternalBannerGroup(bannerGroupViewModel)
    }

    fun sendCustomEvent(customEventModel: CustomEventModel, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        if (customEventModel == null || customEventModel?.type.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("you must fill type before accessing sendCustomEvent event method")
            return
        }
        customEventModel.eventName = Constant.customEventName

        EventController.sendCustomEvent(customEventModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendCustomEvent(type: String, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        var customEventModel = CustomEventModel()
        customEventModel.eventName = Constant.customEventName
        customEventModel.type = type

        EventController.sendCustomEvent(customEventModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendProductView(productModel: ProductModel, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        productModel.eventName = Constant.productViewEventName
        if (productModel?.productId == null) {
            SegmentifyLogger.printErrorLog("You must fill productId before accessing sendProductView event")
            return
        }
        if (productModel?.title == null) {
            SegmentifyLogger.printErrorLog("You must fill title before accessing sendProductView event method")
            return
        }
        if (productModel?.url == null) {
            SegmentifyLogger.printErrorLog("You must fill url before accessing sendProductView event method")
            return
        }
        if (productModel?.image == null) {
            SegmentifyLogger.printErrorLog("You must fill image before accessing sendProductView event method")
            return
        }
        if (productModel?.category != null && productModel?.categories != null) {
            SegmentifyLogger.printErrorLog("You can not both fill category and categpries parameters")
            return
        }
        if (productModel?.category == null && productModel?.categories == null) {
            SegmentifyLogger.printErrorLog("You should fill one of category and categories parameters")
            return
        }
        if (productModel.price == null) {
            SegmentifyLogger.printErrorLog("You must fill price before accessing sendProductView event method")
            return
        }

        productModel.activeBanners = clickedBanners


        EventController.sendProductView(productModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendProductView(productId: String, title: String, categories: ArrayList<String>, price: Double,
                        brand: String?, stock: Boolean?, url: String, image: String, imageXS: String?,
                        imageS: String?, imageM: String?, imageL: String?, imageXL: String?, gender: String?,
                        colors: ArrayList<String>?, sizes: ArrayList<String>?, labels: ArrayList<String>?, noUpdate: Boolean?, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        var productModel = ProductModel()
        productModel.eventName = Constant.productViewEventName

        if (productId == null) {
            SegmentifyLogger.printErrorLog("You must fill productId before accessing sendProductView event")
            return
        }
        if (title == null) {
            SegmentifyLogger.printErrorLog("You must fill title before accessing sendProductView event method")
            return
        }
        if (url == null) {
            SegmentifyLogger.printErrorLog("You must fill url before accessing sendProductView event method")
            return
        }
        if (image == null) {
            SegmentifyLogger.printErrorLog("You must fill image before accessing sendProductView event method")
            return
        }
        if (categories == null) {
            SegmentifyLogger.printErrorLog("You must fill categories before accessing sendProductView event method")
            return
        }
        if (price == null) {
            SegmentifyLogger.printErrorLog("You must fill price before accessing sendProductView event method")
            return
        }

        productModel.productId = productId
        productModel.title = title
        productModel.url = url
        productModel.image = image
        productModel.categories = categories
        productModel.price = price
        productModel.brand = brand
        productModel.inStock = stock
        productModel.imageXS = imageXS
        productModel.imageS = imageS
        productModel.imageM = imageM
        productModel.imageL = imageL
        productModel.imageXL = imageXL
        productModel.gender = gender
        productModel.colors = colors
        productModel.sizes = sizes
        productModel.labels = labels
        productModel.noUpdate = noUpdate
        productModel.activeBanners = clickedBanners

        EventController.sendProductView(productModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendCustomerInformation(checkoutModel: CheckoutModel, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        checkoutModel.eventName = Constant.checkoutEventName
        checkoutModel.checkoutStep = Constant.customerInformationStep

        if (checkoutModel.totalPrice == null) {
            SegmentifyLogger.printErrorLog("You must fill totalPrice before accessing sendCustomerInformation event")
            return
        }

        if (checkoutModel.productList == null) {
            SegmentifyLogger.printErrorLog("you must fill productList before accessing sendCustomerInformation event method")
            return
        }

        EventController.sendCheckout(checkoutModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendCustomerInformation(totalPrice: Double, productList: ArrayList<ProductModel>, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        var checkoutModel = CheckoutModel()
        checkoutModel.eventName = Constant.checkoutEventName
        checkoutModel.checkoutStep = Constant.customerInformationStep

        if (totalPrice == null) {
            SegmentifyLogger.printErrorLog("You must fill totalPrice before accessing sendCustomerInformation event")
            return
        }

        if (productList == null) {
            SegmentifyLogger.printErrorLog("you must fill productList before accessing sendCustomerInformation event method")
            return
        }

        checkoutModel.totalPrice = totalPrice
        checkoutModel.productList = productList

        EventController.sendCheckout(checkoutModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendViewBasket(totalPrice: Double, productList: ArrayList<ProductModel>, currency: String?, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        var checkoutModel = CheckoutModel()
        checkoutModel.eventName = Constant.checkoutEventName
        checkoutModel.checkoutStep = Constant.viewBasketStep

        if (totalPrice == null) {
            SegmentifyLogger.printErrorLog("You must fill totalPrice before accessing sendViewBasket event")
        }

        if (productList == null) {
            SegmentifyLogger.printErrorLog("you must fill productList before accessing sendViewBasket event method")
        }

        checkoutModel.totalPrice = totalPrice
        checkoutModel.productList = productList

        if (currency != null) {
            checkoutModel.currency = currency
        }

        EventController.sendCheckout(checkoutModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendViewBasket(checkoutModel: CheckoutModel, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        checkoutModel.eventName = Constant.checkoutEventName
        checkoutModel.checkoutStep = Constant.viewBasketStep
        if (checkoutModel.totalPrice == null) {
            SegmentifyLogger.printErrorLog("You must fill totalPrice before accessing sendViewBasket event")
        }

        if (checkoutModel.productList == null) {
            SegmentifyLogger.printErrorLog("you must fill productList before accessing sendViewBasket event method")
        }

        EventController.sendCheckout(checkoutModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendPaymentInformation(checkoutModel: CheckoutModel, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        checkoutModel.eventName = Constant.checkoutEventName
        checkoutModel.checkoutStep = Constant.paymentInformationStep
        if (checkoutModel.totalPrice == null) {
            SegmentifyLogger.printErrorLog("You must fill totalPrice before accessing sendPaymentInformation event")
            return
        }

        if (checkoutModel.productList == null) {
            SegmentifyLogger.printErrorLog("you must fill productList before accessing sendPaymentInformation event method")
            return
        }

        EventController.sendCheckout(checkoutModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendPaymentInformation(totalPrice: Double, productList: ArrayList<ProductModel>, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        var checkoutModel = CheckoutModel()
        checkoutModel.eventName = Constant.checkoutEventName
        checkoutModel.checkoutStep = Constant.paymentInformationStep
        if (checkoutModel.totalPrice == null) {
            SegmentifyLogger.printErrorLog("You must fill totalPrice before accessing sendPaymentInformation event")
            return
        }

        if (checkoutModel.productList == null) {
            SegmentifyLogger.printErrorLog("you must fill productList before accessing sendPaymentInformation event method")
            return
        }

        checkoutModel.totalPrice = totalPrice
        checkoutModel.productList = productList

        EventController.sendCheckout(checkoutModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendAddOrRemoveBasket(basketModel: BasketModel) {
        basketModel.eventName = Constant.basketOperationsEventName

        if (basketModel.step == null) {
            SegmentifyLogger.printErrorLog("You must fill step before accessing sendAddOrRemoveBasket event method")
            return
        }
        if (basketModel.productId == null) {
            SegmentifyLogger.printErrorLog("You must fill productId before accessing sendAddOrRemoveBasket event method")
            return
        }
        if (basketModel.quantity == null) {
            SegmentifyLogger.printErrorLog("You must fill quantity before accessing sendAddOrRemoveBasket event method")
            return
        }

        basketModel.activeBanners = clickedBanners

        EventController.sendAddOrRemoveBasket(basketModel)
    }

    fun sendAddOrRemoveBasket(basketStep: String, productId: String, quantity: Double?, price: Double?) {
        var basketModel = BasketModel()
        basketModel.eventName = Constant.basketOperationsEventName

        if (basketStep == null) {
            SegmentifyLogger.printErrorLog("You must fill step before accessing sendAddOrRemoveBasket event method")
            return
        }
        if (productId == null) {
            SegmentifyLogger.printErrorLog("You must fill productId before accessing sendAddOrRemoveBasket event method")
            return
        }
        if (quantity == null) {
            SegmentifyLogger.printErrorLog("You must fill quantity before accessing sendAddOrRemoveBasket event method")
            return
        }
        if (price == null) {
            SegmentifyLogger.printErrorLog("You must fill price before accessing sendAddOrRemoveBasket event method")
            return
        }

        basketModel.step = basketStep
        basketModel.productId = productId
        basketModel.quantity = quantity
        basketModel.price = price
        basketModel.activeBanners = clickedBanners


        EventController.sendAddOrRemoveBasket(basketModel)
    }

    fun sendPurchase(checkoutModel: CheckoutModel, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        checkoutModel.eventName = Constant.checkoutEventName
        checkoutModel.checkoutStep = Constant.paymentPurchaseStep

        if (checkoutModel.totalPrice == null) {
            SegmentifyLogger.printErrorLog("You must fill totalPrice before accessing sendPurchase event")
            return
        }
        if (checkoutModel.productList == null || checkoutModel.productList?.size == 0) {
            SegmentifyLogger.printErrorLog("You must fill productList before accessing sendPurchase event method")
            return
        }

        checkoutModel.activeBanners = clickedBanners

        EventController.sendCheckout(checkoutModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendPurchase(totalPrice: Double, productList: ArrayList<ProductModel>, orderNo: String?, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        var checkoutModel = CheckoutModel()
        checkoutModel.eventName = Constant.checkoutEventName
        checkoutModel.checkoutStep = Constant.paymentPurchaseStep
        checkoutModel.totalPrice = totalPrice
        checkoutModel.productList = productList
        checkoutModel.activeBanners = clickedBanners
        checkoutModel.orderNo = orderNo

        EventController.sendCheckout(checkoutModel, object : SegmentifyCallback<ArrayList<RecommendationModel>> {
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendImpression(instanceId: String, interactionId: String) {
        var interactionModel = InteractionModel()
        interactionModel.eventName = Constant.interactionEventName
        interactionModel.type = Constant.impressionStep
        interactionModel.instanceId = instanceId
        interactionModel.interactionId = interactionId
        EventController.sendInteractionEvent(interactionModel)

    }

    fun sendImpression(interactionModel: InteractionModel) {
        if (interactionModel.instanceId.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill instanceId before sending interaction event")
            return
        }
        if (interactionModel.interactionId.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill interactionId before sending interaction event")
            return
        }

        interactionModel.eventName = Constant.interactionEventName
        interactionModel.type = Constant.impressionStep

        EventController.sendInteractionEvent(interactionModel)
    }

    fun sendWidgetView(instanceId: String, interactionId: String) {
        var interactionModel = InteractionModel()
        interactionModel.eventName = Constant.interactionEventName
        interactionModel.type = Constant.widgetViewStep
        interactionModel.instanceId = instanceId
        interactionModel.interactionId = interactionId

        EventController.sendInteractionEvent(interactionModel)
    }

    fun sendWidgetView(interactionModel: InteractionModel) {
        if (interactionModel.instanceId.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill instanceId before sending interaction event")
            return
        }
        if (interactionModel.interactionId.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill interactionId before sending interaction event")
            return
        }

        interactionModel.eventName = Constant.interactionEventName
        interactionModel.type = Constant.widgetViewStep

        EventController.sendInteractionEvent(interactionModel)
    }

    fun sendClickView(instanceId: String, interactionId: String) {
        var interactionModel = InteractionModel()
        interactionModel.eventName = Constant.interactionEventName
        interactionModel.type = Constant.clickStep
        interactionModel.instanceId = instanceId
        interactionModel.interactionId = interactionId

        EventController.sendInteractionEvent(interactionModel)
    }

    fun sendClickView(interactionModel: InteractionModel) {
        if (interactionModel.instanceId.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill instanceId before sending interaction event")
            return
        }
        if (interactionModel.interactionId.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill interactionId before sending interaction event")
            return
        }

        interactionModel.eventName = Constant.interactionEventName
        interactionModel.type = Constant.clickStep

        EventController.sendInteractionEvent(interactionModel)
    }

    fun sendSearchClickView(instanceId: String, interactionId: String) {
        var interactionModel = InteractionModel()
        interactionModel.eventName = Constant.interactionEventName
        interactionModel.type = Constant.searchStep
        interactionModel.instanceId = instanceId
        interactionModel.interactionId = interactionId

        EventController.sendInteractionEvent(interactionModel)
    }

    fun sendSearchClickView(interactionModel: InteractionModel) {
        if (interactionModel.instanceId.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill instanceId before sending interaction event")
            return
        }
        if (interactionModel.interactionId.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill interactionId before sending interaction event")
            return
        }

        interactionModel.eventName = Constant.interactionEventName
        interactionModel.type = Constant.searchStep

        EventController.sendInteractionEvent(interactionModel)
    }

    fun sendUserRegister(userModel: UserModel) {
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.registerStep

        if (userModel.email == null && userModel.username == null) {
            SegmentifyLogger.printErrorLog("You must fill email or email before accessing sendUserLogout event")
            return
        }

        EventController.sendUserOperation(userModel)

    }

    fun sendUserRegister(username: String?, fullName: String?, email: String?, mobilePhone: String?, gender: String?, age: String?, birthdate: String?) {
        var userModel = UserModel()
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.registerStep
        userModel.username = username
        userModel.fullName = fullName
        userModel.email = email
        userModel.mobilePhone = mobilePhone
        userModel.gender = gender
        userModel.age = age
        userModel.birthDate = birthdate

        EventController.sendUserOperation(userModel)
    }

    fun sendUserLogin(userModel: UserModel) {
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.signInStep

        if (userModel?.username.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill username before accessing change user event")
            return
        }

        if (userModel?.email.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill email before accessing change user event")
            return
        }

        EventController.sendUserOperation(userModel)
    }

    fun sendUserLogin(username: String?, email: String?) {
        var userModel = UserModel()
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.signInStep
        userModel.username = username
        userModel.email = email

        EventController.sendUserOperation(userModel)
    }

    fun sendUserLogout(username: String?, email: String?) {
        var userModel = UserModel()
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.logoutStep
        userModel.username = username
        userModel.email = email

        EventController.sendUserOperation(userModel)
    }

    fun sendUserUpdate(userModel: UserModel) {
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.updateUserStep

        if (userModel.email.isNullOrBlank() || userModel.username.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill username or email before accessing sendUserUpdate event")
            return
        }

        //Email ve userName kaydedebilmek için
        //clientPreferences.setUserName(userModel.username.toString())
        //clientPreferences?.setEmail(userModel.email.toString())

        EventController.sendUserOperation(userModel)
    }

    fun sendUserUpdate(username: String?, fullName: String?, email: String?, mobilePhone: String?, gender: String?, age: String?, birthdate: String?, isRegistered: String?, isLogin: Boolean?) {
        var userModel = UserModel()
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.updateUserStep
        userModel.username = username
        userModel.fullName = fullName
        userModel.email = email
        userModel.mobilePhone = mobilePhone
        userModel.gender = gender
        userModel.age = age
        userModel.birthDate = birthdate
        userModel.isRegistered = isRegistered
        userModel.isLogin = isLogin


        //Email ve userName kaydedebilmek için

        clientPreferences?.setUserName(username.toString())
        clientPreferences?.setEmail(email.toString())


        EventController.sendUserOperation(userModel)
    }

    fun sendChangeUser(userChangeModel: UserChangeModel) {
        userChangeModel.eventName = Constant.userChangeEventName

        if (userChangeModel?.userId.isNullOrBlank()) {
            SegmentifyLogger.printErrorLog("You must fill userId before accessing change user event")
            return
        }
        userChangeModel.oldUserId = clientPreferences?.getUserId()

        if (clientPreferences?.getUserId() != userChangeModel.userId) {
            EventController.sendChangeUser(userChangeModel, object : SegmentifyCallback<Boolean> {
                override fun onDataLoaded(isSuccessful: Boolean) {
                    if (isSuccessful) {
                        userChangeModel.userId?.let { clientPreferences?.setUserId(it) }
                    }
                }
            })
        }
    }

    fun setAdvertisingIdentifier(adIdentifier: String?) {
        segmentifyObject.advertisingIdentifier = adIdentifier
    }

    fun setAppVersion(appVersion: String?) {
        segmentifyObject.appVersion = appVersion
    }

    fun sendNotification(notificationModel: NotificationModel) {
        if (notificationModel.type == NotificationType.PERMISSION_INFO) {

            if (notificationModel.deviceToken.isNullOrEmpty()) {
                SegmentifyLogger.printErrorLog("You must fill deviceToken before accessing notification event")
                return
            }
        }
        PushController.sendNotification(notificationModel)
    }


    fun sendNotificationInteraction(notificationModel: NotificationModel) {

        if (notificationModel.type == NotificationType.VIEW) {
            if (notificationModel.instanceId.isNullOrEmpty()) {
                SegmentifyLogger.printErrorLog("You must fill instanceId before accessing notification view event")
                return
            }
        }

        if (notificationModel.type == NotificationType.CLICK) {
            if (notificationModel.instanceId.isNullOrEmpty() && notificationModel.productId.isNullOrEmpty()) {
                SegmentifyLogger.printErrorLog("You must fill deviceToken before accessing notification click event")
                return
            } else {
                clientPreferences?.setPushCampaignId(notificationModel.instanceId!!)
                clientPreferences?.setPushCampaignProductId(notificationModel.productId!!)
                sendClickView(notificationModel.instanceId!!, notificationModel.productId!!);
            }
        }
        PushController.sendNotificationInteraction(notificationModel)
    }

    fun getTrackingParameters(): UtmModel {
        return UtmModel()
    }

    fun clearLastSearchHistory(userModel: UserModel) {
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.deleteLastSearchStep

        EventController.sendUserOperation(userModel)
    }

    fun clearLastSearchHistory(lastSearchDeletedKeyword: String?) {
        var userModel = UserModel()
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.deleteLastSearchStep
        userModel.lastSearchDeletedKeywords = lastSearchDeletedKeyword

        EventController.sendUserOperation(userModel)
    }

}
