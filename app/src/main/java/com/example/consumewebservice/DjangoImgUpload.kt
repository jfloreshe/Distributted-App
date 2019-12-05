package com.example.consumewebservice
/*
SERVICIO REST API
 */

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DjangoImgUpload {


    @Multipart
    @POST("upload/")
    fun uploadFile(@Part file: MultipartBody.Part): Call<RequestBody>

    companion object {

        val DJANGO_SITE = globalServer +"/image/"
    }



}