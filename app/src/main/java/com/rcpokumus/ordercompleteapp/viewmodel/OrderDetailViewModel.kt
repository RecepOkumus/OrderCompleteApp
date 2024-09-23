package com.rcpokumus.ordercompleteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcpokumus.ordercompleteapp.data.local.entity.FormVehicleWithItems
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailFormEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderDetailWithForms
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity
import com.rcpokumus.ordercompleteapp.data.model.OrderStatus
import com.rcpokumus.ordercompleteapp.data.model.VehicleEquipmentItem
import com.rcpokumus.ordercompleteapp.repository.FormVehicleRepository
import com.rcpokumus.ordercompleteapp.repository.OrderDetailFormsRepository
import com.rcpokumus.ordercompleteapp.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val formVehicleRepository: FormVehicleRepository,
    private val orderDetailFormsRepository: OrderDetailFormsRepository
) : ViewModel() {

    private val _orderStatusList = MutableLiveData<List<OrderStatus>>()
    val orderStatusList: LiveData<List<OrderStatus>> get() = _orderStatusList

    private val _formList = MutableLiveData<List<FormVehicleWithItems>>()
    val formList: LiveData<List<FormVehicleWithItems>> get() = _formList
    var order: MutableLiveData<OrderEntity> =MutableLiveData<OrderEntity>(null)
    var readMode: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var orderDetailData: MutableLiveData<OrderDetailWithForms> = MutableLiveData<OrderDetailWithForms>()


    init {
        loadMockData()
    }

    private fun loadMockData() {
        val statuses = listOf(
            OrderStatus(0, "Seçim Yapınız."),
            OrderStatus(1, "Sorunsuz kapatıldı."),
            OrderStatus(2, "Vardiya bitti."),
            OrderStatus(3, "Hayvan hasarı var, başka ekip gelmeli.")
        )
        _orderStatusList.value = statuses
    }


     fun getOrder(id:String) {
         viewModelScope.launch {
             order.value=orderRepository.getOrder(id)
             order.value.let {
                 if( order.value!!.siparisDurumu=="1")
                     readMode.value=true

                 orderDetailData.value=orderDetailFormsRepository.getOrderWithForms(order.value!!.id.toInt())

             }
             val formItems = formVehicleRepository.getAllFormsWithVehicleEquipment()
             _formList.value = formItems
         }
    }

    fun mapsDetailDataForms(){
        if(orderDetailData.value!=null){
            orderDetailData.value!!.orderDetailForms.forEach {
                formList.value?.filter { i-> i.formItem.id == it.groupId }?.get(0)?.vehicleEquipmentItems?.filter{ t-> t.id==it.vehicleId}?.get(0)?.apply {
                    isPositive=it.isPositive
                    formsDetailId=it.orderDetailId
                    orderId=it.orderId
                    saveId=it.id
                }
            }
        }


    }

    fun savaDetailForms(not:String,orderStatus:Int){
        viewModelScope.launch {
            orderRepository.insertAllOrder(order.value!!)
                val orderDetailForms=createDetailFormsData()
                val orderDetail= order.value?.id?.let {
                    OrderDetailEntity(
                        id = orderDetailData.value?.orderDetail?.id ?: null,
                        orderId = it.toInt(),
                        siparisDurum = orderStatus,
                        not = not

                    )
            }
            orderDetail?.let {
                val orderDetailWithForms=OrderDetailWithForms(it,orderDetailForms)
                orderDetailFormsRepository.insertOrderWithForms(orderDetailWithForms)
            }

        }
    }

    private fun createDetailFormsData(): List<OrderDetailFormEntity> {
        val orderDetailForms = mutableListOf<OrderDetailFormEntity>()
        formList.value?.forEach {it ->
            it.vehicleEquipmentItems.forEach { t ->
                val orderDetailFormEntity= order.value?.id?.let { it1 ->
                    OrderDetailFormEntity(
                        id = t.saveId,
                        orderDetailId = orderDetailData.value?.orderDetail?.id ?: 0,
                        orderId = it1.toInt(),
                        groupId = it.formItem.id,
                        vehicleId = t.id,
                        isPositive = t.isPositive ?: -1
                    )
                }
                orderDetailForms.add(orderDetailFormEntity!!)
            }
        }
        return orderDetailForms
    }


}


