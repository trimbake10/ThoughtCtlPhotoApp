package com.thoughtctlphotoapp.model

data class GallaryResponse(
    val `data`: List<Data>,
    val status: Int,
    val success: Boolean
)