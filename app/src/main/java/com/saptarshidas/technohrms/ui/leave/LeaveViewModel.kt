package com.saptarshidas.technohrms.ui.leave

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.technohrms.base.BaseViewModel
import com.saptarshidas.technohrms.data.exchanges.employee.GetEmployeeResponse
import com.saptarshidas.technohrms.data.exchanges.leave.ApproveLeaveRequest
import com.saptarshidas.technohrms.data.exchanges.leave.ApproveLeaveResponse
import com.saptarshidas.technohrms.data.exchanges.leave.GetLeaveRequestResponse
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.EmployeeRepository
import com.saptarshidas.technohrms.data.repository.LeaveRepository
import kotlinx.coroutines.launch

class LeaveViewModel(application: Application): BaseViewModel(application) {

    private val repository: LeaveRepository = LeaveRepository(application)
    private val empRepository = EmployeeRepository(application)

    private val getAllLeaveRequestResponse: MutableLiveData<Resource<GetLeaveRequestResponse>> = MutableLiveData()
    private val getApproveLeaveRequestResponse: MutableLiveData<Resource<ApproveLeaveResponse>> = MutableLiveData()
    private var getEmployeeResponse: MutableLiveData<Resource<GetEmployeeResponse>> = MutableLiveData()

    val leaveRequestsLiveData: LiveData<Resource<GetLeaveRequestResponse>> = getAllLeaveRequestResponse
    val approveRequestLiveData: LiveData<Resource<ApproveLeaveResponse>> = getApproveLeaveRequestResponse
    var employeesLiveData: LiveData<Resource<GetEmployeeResponse>> = getEmployeeResponse

    fun getAllLeaveRequest() = viewModelScope.launch {
        getAllLeaveRequestResponse.value = repository.getAllLeaveRequests()
    }

    fun getAllLeaveRequestByEmployee(id: Int) = viewModelScope.launch {
        getAllLeaveRequestResponse.value = repository.getAllLeaveRequestByEmployee(id)
    }


    fun approveLeaveRequest(request: ApproveLeaveRequest) = viewModelScope.launch {
        getApproveLeaveRequestResponse.value = repository.approveLeaveRequest(request)
    }

    fun getAllEmployees() = viewModelScope.launch {
        getEmployeeResponse.value = empRepository.getAllEmployees()
    }



}