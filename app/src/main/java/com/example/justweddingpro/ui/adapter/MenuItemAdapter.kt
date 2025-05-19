package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justweddingpro.Network.RequestModel.AddEventMenuPlanRequest
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.Response.MenuItemDetailsResponse

class MenuItemAdapter(
    var mcontext: Context,
    var mList: List<MenuItemDetailsResponse.MenuItemDetail>
) :
    RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {
    lateinit var onclickListner: OnclickListner
    lateinit var onSelectedListner: OnSeletedListner

    companion object {
        var mSelectedItemList: ArrayList<AddEventMenuPlanRequest.EventMenuPlanDetail>? = ArrayList()
        var mSelectedItemId: ArrayList<String>? = ArrayList()
    }

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    fun SetOnSelectedListner(onSelectedListner: OnSeletedListner) {
        this.onSelectedListner = onSelectedListner
    }

    interface OnclickListner {
        fun onclick(position: Int)
    }

    interface OnSeletedListner {
        fun onselected(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_menu_planning, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mList[position].getIsSelected() == true) {
            holder.mAddIcon.setImageResource(R.drawable.right_icon)
        } else {
            holder.mAddIcon.setImageResource(R.drawable.add_icon)
        }

        AddTotalItem()

        holder.llAdd.setOnClickListener { holder.mAddIcon.performClick() }

        holder.mAddIcon.setOnClickListener {
            val mEventMenuPlanDetail = AddEventMenuPlanRequest.EventMenuPlanDetail()
            mEventMenuPlanDetail.setItemId(mList[position].getId())
            mEventMenuPlanDetail.setMenuName(mList[position].getItemname())
            mEventMenuPlanDetail.setMenuSortorder(0)
            mEventMenuPlanDetail.setMenuCategoryId(mList[position].getMenucategoryId())
            mEventMenuPlanDetail.setItemSortorder(0)
            mEventMenuPlanDetail.setEventmenudetailsId(mList[position].getSubmenuId())
            mEventMenuPlanDetail.setInstruction(mList[position].getItemslogan())
            mEventMenuPlanDetail.setIsadditionalcounter(0)
            mEventMenuPlanDetail.setItemName(mList[position].getItemname())

            if (mList[position].getIsSelected() == false) {
                if (!mSelectedItemId?.contains(mEventMenuPlanDetail.getItemId().toString())!!) {
                    holder.mAddIcon.setImageResource(R.drawable.right_icon)
                    mList[position].setIsSelected(true)
                    mSelectedItemList?.add(mEventMenuPlanDetail)
                    mSelectedItemId!!.add(mEventMenuPlanDetail.getItemId().toString())
                }
            } else {
                holder.mAddIcon.setImageResource(R.drawable.add_icon)
                mList[position].setIsSelected(false)
                for (i in 0 until mSelectedItemList?.size!!) {
                    if (mSelectedItemList!![i].getItemId() == mEventMenuPlanDetail.getItemId()) {
                        mSelectedItemList!!.removeAt(i)
                        break
                    }
                }
//                mSelectedItemList?.forEach {
//                    if (it.getItemId() == mEventMenuPlanDetail.getItemId()) {
//                        mSelectedItemList!!.remove(it)
//                    }
//                }
                mSelectedItemId!!.remove(mEventMenuPlanDetail.getItemId().toString())
            }
            AddTotalItem()
        }

        holder.tvProductName.setText(mList[position].getItemname())

        Glide.with(mcontext)
            .load(mList[position].getItemimage())
            .placeholder(R.drawable.slider3)
            .into(holder.ivImageview)

        holder.ivImageview.setOnClickListener {
            onclickListner.onclick(position)
        }
    }

    private fun AddTotalItem() {
//        if (mSelectedItemId?.isNotEmpty()!!) {
        onSelectedListner.onselected(mSelectedItemId!!.size)
//        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivImageview: ImageView
        var mAddIcon: ImageView
        var tvProductName: TextView
        var tvRating: TextView
        var tvCal: TextView
        var llAdd: LinearLayout

        init {
            ivImageview = itemView.findViewById(R.id.ivImageview)
            mAddIcon = itemView.findViewById(R.id.mAddIcon)
            tvProductName = itemView.findViewById(R.id.tvProductName)
            tvRating = itemView.findViewById(R.id.tvRating)
            tvCal = itemView.findViewById(R.id.tvCal)
            llAdd = itemView.findViewById(R.id.llAdd)
        }
    }
}