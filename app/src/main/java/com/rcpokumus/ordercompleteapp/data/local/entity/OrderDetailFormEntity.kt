package com.rcpokumus.ordercompleteapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_details_forms")
data class OrderDetailFormEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val orderDetailId: Int,
    val orderId: Int,
    val groupId: Int,
    val vehicleId: String,
    val isPositive: Int
)
