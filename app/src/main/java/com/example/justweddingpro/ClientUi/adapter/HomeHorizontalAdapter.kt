package com.example.justweddingpro.ClientUi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justweddingpro.ClientUi.Response.EventFunctionMenuDetailsResponse
import com.example.justweddingpro.ClientUi.Response.ItemsDetail
import com.example.justweddingpro.R
import de.hdodenhof.circleimageview.CircleImageView

class HomeHorizontalAdapter(
    var mcontext: Context,
    var mList: ArrayList<EventFunctionMenuDetailsResponse.EventMenuPlanDetail>
) :
    RecyclerView.Adapter<HomeHorizontalAdapter.ViewHolder>() {

    lateinit var onclickListner: OnclickListner

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(position: Int, itemsDetails: List<ItemsDetail>?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_home_horizontal, parent, false)
        return ViewHolder(view)
    }

    var row_index = -1
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvFoodName.text = mList[position].menuName

        if (row_index == position) {
            holder.mImgFood.borderColor = mcontext.getColor(R.color.Color_Primery)
            holder.imgDot.visibility = View.VISIBLE
        } else {
            if ((row_index == -1) && (position == 0)) {
                holder.mImgFood.borderColor = mcontext.getColor(R.color.Color_Primery)
                holder.imgDot.visibility = View.VISIBLE
            } else {
                holder.mImgFood.borderColor = mcontext.getColor(R.color.white)
                holder.imgDot.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            onclickListner.onclick(position, mList[position].itemsDetails)
            row_index = position
            notifyDataSetChanged()
        }

        Glide.with(mcontext)
            .load(mList[position].menuImage)
            .placeholder(R.drawable.slider3)
            .into(holder.mImgFood)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImgFood: CircleImageView
        var imgDot: ImageView
        var tvFoodName: TextView

        init {
            mImgFood = itemView.findViewById(R.id.mImgFood)
            tvFoodName = itemView.findViewById(R.id.tvFoodName)
            imgDot = itemView.findViewById(R.id.imgDot)
        }
    }
}