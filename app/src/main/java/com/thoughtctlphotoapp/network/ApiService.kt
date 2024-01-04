package com.thoughtctlphotoapp.network

import com.thoughtctlphotoapp.model.GallaryResponse
import com.thoughtctlphotoapp.utils.Constants
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @Headers(
        "Cache-Control: max-age=60",
        "Authorization: Client-ID ${Constants.CLIENT_ID}"
    )
    @GET("search/top")
    fun getPhotoList(
       @Query("q") query: String
    ): Observable<GallaryResponse>
}