package com.saptarshidas.technohrms.ui.department

import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.data.model.Department
import kotlinx.android.synthetic.main.list_departments.view.*

class DepartmentAdapter<T>(l: BaseAdapter.OnRecyclerClickListener,list: ArrayList<T>?): BaseAdapter<T>() {
    init {
        dataList = list!!
        listener = l
        layoutId = R.layout.list_departments
    }
    override fun onViewHolderBind(holder: ViewHolder, position: Int, data: Any?) {

        var department = data as Department

        holder.view.txtViewDeptName.text = department.name
        holder.view.txtViewDeptDesc.text = department.description

    }
}