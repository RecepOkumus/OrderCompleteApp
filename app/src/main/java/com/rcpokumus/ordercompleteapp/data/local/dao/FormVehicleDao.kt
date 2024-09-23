package com.rcpokumus.ordercompleteapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.rcpokumus.ordercompleteapp.data.local.entity.FormItemEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.FormVehicleWithItems
import com.rcpokumus.ordercompleteapp.data.local.entity.VehicleEquipmentItemEntity

@Dao
interface FormVehicleDao {

    @Transaction
    @Query("SELECT * FROM form_items")
    fun getAllFormsWithVehicleEquipment(): LiveData<List<FormVehicleWithItems>>

    @Transaction
    @Query("SELECT * FROM form_items")
    suspend fun getAllFormsWithVehicleEquipmentData(): List<FormVehicleWithItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormItem(formItem: FormItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicleEquipmentItems(vehicleItems: List<VehicleEquipmentItemEntity>)

    @Transaction
    suspend fun insertFormWithVehicleItems(formVehicleWithItems: FormVehicleWithItems) {
        insertFormItem(formVehicleWithItems.formItem)
        insertVehicleEquipmentItems(formVehicleWithItems.vehicleEquipmentItems)
    }

    @Query("DELETE FROM form_items")
    suspend fun deleteAllFormItems()

    @Query("DELETE FROM vehicle_equipment_items")
    suspend fun deleteAllVehicleItems()
}