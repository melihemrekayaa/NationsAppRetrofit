package com.mobile.nationalized.data.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Country(
     @ColumnInfo("name")
     @SerializedName("name")
     var countryName: String?,
     @ColumnInfo("region")
     @SerializedName("region")
     var countryRegion: String?,
     @ColumnInfo("capital")
     @SerializedName("capital")
     var countryCapital: String?,
     @ColumnInfo("currency")
     @SerializedName("currency")
     var countryCurrency: String?,
     @ColumnInfo("language")
     @SerializedName("language")
     var countryLanguage: String?,
     @ColumnInfo("flag")
     @SerializedName("flag")
     var imageUrl: String?



    )
{
     @PrimaryKey(autoGenerate = true)
     var uuid: Int = 0
}