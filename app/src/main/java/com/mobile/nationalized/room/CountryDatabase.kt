package com.mobile.nationalized.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobile.nationalized.data.entity.Country

@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase : RoomDatabase(){
    abstract fun getCountryDao() : CountryDao
}