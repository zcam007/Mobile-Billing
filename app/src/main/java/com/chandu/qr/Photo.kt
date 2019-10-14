package com.chandu.qr

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("filename")
    var filename: String? = null
)