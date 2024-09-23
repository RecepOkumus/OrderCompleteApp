package com.rcpokumus.ordercompleteapp.ui

import FormAccordionAdapter
import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.rcpokumus.ordercompleteapp.data.local.entity.FormVehicleWithItems
import com.rcpokumus.ordercompleteapp.data.local.entity.VehicleEquipmentItemEntity
import com.rcpokumus.ordercompleteapp.databinding.ActivityOrderDetailBinding
import com.rcpokumus.ordercompleteapp.utils.MessageUtil
import com.rcpokumus.ordercompleteapp.utils.TimeUtil
import com.rcpokumus.ordercompleteapp.viewmodel.OrderDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() , FormAccordionAdapter.OnItemCheckedChangeListener {
    private lateinit var binding: ActivityOrderDetailBinding
    private val viewModel: OrderDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getIntExtra("order_id", -1)
        if (orderId != -1) {
            viewModel.getOrder(orderId.toString())
        }


        setupTabs()
        setupObservers()
        setupClickListeners()
    }

    private fun setupTabs() {
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    binding.notesContent.visibility = View.VISIBLE
                    binding.formRecyclerView.visibility = View.GONE
                } else {
                    binding.notesContent.visibility = View.GONE
                    binding.formRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Seçim kalktığında yapılacak işlemler
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Tab tekrar seçildiğinde yapılacak işlemler
            }
        })
    }

    private fun setupObservers() {
        viewModel.orderStatusList.observe(this, Observer { statusList ->
            val adapter =
                ArrayAdapter(this, R.layout.simple_spinner_item, statusList.map { it.text })
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.orderStatusSpinner.adapter = adapter
        })

        viewModel.formList.observe(this, Observer { formList ->
            viewModel.mapsDetailDataForms()
            val formAdapter = viewModel.order.value?.let {
                FormAccordionAdapter(formList, it, binding.formRecyclerView, this)
            }
            binding.formRecyclerView.adapter = formAdapter
            binding.formRecyclerView.layoutManager = LinearLayoutManager(this)

        })

        viewModel.orderDetailData.observe(this, Observer {
            it?.orderDetail?.let { it ->
                binding.orderStatusSpinner.setSelection(it.siparisDurum)
                binding.descriptionEditText.setText(it.not)
                viewModel.mapsDetailDataForms()
            }
        })

        viewModel.readMode.observe(this, Observer {
            setReadMode(!it)
        })

        viewModel.order.observe(this) { order ->
            setTextTime()

          order?.let {
              if (order.enerjiDurumu == "VAR") {
                  binding.powerOffButton.isEnabled = true
                  binding.powerOnButton.isEnabled = false
              } else if (order.enerjiDurumu == "YOK") {
                  binding.powerOffButton.isEnabled = false
                  binding.powerOnButton.isEnabled = true
              }
          }

        }

    }

    private fun setReadMode(value: Boolean) {
        binding.orderStatusSpinner.isEnabled = value
        binding.descriptionEditText.isEnabled = value
        binding.startButton.isEnabled = value
        binding.approveButton.isEnabled = value
        binding.powerOffButton.isEnabled = value
        binding.powerOnButton.isEnabled = value
    }

    private fun setTextTime(){
        binding.startTimeText.text=
            viewModel.order.value?.yolBaslangicMillis?.let { TimeUtil.formatTimestampToDate(it)}
        binding.approveTimeText.text=
            viewModel.order.value?.sonTamamlanmaMillis?.let { TimeUtil.formatTimestampToDate(it)}
        binding.powerOffTimeText.text=
            viewModel.order.value?.enerjiKesmeMillis?.let { TimeUtil.formatTimestampToDate(it)}
        binding.powerOnTimeText.text=
            viewModel.order.value?.enerjiVermeMillis?.let { TimeUtil.formatTimestampToDate(it)}

        binding.enerjiDurumuText.text= "Enerji Durumu: "+viewModel.order.value?.enerjiDurumu

    }

    override fun onItemCheckedChanged(position: Int, subPosition:Int, item: VehicleEquipmentItemEntity, isPositiveChecked: Int) {
        /*viewModel.formList.value?.filter { i -> i.vehicleEquipmentItems.contains(item) }
            ?.get(0)?.vehicleEquipmentItems?.filter { it -> it.id == item.id }?.get(0)?.isPositive = isPositiveChecked*/

        viewModel.formList.value?.get(position)?.vehicleEquipmentItems?.get(subPosition)?.isPositive =isPositiveChecked

    }

    override fun onItemCheckedAllChanged(position: Int, isPositiveChecked: Int) {
        viewModel.formList.value?.get(position)?.vehicleEquipmentItems?.forEach { item ->
            item.isPositive = isPositiveChecked
        }
    }


    private fun setupClickListeners() {
        binding.startButton.setOnClickListener {
            MessageUtil.showMessage(this , "Sipariş Başlatılacaktır.\nOnaylıyor musunuz?")
            {
                val currentOrder = viewModel.order.value?.copy()
                currentOrder?.yolBaslangicMillis = System.currentTimeMillis()
                viewModel.order.value = currentOrder
            }
        }

        binding.approveButton.setOnClickListener {
            if (checkData()) {
                MessageUtil.showMessage(this , "Enerji Durumu: "+viewModel.order.value?.enerjiDurumu+
                    "\n Siparişi KAYDETMEK istiyor musunuz?") {
                    viewModel.savaDetailForms(binding.descriptionEditText.text.toString(), binding.orderStatusSpinner.selectedItemPosition)
                finish()
                }
            }
        }

        binding.powerOffButton.setOnClickListener {
            MessageUtil.showMessage(this , "Enerji KESME yapılacatır.\nOnaylıyor musunuz?")
            {
                val currentOrder = viewModel.order.value?.copy()
                currentOrder?.enerjiDurumu = "YOK"
                currentOrder?.enerjiKesmeMillis = System.currentTimeMillis()
                viewModel.order.value = currentOrder
            }


        }

        binding.powerOnButton.setOnClickListener {
            MessageUtil.showMessage(this , "Enerji VERME yapılacatır.\nOnaylıyor musunuz?")
            {
                val currentOrder = viewModel.order.value?.copy()
                currentOrder?.enerjiDurumu = "VAR"
                currentOrder?.enerjiVermeMillis = System.currentTimeMillis()
                viewModel.order.value = currentOrder
            }
        }
    }

    private fun checkData(): Boolean {
        var isValid = true

        val descriptionText = binding.descriptionEditText.text.toString()
        if (descriptionText.isBlank() || descriptionText.length < 10) {
            isValid = false
            MessageUtil.showMessage(this,"Lütfen açıklamayı en az 10 karakter giriniz.")
        }

        val selectedStatusPosition = binding.orderStatusSpinner.selectedItemPosition
        if (selectedStatusPosition == 0) {
            isValid = false
            MessageUtil.showMessage(this,"Lütfen bir sipariş durumu seçin.")
        }



        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}