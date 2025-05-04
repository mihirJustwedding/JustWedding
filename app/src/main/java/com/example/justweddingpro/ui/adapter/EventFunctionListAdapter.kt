package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.utils.CommonUtils

class EventFunctionListAdapter(
    var mcontext: Context,
    var mList: List<EventDetailsResponse.EventFunctionDetail>
) :
    RecyclerView.Adapter<EventFunctionListAdapter.ViewHolder>() {
    lateinit var onclickListner: OnclickListner
    lateinit var OnMenuclickListner: OnMenuClickListner

    fun SetOnMenuclickListner(OnMenuclickListner: OnMenuClickListner) {
        this.OnMenuclickListner = OnMenuclickListner
    }

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(position: Int)
    }

    interface OnMenuClickListner {
        fun onclick(position: Int, mView: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_eventfunction_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFunctionName.text = mList[position].functionName
        holder.tvDate.text = CommonUtils.parseDateAndTimeToViewDate(mList[position].starttime)
        holder.tvStartTime.text = CommonUtils.parseDateAndTimeToViewTime(mList[position].starttime)
        holder.tvEndTime.text = CommonUtils.parseDateAndTimeToViewTime(mList[position].endtime)
        holder.tvVanueName.text = mList[position].venueName
        holder.tvPax.text = mList[position].pax.toString()

        holder.itemView.setOnClickListener {
            onclickListner.onclick(position)
        }

        holder.imgPopupMenu.setOnClickListener {
            OnMenuclickListner.onclick(position, holder.imgPopupMenu)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView
        var tvFunctionName: TextView
        var tvVanueName: TextView
        var tvStartTime: TextView
        var tvEndTime: TextView
        var tvPax: TextView
        var imgPopupMenu: ImageView

        init {
            tvDate = itemView.findViewById(R.id.tvDate)
            tvFunctionName = itemView.findViewById(R.id.tvFunctionName)
            tvVanueName = itemView.findViewById(R.id.tvVanueName)
            tvStartTime = itemView.findViewById(R.id.tvStartTime)
            tvEndTime = itemView.findViewById(R.id.tvEndTime)
            tvPax = itemView.findViewById(R.id.tvPax)
            imgPopupMenu = itemView.findViewById(R.id.imgPopupMenu)
        }
    }
}