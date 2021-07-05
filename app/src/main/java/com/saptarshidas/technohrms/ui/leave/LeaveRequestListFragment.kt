package com.saptarshidas.technohrms.ui.leave

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.leave.LeaveRequestDto
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.model.Designation
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentLeaveRequestListBinding
import com.saptarshidas.technohrms.utils.handleApiError
import kotlinx.android.synthetic.main.fragment_attendance_list.*
import kotlinx.android.synthetic.main.fragment_leave_request_list.*

class LeaveRequestListFragment : BaseFragment<FragmentLeaveRequestListBinding, LeaveViewModel>(),
BaseAdapter.OnRecyclerClickListener{

    private val TAG = "LeaveRequestListFragmen"
    private lateinit var adapter : LeaveRequestAdapter<LeaveRequestDto>

    private var employeeList: MutableList<Employee>? = null

    private val blankSpinnerOpt = Employee(
        0,
        "All",
        Department("",0,"",0),
        Designation("", 0, ""),
        "", "","", "", ""
    )

    private var empId = 0
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerLeaveRequests.showShimmer()
        vModel.getAllLeaveRequest()
        vModel.getAllEmployees()
        
        vModel.leaveRequestsLiveData.observe(viewLifecycleOwner, {
            recyclerLeaveRequests.hideShimmer()
            when(it){
                is Resource.Success -> { adapter.updateList(ArrayList(it.data.leaveRequestDtoList))}
                
                is Resource.Error -> { handleApiError(it){}}
            }
        })

        vModel.employeesLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {

                    initSpinner(ArrayList(it.data.employeeList))
                }
                is Resource.Error -> {
                    handleApiError(it){}
                }
            }
        })
        
    }
    
    
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLeaveRequestListBinding {
        return FragmentLeaveRequestListBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        recyclerLeaveRequests.layoutManager = LinearLayoutManager(requireContext())
        adapter = LeaveRequestAdapter(this, ArrayList())
        recyclerLeaveRequests.adapter = adapter
    }

    override fun initActionView() {
        Log.d(TAG, "initActionView: ")
    }

    override fun getViewModel(): Class<LeaveViewModel> {
        return LeaveViewModel::class.java
    }

    override fun onClick(data: Any?) {
        val action = LeaveRequestListFragmentDirections
            .actionLeaveRequestListFragmentToDetailedLeaveRequestFragment(data as LeaveRequestDto)
        navController.navigate(action)
    }

    private fun initSpinner(list: ArrayList<Employee>) {

        list.add(blankSpinnerOpt)
        employeeList = list
        leaveSpinner.item = employeeList

        leaveSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                empId = (employeeList as ArrayList<Employee>).get(position).id

                if(empId == 0){
                    vModel.getAllLeaveRequest()
                }else{
                    vModel.getAllLeaveRequestByEmployee(empId)
                }


            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
    }

}