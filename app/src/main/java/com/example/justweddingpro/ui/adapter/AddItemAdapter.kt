package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.Network.RequestModel.AddEventMenuPlanRequest
import com.example.justweddingpro.R

class AddItemAdapter(
    var mcontext: Context,
    var mList: ArrayList<AddEventMenuPlanRequest.EventMenuPlanDetail>
) :
    RecyclerView.Adapter<AddItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_addtocard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvProductName.setText(mList[position].getItemName())
        holder.imgRemove.setOnClickListener {
            mRemoveItem(position)
        }
    }

    fun mRemoveItem(position: Int) {
        mList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mProfileimage: ImageView
        var imgRemove: ImageView
        var tvProductName: TextView

        init {
            mProfileimage = itemView.findViewById(R.id.mProfileimage)
            imgRemove = itemView.findViewById(R.id.imgRemove)
            tvProductName = itemView.findViewById(R.id.tvProductName)
        }
    }
}