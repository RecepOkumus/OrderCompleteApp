package com.rcpokumus.ordercompleteapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders")
     fun getAllOrders(): LiveData<List<OrderEntity>>

    @Query("SELECT * FROM orders")
    suspend fun getAllOrdersData(): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderById(orderId: String): OrderEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Delete
    suspend fun deleteOrder(order: OrderEntity)

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()
}