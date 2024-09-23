package com.rcpokumus.ordercompleteapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class FormVehicleWithItems(
    @Embedded val formItem: FormItemEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val vehicleEquipmentItems: List<VehicleEquipmentItemEntity>
)
