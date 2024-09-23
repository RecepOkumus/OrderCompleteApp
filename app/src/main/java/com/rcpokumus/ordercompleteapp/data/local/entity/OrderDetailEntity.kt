package com.rcpokumus.ordercompleteapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_details")
data class OrderDetailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val orderId: Int,
    val siparisDurum: Int,
    val not: String
)
