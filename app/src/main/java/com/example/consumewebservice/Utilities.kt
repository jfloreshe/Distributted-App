package com.example.consumewebservice

import android.util.Log

import java.io.File

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val globalServer = "http://192.168.1.61:8000"
class ImageRest{
    fun uploadFoto(filepath: String) {

        val imageFile = File(filepath)

        val retrofit = Retrofit.Builder()
            .baseUrl(DjangoImgUpload.DJANGO_SITE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val postApi = retrofit.create(DjangoImgUpload::class.java!!)


        val requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile)
        val multiPartBody = MultipartBody.Part
            .createFormData("model_pic", imageFile.name, requestBody)


        val call = postApi.uploadFile(multiPartBody)

        call.enqueue(object : Callback<RequestBody> {
            override fun onResponse(call: Call<RequestBody>, response: Response<RequestBody>) {
                Log.d("good", "good")

            }

            override fun onFailure(call: Call<RequestBody>, t: Throwable) {
                Log.d("fail", "fail")
            }
        })
    }

}