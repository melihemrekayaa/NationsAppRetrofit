package com.mobile.nationalized.data.repository

import com.mobile.nationalized.data.datasource.CountryDataSource
import com.mobile.nationalized.data.entity.Country


class CountryRepository(var cds: CountryDataSource) {

    suspend fun getAllCountries(): List<Country> = cds.getAllCountries()

    suspend fun insertAll(vararg countries : Country) : List<Long> = cds.insertAll(*countries)

    suspend fun getCountry(countryId : Int) : Country = cds.getCountry(countryId)

    suspend fun deleteAllCountries() = cds.deleteAllCountries()

}