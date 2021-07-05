package com.saptarshidas.technohrms.ui.department

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.technohrms.base.BaseViewModel
import com.saptarshidas.technohrms.data.exchanges.department.AddDepartmentResponse
import com.saptarshidas.technohrms.data.exchanges.employee.GetDepartmentsResponse
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.DepartmentRepository
import com.saptarshidas.technohrms.data.repository.EmployeeRepository
import kotlinx.coroutines.launch

class DepartmentViewModel(application: Application): BaseViewModel(application) {

    private val repository: DepartmentRepository = DepartmentRepository(application)
    private val employeeRepository: EmployeeRepository = EmployeeRepository(application)

    private val getAddDepartmentResponse: MutableLiveData<Resource<AddDepartmentResponse>> = MutableLiveData()
    private val getDepartmentsResponse: MutableLiveData<Resource<GetDepartmentsResponse>> = MutableLiveData()
    private val getUpdateDepartmentResponse: MutableLiveData<Resource<Department>> = MutableLiveData()


    val addDeptLiveData: LiveData<Resource<AddDepartmentResponse>> = getAddDepartmentResponse
    val getDeptLiveData: LiveData<Resource<GetDepartmentsResponse>> = getDepartmentsResponse
    val getUpdatedDeptLiveData: LiveData<Resource<Department>> = getUpdateDepartmentResponse

    fun addDepartment(request: Department) = viewModelScope.launch {
        getAddDepartmentResponse.value = repository.addDepartment(request)
    }

    fun getAllDepartments() = viewModelScope.launch {
        getDepartmentsResponse.value = employeeRepository.getAllDepartments()
    }

    fun updateDepartment(request: Department) = viewModelScope.launch {
        getUpdateDepartmentResponse.value = repository.updateDepartment(request)
    }

}