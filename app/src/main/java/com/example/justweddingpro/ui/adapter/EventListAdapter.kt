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
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.utils.CommonUtils

class EventListAdapter(
    var mcontext: Context,
    var mList: List<EventDetailsResponse.EventMasterDetail>
) :

    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    lateinit var onclickListner: OnclickListner
    lateinit var onclickPopupMenuListner: PopupOnclickListner

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    fun SetOnPopupclickListner(mOnclickPopupMenuListner: PopupOnclickListner) {
        this.onclickPopupMenuListner = mOnclickPopupMenuListner
    }

    interface OnclickListner {
        fun onclick(position: Int)
    }

    interface PopupOnclickListner {
        fun onclick(view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_eventlist_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvPartyName.text = mList[position].partyName
        holder.tvEventName.text = mList[position].eventname
        holder.btnStatus.text = CommonUtils.mGetStatus(mList[position].status!!)
        holder.tvStartDate.text = CommonUtils.parseDateAndToViewDate(mList[position].eventDate)

        holder.itemView.setOnClickListener {
            onclickListner.onclick(position)
        }

        holder.imgPopupMenu.setOnClickListener {
            onclickPopupMenuListner.onclick(holder.imgPopupMenu)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPartyName: TextView
        var tvEventName: TextView
        var btnStatus: AppCompatButton
        var tvnoPax: TextView
        var tvVanue: TextView
        var tvStartDate: TextView
        var tvEndDate: TextView
        var imgPopupMenu: ImageView

        init {
            tvPartyName = itemView.findViewById(R.id.tvPartyName)
            tvEventName = itemView.findViewById(R.id.tvEventName)
            btnStatus = itemView.findViewById(R.id.btnStatus)
            tvnoPax = itemView.findViewById(R.id.tvnoPax)
            tvVanue = itemView.findViewById(R.id.tvVanue)
            tvStartDate = itemView.findViewById(R.id.tvStartDate)
            tvEndDate = itemView.findViewById(R.id.tvEndDate)
            imgPopupMenu = itemView.findViewById(R.id.imgPopupMenu)
        }
    }
}