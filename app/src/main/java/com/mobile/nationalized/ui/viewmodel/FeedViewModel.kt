package com.mobile.nationalized.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.mobile.nationalized.data.entity.Country
import com.mobile.nationalized.data.repository.CountryRepository
import com.mobile.nationalized.service.CountryAPIService
import com.mobile.nationalized.util.CustomSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel  @Inject constructor (application: Application,var crepo: CountryRepository) : BaseViewModel(application) {
    var countries = MutableLiveData<List<Country>>()
    var countryError = MutableLiveData<Boolean>()
    var countryLoading = MutableLiveData<Boolean>()

    private var countryApiService = CountryAPIService()
    private var disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L
    fun refreshData(){
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQL()
        }else{
            getDataFromAPI()
        }
    }

     fun refreshFromAPI(){
         getDataFromAPI()
     }

     fun getDataFromSQL(){
        launch {
            val countries = crepo.getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries From SQL", Toast.LENGTH_LONG).show()
            
        }
    }

     fun getDataFromAPI(){

        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }
     private fun showCountries(countryList : List<Country>){
         countries.value = countryList
         countryLoading.value = false
         countryError.value = false
     }

    private fun storeInSQLite(list: List<Country>){
        launch {
            crepo.deleteAllCountries()
            val listLong = crepo.insertAll(*list.toTypedArray())

            var i = 0

            while (i<list.size){
                list[i].uuid = listLong[i].toInt()
                i++
            }

            showCountries(list)

        }
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}