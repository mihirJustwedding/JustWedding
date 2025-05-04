package com.example.justweddingpro.ClientUi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justweddingpro.ClientUi.ClientHomeFragment.Companion.itemDetailsList
import com.example.justweddingpro.ClientUi.Request.AddOrderRequest
import com.example.justweddingpro.ClientUi.Response.ItemsDetail
import com.example.justweddingpro.R
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager

class HomeFoodItemListAdapter(var mcontext: Context, var mList: ArrayList<ItemsDetail>) :
    RecyclerView.Adapter<HomeFoodItemListAdapter.ViewHolder>() {

    lateinit var onclickListner: OnclickListner
    private var mTotalCount = 0

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_food_homelist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvFoodName.text = mList[position].getItemName()
        holder.tvFoodDescription.text = mList[position].getItemSlogan()
        var mCount = 1

        if (PreferenceManager.getPref(Constants.Preference.IS_WRITE_PERMISSION, false)!!) {
            holder.rlEnd.visibility = View.VISIBLE
        } else {
            holder.rlEnd.visibility = View.GONE
        }

        if (itemDetailsList!!.isNotEmpty())
            for (i in 0 until itemDetailsList?.size!!) {
                if (itemDetailsList!![i].getItemId() == mList[position].getItemId()!!) {
                    holder.rlCard.visibility = View.VISIBLE
                    holder.btnAddToCard.visibility = View.GONE
                    holder.mItemModel = itemDetailsList?.get(i)!!

                    mCount = holder.mItemModel.getQty()!!
                    holder.tvCount.text = mCount.toString()
                    itemDetailsList!![i].setQty(holder.mItemModel.getQty())

                    AddTotalItem()
                }
            }

        holder.btnAddToCard.setOnClickListener {
            holder.rlCard.visibility = View.VISIBLE
            holder.btnAddToCard.visibility = View.GONE
            mCount = 1
            holder.tvCount.text = mCount.toString()

            holder.mItemModel.setItemId(mList[position].getItemId())
            holder.mItemModel.setItemName(mList[position].getItemName())
            holder.mItemModel.setQty(holder.tvCount.text.toString().toInt())

            itemDetailsList?.add(holder.mItemModel)
            AddTotalItem()
        }

        holder.imgRemove.setOnClickListener {
            holder.rlCard.visibility = View.GONE
            holder.btnAddToCard.visibility = View.VISIBLE
            holder.tvCount.text = mCount.toString()
            itemDetailsList?.remove(holder.mItemModel)
            AddTotalItem()
        }

        holder.imgPlush.setOnClickListener {
            mCount++
            holder.tvCount.setText(mCount.toString())
            holder.mItemModel.setQty(holder.tvCount.text.toString().toInt())
            AddTotalItem()
        }

        holder.imgMinus.setOnClickListener {
            if (mCount != 1) {
                mCount--
                holder.tvCount.text = mCount.toString()
                holder.mItemModel.setQty(holder.tvCount.text.toString().toInt())
                AddTotalItem()
            } else {
                holder.rlCard.visibility = View.GONE
                holder.btnAddToCard.visibility = View.VISIBLE
                holder.tvCount.text = mCount.toString()
                itemDetailsList?.remove(holder.mItemModel)
                AddTotalItem()
            }
        }

        Glide.with(mcontext)
            .load(mList[position].getItemImage())
            .placeholder(R.drawable.slider3)
            .into(holder.mImgFood)
    }

    private fun AddTotalItem() {
        var countt = 0
        for (i in 0 until itemDetailsList?.size!!) {
            countt += itemDetailsList!![i].getQty()!!
        }
        mTotalCount = countt
        onclickListner.onclick(mTotalCount)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImgFood: ImageView
        var imgRemove: ImageView
        var imgPlush: ImageView
        var imgMinus: ImageView
        var tvFoodName: TextView
        var tvFoodDescription: TextView
        var tvCount: TextView
        var btnAddToCard: AppCompatButton
        var rlCard: RelativeLayout
        var rlEnd: RelativeLayout
        var mItemModel: AddOrderRequest.ItemDetail = AddOrderRequest.ItemDetail()

        init {
            mImgFood = itemView.findViewById(R.id.mImgFood)
            imgRemove = itemView.findViewById(R.id.imgRemove)
            rlCard = itemView.findViewById(R.id.rlCard)
            rlEnd = itemView.findViewById(R.id.rlEnd)
            tvFoodName = itemView.findViewById(R.id.tvFoodName)
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription)
            btnAddToCard = itemView.findViewById(R.id.btnAddToCard)
            imgPlush = itemView.findViewById(R.id.imgPlush)
            imgMinus = itemView.findViewById(R.id.imgMinus)
            tvCount = itemView.findViewById(R.id.tvCount)
        }
    }
}