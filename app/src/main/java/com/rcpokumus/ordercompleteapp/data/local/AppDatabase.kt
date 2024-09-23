package com.rcpokumus.ordercompleteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rcpokumus.ordercompleteapp.data.local.dao.FormVehicleDao
import com.rcpokumus.ordercompleteapp.data.local.dao.OrderDao
import com.rcpokumus.ordercompleteapp.data.local.dao.OrderDetailFormsDao
import com.rcpokumus.ordercompleteapp.data.local.entity.FormItemEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailFormEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.VehicleEquipmentItemEntity

@Database(entities = [OrderEntity::class, FormItemEntity::class, VehicleEquipmentItemEntity::class , OrderDetailEntity::class, OrderDetailFormEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun formVehicleDao(): FormVehicleDao
    abstract fun orderDetailFormsDao(): OrderDetailFormsDao
}