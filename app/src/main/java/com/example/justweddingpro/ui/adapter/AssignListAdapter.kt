package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.Response.AssignManagerAndCaptainResponse

class AssignListAdapter(
    var mcontext: Context,
    var mList: List<AssignManagerAndCaptainResponse.Detail>
) :
    RecyclerView.Adapter<AssignListAdapter.ViewHolder>() {
    lateinit var onclickListner: OnclickListner


    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(position: Int, mDetail: AssignManagerAndCaptainResponse.Detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_assignlist_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        Glide.with(mcontext)
//            .load(mList[position].imageUrl)
//            .placeholder(R.drawable.slider3)
//            .into(holder.mImgFood)

        holder.tvUserName.text = mList[position].clientUserName
        holder.tvDate.text = mList[position].clientUserName
        holder.itemView.setOnClickListener {
            onclickListner.onclick(position, mList[position])
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImgFood: ImageView
        var tvUserName: TextView
        var tvDate: TextView
        var btnStatus: AppCompatButton

        init {
            mImgFood = itemView.findViewById(R.id.mImgFood)
            tvUserName = itemView.findViewById(R.id.tvUserName)
            tvDate = itemView.findViewById(R.id.tvDate)
            btnStatus = itemView.findViewById(R.id.btnStatus)
        }
    }
}