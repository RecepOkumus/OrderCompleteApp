package com.rcpokumus.ordercompleteapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rcpokumus.ordercompleteapp.adapter.OrderAdapter
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity
import com.rcpokumus.ordercompleteapp.databinding.ActivityMainBinding
import com.rcpokumus.ordercompleteapp.utils.MessageUtil
import com.rcpokumus.ordercompleteapp.utils.TimeUtil
import com.rcpokumus.ordercompleteapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: OrderAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.orders.observe(this) { orders ->
            setupRecyclerView(orders)
        }

    }

    private fun setupRecyclerView(orders: List<OrderEntity>) {
        adapter = OrderAdapter(orders) { order ->
            handleOrderClick(order)
        }
        binding.orderRecyclerView.adapter = adapter
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun handleOrderClick(order: OrderEntity) {
        checkOrderSiparisDurumu(order)
    }

    private fun checkOrderSiparisDurumu(order: OrderEntity) {
        when (order.siparisDurumu) {
            "0" -> {//"Giriş Yapılabilir"
                if(order.sonTamamlanmaMillis?.let { TimeUtil.isTimestampBeforeNow(it) } == false){
                    starOrderDetail(order.id)
                }else{
                    MessageUtil.showMessage(this, "Sipariş tarihi geçmiştir. Yapılamaz.")
                }
            }
            "1" -> { //"Okunabilir"
                starOrderDetail(order.id)
            }
            "2" ->  { //"Yapılamaz"
                MessageUtil.showMessage(this, "Bu sipariş artık yapılamaz...")
            }
            else -> { // Bilinmiyor
                MessageUtil.showMessage(this, "Bilinmeyen sipariş durumu...")
            }
        }
    }

    private fun starOrderDetail(id:String){
        val intent = Intent(this, OrderDetailActivity::class.java)
        intent.putExtra("order_id", id.toInt())
        startActivity(intent)
    }

    private fun readMode(){

    }


}