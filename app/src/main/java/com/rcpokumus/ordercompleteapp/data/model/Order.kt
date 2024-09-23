package com.rcpokumus.ordercompleteapp.data.model

data class Order(
    val id: String,
    val yol_baslangic_millis: Long?,
    val yol_bitis_millis: Long?,
    val enerji_kesme_millis: Long?,
    val enerji_verme_millis: Long?,
    val enerji_durumu: String?,
    val son_tamamlanma_millis: Long?,
    val siparis_durumu: String?
)

data class Orders(
    val order_list: List<Order>
)
