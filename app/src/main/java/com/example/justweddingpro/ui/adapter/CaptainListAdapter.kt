package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.ManagerListResponse
import com.example.justweddingpro.ui.AssignFunctionActivity.Companion.clientUserIdList
import com.example.justweddingpro.utils.CommonUtils

class CaptainListAdapter(
    var mcontext: Context,
    var mList: List<ManagerListResponse.ClientUserDetail>,
    var IsFunction: Boolean
) :
    RecyclerView.Adapter<CaptainListAdapter.ViewHolder>() {
    lateinit var onclickListner: OnclickListner

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_manager_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        clientUserIdList.forEachIndexed { index, i ->
            if (i == mList[position].eventfunctionId) {
                holder.Checkbox.isChecked = true
            }
        }

        Glide.with(mcontext)
            .load(mList[position].imageUrl)
            .placeholder(R.drawable.slider3)
            .into(holder.mImgFood)

        holder.tvUserName.text = mList[position].userName
        holder.tvDate.text = CommonUtils.parseDateAndTimeToViewDate(mList[position].emailId)
        holder.Checkbox.setOnClickListener {
            if (holder.Checkbox.isChecked) {
                clientUserIdList.add(mList[position].eventfunctionId!!)
            } else {
                clientUserIdList.remove(mList[position].eventfunctionId!!)
            }
        }

        if (!IsFunction) {
            holder.Checkbox.visibility = View.GONE
            holder.imgfrdicon.visibility = View.VISIBLE
            holder.itemView.setOnClickListener {
                onclickListner.onclick(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImgFood: ImageView
        var imgfrdicon: ImageView
        var tvUserName: TextView
        var tvDate: TextView
        var Checkbox: CheckBox

        init {
            mImgFood = itemView.findViewById(R.id.mImgFood)
            imgfrdicon = itemView.findViewById(R.id.imgfrdicon)
            tvUserName = itemView.findViewById(R.id.tvUserName)
            tvDate = itemView.findViewById(R.id.tvDate)
            Checkbox = itemView.findViewById(R.id.Checkbox)
        }
    }
}