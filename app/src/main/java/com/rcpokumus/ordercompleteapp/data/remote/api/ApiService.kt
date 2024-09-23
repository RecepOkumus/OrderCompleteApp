package com.rcpokumus.ordercompleteapp.data.remote.api


import com.rcpokumus.ordercompleteapp.data.model.FormItem
import com.rcpokumus.ordercompleteapp.data.model.Order
import com.rcpokumus.ordercompleteapp.data.model.Orders
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("orderList")
    suspend fun getOrderList(): Response<Orders>

    @GET("formItems")
    suspend fun getFormItems(): Response<List<FormItem>>
}