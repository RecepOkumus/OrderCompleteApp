package com.rcpokumus.ordercompleteapp.data.local.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_equipment_items")
data class VehicleEquipmentItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val groupId: Int?,
    val negativeAnswerText: String?,
    val positiveAnswerText: String?,
    val title: String?

) {
    @Ignore
    var isPositive: Int? = -1
    @Ignore
    var formsDetailId:Int? = null
    @Ignore
    var orderId:Int? = null
    @Ignore
    var saveId:Int? = null
}
