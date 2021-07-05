package com.saptarshidas.technohrms.ui.leave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.leave.ApproveLeaveRequest
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentDetailedLeaveRequestBinding
import com.saptarshidas.technohrms.ui.employee.DetailedEmployeeFragmentArgs
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_detailed_leave_request.*


class DetailedLeaveRequestFragment : BaseFragment<FragmentDetailedLeaveRequestBinding, LeaveViewModel>() {

    private val args: DetailedLeaveRequestFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.approveRequestLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> { requireView().snackBar(it.data.message)}

                is Resource.Error -> {handleApiError(it){}}
            }
        })
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailedLeaveRequestBinding {
        return FragmentDetailedLeaveRequestBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        edTxtLeaveReqId.setText(args.leaveRequest.id.toString())
        edTxtLeaveName.setText(args.leaveRequest.leaveName)
        edTxtFromDate.setText(args.leaveRequest.startDate)
        edTxtToDate.setText(args.leaveRequest.endDate)
        edTxtEmployee.setText(args.leaveRequest.employee.name)
        edTxtLeaveRequestStatus.setText(args.leaveRequest.status)
        edTxtLeaveReason.setText(args.leaveRequest.reason)

    }

    override fun initActionView() {
        btnReject.setOnClickListener{
            val request = ApproveLeaveRequest(
                args.leaveRequest.id,
                "REJECTED"
            )
            vModel.approveLeaveRequest(request)
        }

        btnApprove.setOnClickListener{
            val request = ApproveLeaveRequest(
                args.leaveRequest.id,
                "APPROVED"
            )
            vModel.approveLeaveRequest(request)
        }
    }

    override fun getViewModel(): Class<LeaveViewModel> {
        return LeaveViewModel::class.java
    }

}