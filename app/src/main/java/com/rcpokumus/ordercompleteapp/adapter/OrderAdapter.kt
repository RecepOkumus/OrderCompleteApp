package com.rcpokumus.ordercompleteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity
import com.rcpokumus.ordercompleteapp.databinding.OrderItemBinding
import com.rcpokumus.ordercompleteapp.utils.TimeUtil

class OrderAdapter(
    private val orders: List<OrderEntity>,
    private val onItemClick: (OrderEntity) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.apply {
            orderIdTextView.text = order.id
            sonTamamlamaTarihiTextView.text = order.sonTamamlanmaMillis?.let {
                TimeUtil.formatTimestampToDate(
                    it
                )
            }
            enerjiDurumuTextView.text = order.enerjiDurumu ?: "BİLİNMİYOR"
            siparisDurumuTextView.text = order.siparisDurumu?.let { getOrderStatus(it) }
        }

        holder.itemView.setOnClickListener {
            onItemClick(order)
        }
    }

    override fun getItemCount(): Int = orders.size

    private fun getOrderStatus(status: String): String {
        return when (status) {
            "0" -> "Giriş Yapılabilir"
            "1" -> "Okunabilir"
            "2" -> "Yapılamaz"
            else -> "Bilinmiyor"
        }
    }
}