package com.rcpokumus.ordercompleteapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var yolBaslangicMillis: Long?,
    var yolBitisMillis: Long?,
    var enerjiKesmeMillis: Long?,
    var enerjiVermeMillis: Long?,
    var enerjiDurumu: String?,
    var sonTamamlanmaMillis: Long?,
    var siparisDurumu: String?
)
