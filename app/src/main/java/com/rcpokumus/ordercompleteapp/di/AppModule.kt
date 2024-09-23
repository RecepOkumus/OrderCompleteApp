package com.rcpokumus.ordercompleteapp.di

import android.content.Context
import androidx.room.Room
import com.rcpokumus.ordercompleteapp.data.local.AppDatabase
import com.rcpokumus.ordercompleteapp.data.local.dao.FormVehicleDao
import com.rcpokumus.ordercompleteapp.data.local.dao.OrderDao
import com.rcpokumus.ordercompleteapp.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/bydevelopertr/versionproject/master/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }




}