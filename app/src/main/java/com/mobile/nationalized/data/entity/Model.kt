package com.mobile.nationalized.data.entity


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Country(
     @SerializedName("name")
     var countryName: String?,
     @SerializedName("region")
     var countryRegion: String?,
     @SerializedName("capital")
     var countryCapital: String?,
     @SerializedName("currency")
     var countryCurrency: String?,
     @SerializedName("language")
     var countryLanguage: String?,
     @SerializedName("flag")
     var imageUrl: String?

    ): Serializable
{

}