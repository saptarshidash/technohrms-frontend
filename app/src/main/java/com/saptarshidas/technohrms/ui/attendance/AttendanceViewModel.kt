package com.saptarshidas.technohrms.ui.attendance

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.technohrms.base.BaseViewModel
import com.saptarshidas.technohrms.data.exchanges.attendance.GetAttendanceResponse
import com.saptarshidas.technohrms.data.exchanges.employee.GetEmployeeResponse
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.AttendanceRepository
import com.saptarshidas.technohrms.data.repository.EmployeeRepository
import kotlinx.coroutines.launch

class AttendanceViewModel(application: Application): BaseViewModel(application) {

    private val repository = AttendanceRepository(application)
    private val empRepository = EmployeeRepository(application)

    private val getAllAttendanceByDateResponse: MutableLiveData<Resource<GetAttendanceResponse>> = MutableLiveData()
    private var getEmployeeResponse: MutableLiveData<Resource<GetEmployeeResponse>> = MutableLiveData()

    val attendanceResponseLiveData: LiveData<Resource<GetAttendanceResponse>> = getAllAttendanceByDateResponse
    var employeesLiveData: LiveData<Resource<GetEmployeeResponse>> = getEmployeeResponse


    fun getAllAttendanceByDate(st: String, end: String) = viewModelScope.launch{
        getAllAttendanceByDateResponse.value = repository.getAllAttendanceByDate(st, end)
    }

    fun getAllEmployees() = viewModelScope.launch {
        getEmployeeResponse.value = empRepository.getAllEmployees()
    }

    fun getAllAttendanceByEmpAndDate(id: Int, st: String, end: String) = viewModelScope.launch{
        getAllAttendanceByDateResponse.value = repository.getAllAttendanceByEmpAndDate(id, st, end)
    }


}