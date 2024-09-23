package com.rcpokumus.ordercompleteapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcpokumus.ordercompleteapp.data.local.dao.OrderDao
import com.rcpokumus.ordercompleteapp.data.local.entity.FormVehicleWithItems
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity
import com.rcpokumus.ordercompleteapp.repository.FormVehicleRepository
import com.rcpokumus.ordercompleteapp.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {
    val orders: LiveData<List<OrderEntity>> = orderRepository.allOrders

    init {

    }

}