package com.mahmoudshaaban.titansapp.api.main

import androidx.lifecycle.LiveData
import com.mahmoudshaaban.titansapp.api.GenericResponse
import com.mahmoudshaaban.titansapp.models.AccountProperties
import com.mahmoudshaaban.titansapp.util.GenericApiResponse
import retrofit2.http.*

interface OpenApiMainService {


    @GET("account/properties")
    fun getAccountProperties(
        @Header("Authorization") authroziation : String
    ): LiveData<GenericApiResponse<AccountProperties>>


    @PUT("account/properties/update")
    @FormUrlEncoded

    fun saveAccountProperties(
        @Header("Authorization") authroziation: String,
        @Field("email") email : String ,
        @Field("username") username : String
    ) : LiveData<GenericApiResponse<GenericResponse>>
}