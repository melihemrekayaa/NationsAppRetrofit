package com.mobile.nationalized.di

import android.content.Context
import androidx.room.Room
import com.mobile.nationalized.data.datasource.CountryDataSource
import com.mobile.nationalized.data.repository.CountryRepository
import com.mobile.nationalized.room.CountryDao
import com.mobile.nationalized.room.CountryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCountryDataSource(cdao: CountryDao) : CountryDataSource {
        return CountryDataSource(cdao)
    }

    @Provides
    @Singleton
    fun provideCountryRepository(cds: CountryDataSource) : CountryRepository {
        return CountryRepository(cds)
    }

    @Provides
    @Singleton
    fun provideCountryDao(@ApplicationContext context: Context): CountryDao {
        val vt = Room.databaseBuilder(context, CountryDatabase::class.java , "countrydatabase").build()
        return vt.getCountryDao()
    }


}