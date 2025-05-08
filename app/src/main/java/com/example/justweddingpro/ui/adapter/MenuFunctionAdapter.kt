package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.Response.FunctionListResponse
import com.example.justweddingpro.utils.CommonUtils


class MenuFunctionAdapter(
    var mcontext: Context,
    var mList: List<FunctionListResponse.FunctionDetail>
) :
    RecyclerView.Adapter<MenuFunctionAdapter.ViewHolder>() {
    lateinit var onclickListner: OnclickListner
    var selectedPosition = 0

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(FunctionID: Int, FunctionName: String, isClick: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_homefunction_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFunctionName.text = mList[position].functionName
        holder.tvEventName.text = mList[position].eventname
        holder.tvDate.text = CommonUtils.parseDateToViewUtcToLocal(mList[position].starttime)

        //using selector drawable
        holder.Checkbox.isChecked = selectedPosition == position

        holder.itemView.setOnClickListener {
            holder.Checkbox.performClick()
        }

        holder.Checkbox.setOnClickListener {
            if (position == selectedPosition) {
                holder.Checkbox.isChecked = false
                selectedPosition = -1
            } else {
                selectedPosition = position
                notifyDataSetChanged()
            }
            onclickListner.onclick(
                mList[position].functionId!!,
                mList[position].functionName!!, true
            )
        }/*(
            CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                // check condition
                if (b) {
                    selectedPosition = holder.adapterPosition
                    onclickListner.onclick(
                        mList[position].functionId!!,
                        mList[position].functionName!!, true
                    )
                }
            })*/
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFunctionName: TextView
        var tvEventName: TextView
        var tvDate: TextView
        var Checkbox: CheckBox

        init {
            tvFunctionName = itemView.findViewById(R.id.tvFunctionName)
            tvEventName = itemView.findViewById(R.id.tvEventName)
            tvDate = itemView.findViewById(R.id.tvDate)
            Checkbox = itemView.findViewById(R.id.Checkbox)
        }
    }
}