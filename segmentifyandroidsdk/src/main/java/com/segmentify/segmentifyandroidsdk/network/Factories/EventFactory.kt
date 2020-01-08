package com.segmentify.segmentifyandroidsdk.network.Factories

import com.segmentify.segmentifyandroidsdk.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface EventFactory {

        //POST PageModel Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendPageView(@Body pageModel: PageModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>

        //POST CheckoutModel Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendPurchase(@Body checkoutModel: CheckoutModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>

        //POST CustomEventModel Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendCustomEvent(@Body customEventModel: CustomEventModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>

        //POST BasketModel Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendAddOrRemoveBasket(@Body basketModel: BasketModel,@Query("apiKey") apiKey : String): Call<Any>

        //POST ProductModel Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendProductView(@Body productModel: ProductModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>

        //POST UserModel Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendUserOperation(@Body userModel: UserModel,@Query("apiKey") apiKey : String): Call<Any>

        //POST UserModel Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendChangeUser(@Body userChangeModel: UserChangeModel,@Query("apiKey") apiKey : String): Call<Any>

        //POST Interaction Object with types as defined in documentation
        @POST("/add/events/v1.json")
        fun sendInteractionEvent(@Body interactionModel: InteractionModel,@Query("apiKey") apiKey : String): Call<Any>

        //POST Banner Operation Object with types as defined in documentation
        @POST("/sync/events/v1.json")
        fun sendBannerOperations(@Body bannerOperationsModel: BannerOperationsModel, @Query("apiKey") apiKey : String): Call<Any>

        //POST Banner Group View Object with types as defined in documentation
        @POST("/add/events/v1.json")
        fun sendBannerGroupView(@Body bannerGroupViewModel: BannerGroupViewModel,@Query("apiKey") apiKey : String): Call<Any>

        //POST Banner Internal Group is an internal event
        @POST("/add/events/v1.json")
        fun sendInternalBannerGroup(@Body bannerGroupViewModel: BannerGroupViewModel,@Query("apiKey") apiKey : String): Call<Any>

        //POST PageModel Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendSearchView(@Body pageModel: SearchPageModel,@Query("apiKey") apiKey : String): Call<SearchEventResponseModel>

}