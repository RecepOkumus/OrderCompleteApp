package com.rcpokumus.ordercompleteapp.di

import android.content.Context
import androidx.room.Room
import com.rcpokumus.ordercompleteapp.data.local.AppDatabase
import com.rcpokumus.ordercompleteapp.data.local.dao.FormVehicleDao
import com.rcpokumus.ordercompleteapp.data.local.dao.OrderDao
import com.rcpokumus.ordercompleteapp.data.local.dao.OrderDetailFormsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "order_complete_database1"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideOrderDao(appDatabase: AppDatabase): OrderDao {
        return appDatabase.orderDao()
    }

    @Provides
    @Singleton
    fun provideFormVehicleDao(appDatabase: AppDatabase): FormVehicleDao {
        return appDatabase.formVehicleDao()
    }


    @Provides
    @Singleton
    fun provideOrderDetailFormsDao(appDatabase: AppDatabase): OrderDetailFormsDao {
        return appDatabase.orderDetailFormsDao()
    }
}