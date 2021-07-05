package com.saptarshidas.technohrms.ui.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saptarshidas.technohrms.base.BaseViewModel
import com.saptarshidas.technohrms.data.exchanges.Dashboard.DashboardResponse
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.DashboardRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): BaseViewModel(application) {

    private val repository: DashboardRepository = DashboardRepository(application)

    private val getDashboardDataResponse: MutableLiveData<Resource<DashboardResponse>> = MutableLiveData()

    val dashboardLiveData: LiveData<Resource<DashboardResponse>> = getDashboardDataResponse

    fun getDashboardData() = viewModelScope.launch {
        getDashboardDataResponse.value = repository.getDashboardData()
    }

}