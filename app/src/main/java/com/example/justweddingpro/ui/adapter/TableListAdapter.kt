package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.TableListResponse
import com.example.justweddingpro.ui.AssignManagerAndCaptainActivity.Companion.mSelectedList

class TableListAdapter(
    var mcontext: Context,
    var mList: ArrayList<TableListResponse.TableMasterDetail>
) :
    RecyclerView.Adapter<TableListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_tablelist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUserName.text = mList[position].tableName

        holder.Checkbox.setOnClickListener {
            holder.itemView.performClick()
            if (holder.Checkbox.isChecked) {
                holder.rlMain.backgroundTintList =
                    ColorStateList.valueOf(mcontext.getColor(R.color.green_color))
                holder.tvUserName.setTextColor(mcontext.getColor(R.color.white))
                mSelectedList.add(mList[position].tableId!!)
            } else {
                holder.rlMain.backgroundTintList = null
                holder.tvUserName.setTextColor(mcontext.getColor(R.color.black))
                mSelectedList.remove(mList[position].tableId!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserName: TextView
        var Checkbox: CheckBox
        var rlMain: RelativeLayout

        init {
            tvUserName = itemView.findViewById(R.id.tvUserName)
            Checkbox = itemView.findViewById(R.id.Checkbox)
            rlMain = itemView.findViewById(R.id.rlMain)
        }
    }
}