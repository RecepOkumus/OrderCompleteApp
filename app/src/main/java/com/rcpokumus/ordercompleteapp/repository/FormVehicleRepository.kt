package com.rcpokumus.ordercompleteapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.rcpokumus.ordercompleteapp.data.local.dao.FormVehicleDao
import com.rcpokumus.ordercompleteapp.data.local.entity.FormItemEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.FormVehicleWithItems
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.VehicleEquipmentItemEntity
import com.rcpokumus.ordercompleteapp.data.remote.api.ApiService
import com.rcpokumus.ordercompleteapp.data.model.FormItem
import javax.inject.Inject

class FormVehicleRepository @Inject constructor(
    private val apiService: ApiService,
    private val formVehicleDao: FormVehicleDao,
) {

    val allFormItems: LiveData<List<FormVehicleWithItems>> =
        formVehicleDao.getAllFormsWithVehicleEquipment()

    suspend fun fetchAndInsertFormVehicle(): FormVehicleWithItems? {

        try {
            val response = apiService.getFormItems()
            if (response.isSuccessful) {
                val formItemsFromApi = response.body() as List<FormItem>

                val formVehicleEntities = formItemsFromApi.map { formResponse ->
                    FormVehicleWithItems(
                        formItem = FormItemEntity(
                            id = formResponse.id,
                            title = formResponse.title
                        ),
                        vehicleEquipmentItems = formResponse.vehicleEquipmentItems.map { itemResponse ->
                            VehicleEquipmentItemEntity(
                                id = itemResponse.id,
                                groupId = itemResponse.groupId,
                                negativeAnswerText = itemResponse.negativeAnswerText,
                                positiveAnswerText = itemResponse.positiveAnswerText,
                                title = itemResponse.title
                            )
                        }
                    )
                }

                formVehicleEntities.forEach { formVehicleWithItems ->
                    insertFormVehicleWithItems(formVehicleWithItems)
                }
            } else {
                val errorCode = response.code()
                val errorMessage = response.message()
                Log.e("API_ERROR", "Error: $errorCode - $errorMessage")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("API_ERROR", "Exception: ${e.message}")

        }
        return null
    }

    @Transaction
    suspend fun insertFormVehicleWithItems(formVehicleWithItems: FormVehicleWithItems) {
        formVehicleDao.insertFormWithVehicleItems(formVehicleWithItems)
    }

    @Transaction
    suspend fun getAllFormsWithVehicleEquipment(): List<FormVehicleWithItems> {
        return formVehicleDao.getAllFormsWithVehicleEquipmentData()
    }
}