package com.mobile.nationalized.data.datasource

import com.mobile.nationalized.data.entity.Country
import com.mobile.nationalized.room.CountryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryDataSource(var cdao: CountryDao) {

    suspend fun getAllCountries(): List<Country> =
        withContext(Dispatchers.IO){
            return@withContext cdao.getAllCountries()
        }

    suspend fun insertAll(vararg countries : Country) : List<Long> =
        withContext(Dispatchers.IO){
            return@withContext cdao.insertAll(*countries)
        }

    suspend fun getCountry(countryId : Int) : Country =
        withContext(Dispatchers.IO){
            return@withContext cdao.getCountry(countryId)
        }

    suspend fun deleteAllCountries() =
        withContext(Dispatchers.IO){
            return@withContext cdao.deleteAllCountries()
        }



}