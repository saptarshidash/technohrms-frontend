package com.saptarshidas.technohrms.ui.attendance

import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.data.exchanges.attendance.GetAttendanceResponseItem
import kotlinx.android.synthetic.main.list_attendance.view.*

class AttendanceAdapter<T>(l: OnRecyclerClickListener, list: ArrayList<T>?) : BaseAdapter<T>() {

    init {
        dataList = list!!
        listener = l
        layoutId = R.layout.list_attendance
    }

    override fun onViewHolderBind(holder: ViewHolder, position: Int, data: Any?) {

        val attendance = data as GetAttendanceResponseItem

        holder.view.txtViewEmpName.text = attendance.employee.name
        holder.view.txtViewDate.text ="\uD83D\uDCC5 "+ attendance.date
        holder.view.txtViewInTime.text ="\uD83D\uDD52 In Time "+ attendance.inTime
        holder.view.txtViewOutTime.text ="\uD83D\uDD52 Out Time "+ attendance.outTime
        if(attendance.outTime == null){
            holder.view.txtViewOutTime.text ="\uD83D\uDD52 Out Time 00:00:00 "
        }

    }
}