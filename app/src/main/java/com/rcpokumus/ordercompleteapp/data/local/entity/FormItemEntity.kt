package com.rcpokumus.ordercompleteapp.data.local.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "form_items")
data class FormItemEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var title: String?,
) {
    @Ignore
    var isExpanded: Boolean? = false
}
