package com.rcpokumus.ordercompleteapp.repository

import androidx.room.Query
import androidx.room.Transaction
import com.rcpokumus.ordercompleteapp.data.local.dao.OrderDetailFormsDao
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailFormEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailWithForms
import com.rcpokumus.ordercompleteapp.data.remote.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderDetailFormsRepository  @Inject constructor(
    private val orderDetailFormsDao: OrderDetailFormsDao
){

    suspend fun insertOrderWithForms(orderDetailWithForms: OrderDetailWithForms) {
            orderDetailFormsDao.insertOrderWithForms(orderDetailWithForms)
    }

    suspend fun getOrderWithForms(orderId: Int): OrderDetailWithForms? {
            return orderDetailFormsDao.getOrderDetailWithForms(orderId)
    }

    suspend fun getOrderWithFormsAll(): OrderDetailWithForms? {
        return orderDetailFormsDao.getOrderDetailWithFormsAll()
    }



}