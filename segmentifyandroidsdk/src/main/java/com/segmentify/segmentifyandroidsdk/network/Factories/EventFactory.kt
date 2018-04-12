package com.segmentify.segmentifyandroidsdk.network.Factories

import com.segmentify.segmentifyandroidsdk.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface EventFactory {

        //POST Cart Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendPageView(@Body pageModel: PageModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>

        //POST Checkout Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendPurchase(@Body checkoutModel: CheckoutModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>

        //POST Custom Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendCustomEvent(@Body customEventModel: CustomEventModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>

        //POST Cart Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendAddOrRemoveBasket(@Body basketModel: BasketModel,@Query("apiKey") apiKey : String): Call<Any>

        //POST ProductView Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendProductView(@Body productModel: ProductModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>

        //POST UserLoginLogout Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendUserRegister(@Body userModel: UserModel,@Query("apiKey") apiKey : String): Call<Any>

        //POST UserRegisterUpdate Object with steps as defined in documentation
        @POST("/add/events/v1.json")
        fun sendChangeUser(@Body userChangeModel: UserChangeModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>

        //POST Interaction Object with types as defined in documentation
        @POST("/add/events/v1.json")
        fun postInteractionEvent(@Body interactionModel: InteractionModel,@Query("apiKey") apiKey : String): Call<EventResponseModel>
}