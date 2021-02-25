package com.uhi5d.spotibud.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("country") val country : String,
    @SerializedName("display_name") val display_name : String,
    @SerializedName("email") val email : String,
    @SerializedName("explicit_content") val explicit_content : Explicit_content,
    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("followers") val followers : Followers,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("images") val images : List<Image>,
    @SerializedName("product") val product : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class Explicit_content (

    @SerializedName("filter_enabled") val filter_enabled : Boolean,
    @SerializedName("filter_locked") val filter_locked : Boolean
)


