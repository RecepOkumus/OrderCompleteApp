package com.rcpokumus.ordercompleteapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Transaction
import com.rcpokumus.ordercompleteapp.data.local.dao.OrderDao
import com.rcpokumus.ordercompleteapp.data.local.entity.FormVehicleWithItems
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity
import com.rcpokumus.ordercompleteapp.data.model.Order
import com.rcpokumus.ordercompleteapp.data.model.Orders
import com.rcpokumus.ordercompleteapp.data.remote.api.ApiService
import retrofit2.Response
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiService: ApiService,
    private val orderDao: OrderDao,
) {
    val allOrders: LiveData<List<OrderEntity>> = orderDao.getAllOrders()
    suspend fun fetchOrders(): List<OrderEntity>? {
        var orderEntities: List<OrderEntity>? = null
        try {
            val response = apiService.getOrderList()
            if (response.isSuccessful) {
                val ordersFromApi = (response.body() as Orders).order_list
                ordersFromApi.let {
                    orderEntities = it.map { order -> order.toEntity() }
                    insertAllOrders(orderEntities!!)
                }
            } else {
                val errorCode = response.code()
                val errorMessage = response.message()
                Log.e("API_ERROR", "Error: $errorCode - $errorMessage")
            }
            return orderEntities
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            return orderEntities
        }
    }

    private fun Order.toEntity(): OrderEntity {
        return OrderEntity(
            id = this.id,
            yolBaslangicMillis = this.yol_baslangic_millis,
            yolBitisMillis = this.yol_bitis_millis,
            enerjiKesmeMillis = this.enerji_kesme_millis,
            enerjiVermeMillis = this.enerji_verme_millis,
            enerjiDurumu = this.enerji_durumu,
            sonTamamlanmaMillis = this.son_tamamlanma_millis,
            siparisDurumu = this.siparis_durumu
        )
    }

    @Transaction
    suspend fun insertAllOrders(orders: List<OrderEntity>) {
        orderDao.insertOrders(orders)
    }

    @Transaction
    suspend fun insertAllOrder(order:OrderEntity) {
        orderDao.insertOrder(order)
    }

    @Transaction
    suspend fun getAllOrders(): List<OrderEntity> {
        return orderDao.getAllOrdersData()
    }

    @Transaction
    suspend fun getOrder(id: String): OrderEntity {
        return orderDao.getOrderById(id)
    }
}