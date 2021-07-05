package com.saptarshidas.technohrms.ui.employee

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.technohrms.base.BaseViewModel
import com.saptarshidas.technohrms.data.exchanges.employee.*
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.model.LeaveSetup
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel(application: Application): BaseViewModel(application) {

    private val employeeRepository = EmployeeRepository(application)

    private var getEmployeeResponse: MutableLiveData<Resource<GetEmployeeResponse>> = MutableLiveData()
    private var getDepartmentsResponse: MutableLiveData<Resource<GetDepartmentsResponse>> = MutableLiveData()
    private var getDesignationsResponse: MutableLiveData<Resource<GetDesignationsResponse>> = MutableLiveData()
    private var getUpdateEmpResponse: MutableLiveData<Resource<Employee>> = MutableLiveData()
    private var getLeaveSetupResponse: MutableLiveData<Resource<GetLeaveSetupResponse>> = MutableLiveData()
    private var getAllLeaveSetupResponse: MutableLiveData<Resource<GetAllLeaveSetupResponse>> = MutableLiveData()
    private var getAddEmployeeResponse: MutableLiveData<Resource<AddEmployeeResponse>> = MutableLiveData()

    var employeesLiveData: LiveData<Resource<GetEmployeeResponse>> = getEmployeeResponse
    var departmentsLiveData: LiveData<Resource<GetDepartmentsResponse>> = getDepartmentsResponse
    var designationsLiveData: LiveData<Resource<GetDesignationsResponse>> = getDesignationsResponse
    var empUpdationLiveData: LiveData<Resource<Employee>> = getUpdateEmpResponse
    var leaveSetupLiveData: LiveData<Resource<GetLeaveSetupResponse>> = getLeaveSetupResponse
    var allLeaveSetupLiveData: LiveData<Resource<GetAllLeaveSetupResponse>> = getAllLeaveSetupResponse
    var AddEmployeeLiveData: LiveData<Resource<AddEmployeeResponse>> = getAddEmployeeResponse


    fun getAllEmployees() = viewModelScope.launch {
        getEmployeeResponse.value = employeeRepository.getAllEmployees()
    }

    fun getAllDepartments() = viewModelScope.launch {
        getDepartmentsResponse.value = employeeRepository.getAllDepartments()
    }

    fun getAllDesignations() = viewModelScope.launch {
        getDesignationsResponse.value = employeeRepository.getAllDesignations()
    }

    fun updateEmployee(employee: Employee) = viewModelScope.launch {
        getUpdateEmpResponse.value = employeeRepository.updateEmployee(employee)
    }

    fun saveLeaveSetup(setUp: LeaveSetup) = viewModelScope.launch {
        getLeaveSetupResponse.value = employeeRepository.saveLeaveSetup(setUp)
    }

    fun getAllLeaveSetups(id: Int) = viewModelScope.launch {
        getAllLeaveSetupResponse.value = employeeRepository.getAllLeaveSetups(id)
    }

    fun addEmployee(employee: Employee) = viewModelScope.launch {
        getAddEmployeeResponse.value = employeeRepository.addEmployee(employee)
    }


}