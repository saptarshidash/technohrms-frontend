package com.saptarshidas.technohrms.ui.designation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.technohrms.base.BaseViewModel
import com.saptarshidas.technohrms.data.exchanges.designation.AddDesignationRequest
import com.saptarshidas.technohrms.data.exchanges.designation.AddDesignationResponse
import com.saptarshidas.technohrms.data.exchanges.employee.GetDesignationsResponse
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.DesignationRepository
import com.saptarshidas.technohrms.data.repository.EmployeeRepository
import kotlinx.coroutines.launch

class DesignationViewModel(application: Application): BaseViewModel(application) {

    private val designationRepository =  DesignationRepository(application)

    private val employeeRepository = EmployeeRepository(application)

    private val getAddDesignationResponse: MutableLiveData<Resource<AddDesignationResponse>> = MutableLiveData()
    private val getDesignationResponse: MutableLiveData<Resource<GetDesignationsResponse>> = MutableLiveData()

    val addDesignationLiveData: LiveData<Resource<AddDesignationResponse>> = getAddDesignationResponse
    val designationLiveData: LiveData<Resource<GetDesignationsResponse>> =  getDesignationResponse


    fun addDesignation(request: AddDesignationRequest) = viewModelScope.launch {
        getAddDesignationResponse.value = designationRepository.addDesignation(request)
    }

    fun getAllDesignations() = viewModelScope.launch{
        getDesignationResponse.value = employeeRepository.getAllDesignations()
    }

}