package com.saptarshidas.technohrms.ui.training

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.technohrms.base.BaseViewModel
import com.saptarshidas.technohrms.data.exchanges.employee.GetEmployeeResponse
import com.saptarshidas.technohrms.data.exchanges.training.*
import com.saptarshidas.technohrms.data.model.Training
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.EmployeeRepository
import com.saptarshidas.technohrms.data.repository.TrainingRepository
import kotlinx.coroutines.launch

class TrainingViewModel(application: Application): BaseViewModel(application) {

    private val repository: TrainingRepository = TrainingRepository(application)
    private val empRepository: EmployeeRepository = EmployeeRepository(application)

    private val getAllTrainingResponse: MutableLiveData<Resource<GetAllTrainingResponse>> = MutableLiveData()
    private val getAddTrainingResponse: MutableLiveData<Resource<AddTrainingResponse>> = MutableLiveData()
    private val getAllAssignedTrainingResponse: MutableLiveData<Resource<GetAssignedTrainingResponse>> = MutableLiveData()
    private var getEmployeeResponse: MutableLiveData<Resource<GetEmployeeResponse>> = MutableLiveData()
    private var getAssignTrainingResponse: MutableLiveData<Resource<AssignTrainingResponse>> = MutableLiveData()
    private var getAssignedTrainingUpdateResponse: MutableLiveData<Resource<UpdateAssignedTrainingResponse>> = MutableLiveData()


    val allTrainingLiveData: LiveData<Resource<GetAllTrainingResponse>> = getAllTrainingResponse
    val addTrainingLiveData: LiveData<Resource<AddTrainingResponse>> = getAddTrainingResponse
    val assignedTrainingLiveData: LiveData<Resource<GetAssignedTrainingResponse>> = getAllAssignedTrainingResponse
    var employeesLiveData: LiveData<Resource<GetEmployeeResponse>> = getEmployeeResponse
    var assignTrainingLiveData: LiveData<Resource<AssignTrainingResponse>> = getAssignTrainingResponse
    var updateAssignedTrainingLiveData:LiveData<Resource<UpdateAssignedTrainingResponse>> = getAssignedTrainingUpdateResponse

    fun getAllTraining() = viewModelScope.launch {
        getAllTrainingResponse.value = repository.getAllTraining()
    }

    fun addTraining(request: Training) = viewModelScope.launch {
        getAddTrainingResponse.value = repository.addTraining(request)
    }

    fun getAllAssignedTraining() = viewModelScope.launch {
        getAllAssignedTrainingResponse.value = repository.getAllAssignedTraining()
    }

    fun getAllAssignedTrainingByEmployee(id: Int) = viewModelScope.launch {
        getAllAssignedTrainingResponse.value = repository.getAllAssignedTrainingByEmployee(id)
    }

    fun getAllAssignedTrainingByTraining(id: Int) = viewModelScope.launch {
        getAllAssignedTrainingResponse.value = repository.getAllAssignedTrainingByTraining(id)
    }

    fun getAllEmployees() = viewModelScope.launch {
        getEmployeeResponse.value = empRepository.getAllEmployees()
    }

    fun assignTraining(request: AssignTrainingRequest) = viewModelScope.launch {
        getAssignTrainingResponse.value = repository.assignTraining(request)
    }

    fun updateAssignedTraining(id: Int, request: UpdateAssignedTrainingRequest) = viewModelScope.launch {
       getAssignedTrainingUpdateResponse.value =  repository.updateAssignedTraining(id, request)
    }
}