package com.rcpokumus.ordercompleteapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcpokumus.ordercompleteapp.data.local.entity.FormVehicleWithItems
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity
import com.rcpokumus.ordercompleteapp.repository.FormVehicleRepository
import com.rcpokumus.ordercompleteapp.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val formVehicleRepository: FormVehicleRepository,
) : ViewModel() {

    val _loading = MutableLiveData<Boolean>()

    lateinit var orders: List<OrderEntity>
    lateinit var formItems: List<FormVehicleWithItems>


    init {
        _loading.value = true
        viewModelScope.launch {
            orders = orderRepository.getAllOrders()
            formItems = formVehicleRepository.getAllFormsWithVehicleEquipment()

            if (orders.isEmpty()) fetchOrderData() else delayBeforeSettingLoadingFalse()

            if (formItems.isEmpty()) fetchFormItemData()
        }
    }

    private fun fetchOrderData() {
        viewModelScope.launch {
            try {
                orderRepository.fetchOrders()
                delayBeforeSettingLoadingFalse()
            } catch (e: Exception) {
                delayBeforeSettingLoadingFalse()
                Log.e("fetchOrderData", "Exception: ${e.message}")
            }
        }
    }

    private fun fetchFormItemData() {
        viewModelScope.launch {
            try {
                formVehicleRepository.fetchAndInsertFormVehicle()
            } catch (e: Exception) {
                Log.e("fetchFormItemData", "Exception: ${e.message}")
            }
        }
    }

    private suspend fun delayBeforeSettingLoadingFalse() {
        delay(2000)
        _loading.value = false
    }
}