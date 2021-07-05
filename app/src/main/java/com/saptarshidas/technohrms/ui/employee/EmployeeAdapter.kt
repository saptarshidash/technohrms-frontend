package com.saptarshidas.technohrms.ui.employee

import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.data.model.Employee
import kotlinx.android.synthetic.main.list_employee.view.*
import java.util.*
import kotlin.collections.ArrayList

class EmployeeAdapter<T>(l: OnRecyclerClickListener, list: ArrayList<T>?) : BaseAdapter<T>() {


    init {
        dataList = list!!
        listener = l
        layoutId = R.layout.list_employee
    }

    override fun onViewHolderBind(holder: ViewHolder, position: Int, data: Any?) {

        var employee: Employee = data as Employee

        holder.view.txtViewEmployeeName.text =employee.name
        holder.view.txtViewEmployeeDept.text =employee.department.name
        holder.view.txtViewNameInitial.text =employee.name.first().toString()
            .uppercase(Locale.getDefault())

        holder.view.txtViewEmpId.text = "ID - "+employee.id.toString()


    }



}