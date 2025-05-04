package com.example.justweddingpro.ManagerAndCaptainUi.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justweddingpro.ManagerAndCaptainUi.Response.OrderListTableResponse
import com.example.justweddingpro.R
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager

class OrderHistoryAdapter(
    var mcontext: Context,
    var mList: List<OrderListTableResponse.OrderTableDetail>
) :
    RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    lateinit var onclickListner: OnclickListner

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(OrderTableId: Int, Status: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_order_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvTableNo.text = mList[position].tableName
        holder.tvDescription.text = mList[position].itemName
        holder.btnStatus.text = mList[position].status

        if (mList[position].status?.contains("Delivered")!!) {
            holder.btnStatus.backgroundTintList =
                ColorStateList.valueOf(mcontext.resources.getColor(R.color.green_color))
        } else {
            holder.btnStatus.backgroundTintList =
                ColorStateList.valueOf(mcontext.resources.getColor(R.color.yellow_color))
        }

        holder.btnStatus.setOnClickListener {
            if (!PreferenceManager.getPref(Constants.Preference.Pref_Category, "")
                    .equals("manager")
            ) {
                if (!holder.btnStatus.text.equals("Delivered")) {
                    onclickListner.onclick(mList[position].orderTableId!!, "Delivered")
                }
            }
        }

        Glide.with(mcontext)
            .load(mList[position].itemImage)
            .placeholder(R.drawable.slider3)
            .into(holder.mImgFood)

        holder.tvQnty.setText("quantity: ${mList[position].qty.toString()}")
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTableNo: TextView
        var tvDescription: TextView
        var tvQnty: TextView
        var mImgFood: ImageView
        var btnStatus: AppCompatButton

        init {
            tvTableNo = itemView.findViewById(R.id.tvTableNo)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvQnty = itemView.findViewById(R.id.tvQnty)
            btnStatus = itemView.findViewById(R.id.btnStatus)
            mImgFood = itemView.findViewById(R.id.mImgFood)
        }
    }
}