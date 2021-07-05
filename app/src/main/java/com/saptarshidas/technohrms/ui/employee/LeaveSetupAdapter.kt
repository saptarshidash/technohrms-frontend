package com.saptarshidas.technohrms.ui.employee

import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.data.exchanges.employee.LeaveSetupDto
import kotlinx.android.synthetic.main.list_leave_setups.view.*

class LeaveSetupAdapter<T>(l: OnRecyclerClickListener, list: ArrayList<T>?): BaseAdapter<T>() {


    init {
        dataList = list!!
        listener = l
        layoutId = R.layout.list_leave_setups
    }



    override fun onViewHolderBind(holder: ViewHolder, position: Int, data: Any?) {

        var leaveSetupDto = data as LeaveSetupDto
        holder.view.txtViewLeaveType.text = leaveSetupDto.leaveName
        holder.view.txtViewTotal.text = "Total - " + leaveSetupDto.totalLeave.toString()
        holder.view.txtViewPending.text ="Pending - " +leaveSetupDto.pendingLeave.toString()
        holder.view.txtViewUsed.text ="Used - " + leaveSetupDto.usedLeave.toString()
    }


}