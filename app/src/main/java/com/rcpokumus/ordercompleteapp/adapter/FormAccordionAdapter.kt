import android.database.CursorJoiner
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.rcpokumus.ordercompleteapp.R
import com.rcpokumus.ordercompleteapp.data.local.entity.FormVehicleWithItems
import com.rcpokumus.ordercompleteapp.data.local.entity.OrderEntity
import com.rcpokumus.ordercompleteapp.data.local.entity.VehicleEquipmentItemEntity
import com.rcpokumus.ordercompleteapp.databinding.ItemAccordionBinding


class FormAccordionAdapter(private val accordionList: List<FormVehicleWithItems>,
                           private val order: OrderEntity,
                           private val recyclerView: RecyclerView,
                           private val listener: OnItemCheckedChangeListener) :
    RecyclerView.Adapter<FormAccordionAdapter.AccordionViewHolder>() {
    var _position:Int=-1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccordionViewHolder {
        val binding = ItemAccordionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccordionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccordionViewHolder, position: Int) {
        val accordion = accordionList[position]
        _position=position
        holder.bind(accordion)
    }

    override fun getItemCount(): Int = accordionList.size

    inner class AccordionViewHolder(private val binding: ItemAccordionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(accordion: FormVehicleWithItems) {

            binding.accordionTitle.text = accordion.formItem.title
            binding.formItemContainer.removeAllViews()

            // Butonu ekliyoruz
            val selectAllButton = Button(binding.root.context).apply {
                text = "Hepsi Var/Uygun"
                setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.btn_colorPrimary))
                setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                setTypeface(null, Typeface.BOLD)
                val params = ConstraintLayout.LayoutParams(
                    410,
                    115
                )
                params.setMargins(740, 0, 0, 20)
                layoutParams = params
                setOnClickListener {
                    accordion.vehicleEquipmentItems.forEachIndexed { index, item ->
                        val radioGroup = binding.formItemContainer.findViewWithTag<RadioGroup>("radioGroup_$index")
                        val positiveRadioButton = radioGroup.getChildAt(0) as RadioButton
                        positiveRadioButton.isChecked = true
                        item.isPositive=1
                    }
                    listener.onItemCheckedAllChanged(_position,1)
                }
            }

            if (order.siparisDurumu == "1") {
                selectAllButton.isEnabled = false
            }

            binding.formItemContainer.addView(selectAllButton)

            accordion.vehicleEquipmentItems.forEachIndexed {index,item ->
                val radioGroup = RadioGroup(binding.root.context).apply {
                    orientation = RadioGroup.HORIZONTAL
                    tag = "radioGroup_$index"
                    val params = RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 0, 0, 40)
                    layoutParams = params
                }
                val titleView = TextView(binding.root.context).apply {
                    text = item.title
                    textSize = 17f
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(ContextCompat.getColor(binding.root.context, R.color.accordion_title_color)) // Başlık rengi
                }

                val positiveRadioButton = RadioButton(binding.root.context).apply {
                    id = View.generateViewId()
                    text = item.positiveAnswerText
                    buttonTintList = ContextCompat.getColorStateList(binding.root.context, R.color.color_selector_radio)
                }


                val negativeRadioButton = RadioButton(binding.root.context).apply {
                    id = View.generateViewId()
                    text = item.negativeAnswerText
                    val params = RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(80, 0, 0, 0)
                    layoutParams = params
                    buttonTintList = ContextCompat.getColorStateList(binding.root.context, R.color.color_selector_radio)

                }

                if (order.siparisDurumu == "1") {
                    positiveRadioButton.isEnabled = false
                    negativeRadioButton.isEnabled = false
                }

                when (item.isPositive) {
                    1 -> positiveRadioButton.isChecked = true
                    0 -> negativeRadioButton.isChecked = true
                }


                radioGroup.addView(positiveRadioButton)
                radioGroup.addView(negativeRadioButton)

                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        positiveRadioButton.id -> listener.onItemCheckedChanged(_position,index,item, 1)
                        negativeRadioButton.id -> listener.onItemCheckedChanged(_position,index,item, 0)
                    }
                }

                binding.formItemContainer.addView(titleView)
                binding.formItemContainer.addView(radioGroup)
            }


            if (accordion.formItem.isExpanded == true) {
                binding.formItemContainer.visibility = View.VISIBLE
                binding.arrowIcon.setImageResource(R.drawable.ic_arrow_down)
            } else {
                binding.formItemContainer.visibility = View.GONE
                binding.arrowIcon.setImageResource(R.drawable.ic_arrow_up)
            }

            binding.accordionMain.setOnClickListener {
                recyclerView.scrollToPosition(0)
                closeAllExcept(adapterPosition)
                accordion.formItem.isExpanded = !accordion.formItem.isExpanded!!
                notifyItemChanged(adapterPosition)
            }
        }
    }

    private fun closeAllExcept(position: Int) {
        accordionList.forEachIndexed { index, accordion ->
            if (index != position) {
                accordion.formItem.isExpanded = false
                notifyItemChanged(index)
            }
        }
    }

    interface OnItemCheckedChangeListener {
        fun onItemCheckedChanged(position: Int, subPosition:Int, item: VehicleEquipmentItemEntity, isPositiveChecked: Int)
        fun onItemCheckedAllChanged(position: Int,isPositiveChecked: Int)
    }
}

