package com.saptarshidas.technohrms.ui.auth

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.technohrms.base.BaseViewModel
import com.saptarshidas.technohrms.data.exchanges.auth.AuthRequest
import com.saptarshidas.technohrms.data.exchanges.auth.AuthResponse
import com.saptarshidas.technohrms.data.exchanges.auth.UserRegistrationRequest
import com.saptarshidas.technohrms.data.exchanges.auth.UserRegistrationResponse
import com.saptarshidas.technohrms.data.exchanges.employee.GetEmployeeResponse
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.AuthRepository
import com.saptarshidas.technohrms.data.repository.EmployeeRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : BaseViewModel(application) {

    private  val TAG = "AuthViewModel"
    private var authRepository: AuthRepository = AuthRepository(application)
    private var empRepository = EmployeeRepository(application)


    private var authResponse: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    private var regResponse: MutableLiveData<Resource<UserRegistrationResponse>> = MutableLiveData()
    private var getEmployeeResponse: MutableLiveData<Resource<GetEmployeeResponse>> = MutableLiveData()

    var authLiveData: LiveData<Resource<AuthResponse>> = authResponse
    var regLiveData: LiveData<Resource<UserRegistrationResponse>> = regResponse
    var employeesLiveData: LiveData<Resource<GetEmployeeResponse>> = getEmployeeResponse

    fun login(request: AuthRequest) = viewModelScope.launch {
        authResponse.value = authRepository.authenticate(request)
    }

    fun register(request: UserRegistrationRequest) = viewModelScope.launch {
        regResponse.value = authRepository.register(request)
    }

    fun getAllEmployees() = viewModelScope.launch {
        getEmployeeResponse.value = empRepository.getAllEmployees()
    }
}