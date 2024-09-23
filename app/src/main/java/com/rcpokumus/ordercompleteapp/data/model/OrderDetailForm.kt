package com.rcpokumus.ordercompleteapp.data.model


data class OrderDetailForm(
    var id: Int,
    var orderDetailId: Int,
    val groupId: Int,
    val vehicleId:String,
    val isPositive: Boolean,// true ise pozitif false ise negatif cevap
)
