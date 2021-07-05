package com.saptarshidas.technohrms.ui.designation

import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.data.model.Designation
import kotlinx.android.synthetic.main.list_designations.view.*

class DesignationAdapter<T>(l: BaseAdapter.OnRecyclerClickListener, list: ArrayList<T>?): BaseAdapter<T>() {

    init {
        dataList = list!!
        listener = l
        layoutId = R.layout.list_designations
    }


    override fun onViewHolderBind(holder: ViewHolder, position: Int, data: Any?) {

        val designation = data as Designation

        holder.view.txtViewDesignationName.text = designation.name
        holder.view.txtViewDesignationDesc.text = designation.description

    }
}