package com.mobile.nationalized.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.nationalized.data.entity.Country
import com.mobile.nationalized.service.CountryAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class FeedViewModel() : ViewModel() {
    var countries = MutableLiveData<List<Country>>()
    var countryError = MutableLiveData<Boolean>()
    var countryLoading = MutableLiveData<Boolean>()

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()

    fun refreshData(){
        getDataFromAPI()
    }

    private fun getDataFromAPI(){
        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        countries.value = t
                        countryLoading.value = false
                        countryError.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

}