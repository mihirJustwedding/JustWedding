package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.CreateEventActivity
import com.example.justweddingpro.ui.MenuActivity
import com.example.justweddingpro.ui.MyEventDetailsActivity
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager

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
        fun onclick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_eventlist_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvPartyName.text = "Party Name: ${mList[position].partyName}"
        holder.tvEventName.text = mList[position].eventname
        holder.btnStatus.text = CommonUtils.mGetStatus(mList[position].status!!)
        holder.tvStartDate.text = CommonUtils.parseDateAndToViewDate(mList[position].eventDate)
        holder.tvVanue.text = mList[position].venueName

        holder.btnStatus.backgroundTintList =
            (ColorStateList.valueOf(getRandomColor(mList[position].status!!)))
        //        holder.itemView.setOnClickListener {
        //            onclickListner.onclick(position)
        //        }

        holder.imgPopupMenu.setOnClickListener {
            onclickPopupMenuListner.onclick(holder.imgPopupMenu, position)
        }

        holder.imgView.setOnClickListener {
            PreferenceManager.setPref(
                Constants.Preference.Pref_EVENTId,
                mList[position].eventId.toString()
            )
            mcontext.startActivity(
                Intent(
                    mcontext,
                    MyEventDetailsActivity::class.java
                )
            )
        }

        holder.imgEdite.setOnClickListener {
            MyEventDetailsActivity.mIsEdite = true
            mcontext.startActivity(
                Intent(
                    mcontext,
                    CreateEventActivity::class.java
                )
            )
        }

        holder.imgMenuPlanning.setOnClickListener {
            mcontext.startActivity(
                Intent(
                    mcontext,
                    MenuActivity::class.java
                )
            )
        }
    }

    private fun getRandomColor(eventType: Int): Int {
        if (mcontext != null) {
            val resources: Resources = mcontext.getResources()
            return if (eventType == 0) {
                resources.getColor(R.color.inqury_event)
            } else if (eventType == 1) {
                resources.getColor(R.color.confirmed_event)
            } else if (eventType == 2) {
                resources.getColor(R.color.not_confirmed_event)
            } else {
                resources.getColor(R.color.not_confirmed_event)
            }
        }
        return 0
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
        var imgView: ImageView
        var imgEdite: ImageView
        var imgMenuPlanning: ImageView

        init {
            tvPartyName = itemView.findViewById(R.id.tvPartyName)
            tvEventName = itemView.findViewById(R.id.tvEventName)
            btnStatus = itemView.findViewById(R.id.btnStatus)
            tvnoPax = itemView.findViewById(R.id.tvnoPax)
            tvVanue = itemView.findViewById(R.id.tvVanue)
            tvStartDate = itemView.findViewById(R.id.tvStartDate)
            tvEndDate = itemView.findViewById(R.id.tvEndDate)
            imgPopupMenu = itemView.findViewById(R.id.imgPopupMenu)
            imgView = itemView.findViewById(R.id.imgView)
            imgEdite = itemView.findViewById(R.id.imgEdite)
            imgMenuPlanning = itemView.findViewById(R.id.imgMenuPlanning)
        }
    }
}