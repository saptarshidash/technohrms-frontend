package com.saptarshidas.technohrms.ui.employee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.employee.LeaveSetupDto
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.BaseRepository
import com.saptarshidas.technohrms.databinding.FragmentViewLeaveSetupBinding
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_employee_list.*
import kotlinx.android.synthetic.main.fragment_view_leave_setup.*

class ViewLeaveSetupFragment : BaseFragment<FragmentViewLeaveSetupBinding, EmployeeViewModel>(),
BaseAdapter.OnRecyclerClickListener{

    private  val TAG = "ViewLeaveSetupFragment"
    private val args: ViewLeaveSetupFragmentArgs by navArgs()

    private lateinit var leaveSetupAdapter: LeaveSetupAdapter<LeaveSetupDto>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        vModel.getAllLeaveSetups(args.employee.id)
        
        vModel.allLeaveSetupLiveData.observe(viewLifecycleOwner, {
            
            when(it){
                is Resource.Success-> {
                    Log.d(TAG, "onViewCreated: ${it.data.leaveSetupDtoList}")
                    leaveSetupAdapter.updateList(ArrayList(it.data.leaveSetupDtoList))
                }

            }
            
        })
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentViewLeaveSetupBinding {
        return FragmentViewLeaveSetupBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        recyclerLeaveSetups.layoutManager = LinearLayoutManager(requireContext())
        leaveSetupAdapter = LeaveSetupAdapter(this, ArrayList<LeaveSetupDto>())
        recyclerLeaveSetups.adapter = leaveSetupAdapter
    }

    override fun initActionView() {
        Log.d(TAG, "initActionView: ")
    }

    override fun getViewModel(): Class<EmployeeViewModel> {
        return EmployeeViewModel::class.java
    }

    override fun onClick(data: Any?) {
        TODO("Not yet implemented")
    }

}