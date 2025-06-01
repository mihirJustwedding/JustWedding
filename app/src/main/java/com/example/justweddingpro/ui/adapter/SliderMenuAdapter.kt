package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.MenuCategoryListResponse
import de.hdodenhof.circleimageview.CircleImageView

class SliderMenuAdapter(
    var mcontext: Context,
    var mList: ArrayList<MenuCategoryListResponse.MenuCategoryDetail>
) :
    RecyclerView.Adapter<SliderMenuAdapter.ViewHolder>() {

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
            LayoutInflater.from(mcontext).inflate(R.layout.item_slider_menu, parent, false)
        return ViewHolder(view)
    }

    var row_index = -1
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (row_index == position) {
            holder.imgMenu.borderColor = (mcontext.getColor(R.color.Color_Primery))
//            holder.ivImageview.imageTintList =
//                ColorStateList.valueOf(
//                    mcontext.resources.getColor(
//                        R.color.white
//                    )
//                )
        } else {
            if ((row_index == -1) && (position == 0)) {
                holder.imgMenu.borderColor = (mcontext.getColor(R.color.Color_Primery))
//                holder.ivImageview.imageTintList =
//                    ColorStateList.valueOf(
//                        mcontext.resources.getColor(
//                            R.color.white
//                        )
//                    )
            } else {
                holder.imgMenu.borderColor = (mcontext.getColor(R.color.white))
//                holder.ivImageview.setBackgroundColor(mcontext.getColor(R.color.Color_Primery))
//                holder.ivImageview.imageTintList =
//                    ColorStateList.valueOf(
//                        mcontext.resources.getColor(
//                            R.color.Color_Primery
//                        )
//                    )
            }
        }

        holder.tvCatName.text = mList[position].menuname
//        holder.tvCount.text = "(${mList[position].itemsDetails?.size.toString()})"

        Glide.with(mcontext)
            .load(mList[position].menuImage)
            .placeholder(R.drawable.slider3)
            .into(holder.imgMenu)

        holder.itemView.setOnClickListener {
            onclickListner.onclick(position)
            row_index = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgMenu: CircleImageView

        //        var imgBackground: ImageView
        var tvCatName: TextView
        var tvCount: TextView


        init {
            imgMenu = itemView.findViewById(R.id.imgMenu)
//            imgBackground = itemView.findViewById(R.id.imgBackground)
            tvCatName = itemView.findViewById(R.id.tvCatName)
            tvCount = itemView.findViewById(R.id.tvCount)
        }
    }
}