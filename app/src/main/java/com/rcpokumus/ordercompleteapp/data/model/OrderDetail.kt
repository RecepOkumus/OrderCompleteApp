package com.rcpokumus.ordercompleteapp.data.model


data class OrderDetail(
    val id: Int,
    val orderId: Int,
    val siparisDurum: Int,
    val not:String,
    val orderDetailForms: List<OrderDetailForm>
)
