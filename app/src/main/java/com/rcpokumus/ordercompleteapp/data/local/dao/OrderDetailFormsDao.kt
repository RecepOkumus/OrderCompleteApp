package com.rcpokumus.ordercompleteapp.data.local.dao

import androidx.room.*
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailFormEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailWithForms

@Dao
interface OrderDetailFormsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetail(orderDetail: OrderDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetailForms(orderDetailForms: List<OrderDetailFormEntity>)

    @Transaction
    @Query("SELECT * FROM order_details WHERE id = :orderId")
    suspend fun getOrderDetailWithForms(orderId: Int): OrderDetailWithForms

    @Transaction
    @Query("SELECT * FROM order_details")
    suspend fun getOrderDetailWithFormsAll(): OrderDetailWithForms

    @Transaction
    suspend fun insertOrderWithForms(orderDetailWithForms:OrderDetailWithForms) {
        insertOrderDetail(orderDetailWithForms.orderDetail)
        insertOrderDetailForms(orderDetailWithForms.orderDetailForms)
    }

    @Query("DELETE FROM order_details WHERE id = :orderDetailId")
    suspend fun deleteOrderDetail(orderDetailId: Int)

    @Query("DELETE FROM order_details_forms WHERE orderDetailId = :orderDetailId")
    suspend fun deleteOrderDetailForms(orderDetailId: Int)
}