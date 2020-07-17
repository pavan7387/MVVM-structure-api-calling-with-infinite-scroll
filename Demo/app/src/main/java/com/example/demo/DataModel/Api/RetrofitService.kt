package com.example.demo.DataModel.Api

import android.telecom.Call
import com.example.demo.Model.ModelPage
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService{

    @GET("/3/gallery/search/{page_no}")
    fun getImageData(@Header("Authorization") token :String, @Path(value = "page_no", encoded = true) userId :String,
                     @Query(value = "q", encoded = true) searchkey :String)  : retrofit2.Call<ModelPage>

}