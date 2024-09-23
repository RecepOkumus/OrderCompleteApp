package com.rcpokumus.ordercompleteapp.data.model

data class FormItem(
    val id: Int,
    val title: String,
    val vehicleEquipmentItems: List<VehicleEquipmentItem>
)

data class FormItems(
    val order_list: List<FormItem>
)
