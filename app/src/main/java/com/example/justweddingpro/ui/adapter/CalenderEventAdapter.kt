package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.MyEventDetailsActivity
import com.example.justweddingpro.ui.Response.EventListResponse
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager

class CalenderEventAdapter(
    var mcontext: Context,
    var mList: ArrayList<EventListResponse.EventDetail>
) :
    RecyclerView.Adapter<CalenderEventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_calender_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvName.setText(mList[position].eventName)
        holder.tvDate.setText(CommonUtils.parseDateAndToViewDate(mList[position].eventDate))
        holder.itemView.setOnClickListener {
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

        holder.cardView.setCardBackgroundColor(Color.parseColor(mList[position].color))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var tvDate: TextView
        var ivOption: ImageView
        var cardView: CardView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvDate = itemView.findViewById(R.id.tvDate)
            ivOption = itemView.findViewById(R.id.ivOption)
            cardView = itemView.findViewById(R.id.cardView)
        }
    }
}