package com.rcpokumus.ordercompleteapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class OrderDetailWithForms(
    @Embedded val orderDetail: OrderDetailEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderDetailId"
    )
    val orderDetailForms: List<OrderDetailFormEntity>
)
