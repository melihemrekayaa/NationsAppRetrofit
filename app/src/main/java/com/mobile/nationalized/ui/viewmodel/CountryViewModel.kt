package com.mobile.nationalized.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.nationalized.data.entity.Country
import com.mobile.nationalized.data.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(application: Application,var crepo: CountryRepository): BaseViewModel(application){


    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int){
        launch {
            val country = crepo.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}