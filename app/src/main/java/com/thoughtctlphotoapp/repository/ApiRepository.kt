package com.thoughtctlphotoapp.repository

import com.thoughtctlphotoapp.network.ApiService
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getPhotoList(query:String)=apiService.getPhotoList(query)
}