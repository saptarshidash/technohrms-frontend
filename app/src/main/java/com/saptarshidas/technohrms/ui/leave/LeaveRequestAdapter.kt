package com.saptarshidas.technohrms.ui.leave

import android.graphics.Color
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.data.exchanges.leave.LeaveRequestDto
import kotlinx.android.synthetic.main.list_leave_request.view.*

class LeaveRequestAdapter<T>(l: OnRecyclerClickListener, list: ArrayList<T>?) : BaseAdapter<T>() {

    init {
        dataList = list!!
        listener = l
        layoutId = R.layout.list_leave_request
    }

    override fun onViewHolderBind(holder: ViewHolder, position: Int, data: Any?) {

        val leaveDto = data as LeaveRequestDto

        holder.view.txtViewLeaveName.text = leaveDto.leaveName
        holder.view.txtViewEmpName.text = "Requested By "+leaveDto.employee.name
        holder.view.txtViewLeaveFrom.text = leaveDto.startDate+" TO "
        holder.view.textViewLeaveTo.text = leaveDto.endDate

        if(leaveDto.status.equals("APPROVED")){
            holder.view.imgLeaveStatus.setBackgroundResource(R.drawable.ic_approved)
        }else if(leaveDto.status.equals("REJECTED")){
            holder.view.imgLeaveStatus.setBackgroundResource(R.drawable.ic_rejected)
        }else{
            holder.view.imgLeaveStatus.setBackgroundResource(R.drawable.ic_pending)
        }


    }
}