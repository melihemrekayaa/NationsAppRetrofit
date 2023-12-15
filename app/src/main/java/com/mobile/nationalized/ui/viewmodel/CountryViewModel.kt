package com.mobile.nationalized.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.nationalized.data.entity.Country

class CountryViewModel : ViewModel(){


    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country = Country("Turkey","Asia","Ankara","TRY","Turkish","www.ss.com")
        countryLiveData.value = country
    }
}