package com.saptarshidas.technohrms.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.Dashboard.DashboardResponse
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.menu_header.*


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private  val TAG = "HomeFragment"



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.getDashboardData()

        vModel.dashboardLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    txtViewTotalEmp.setText(it.data.totalEmployee.toString())
                    txtViewAttendance.setText(it.data.todayAttendance.toString())
                    txtViewTotalDept.setText(it.data.totalDepartment.toString())
                    txtViewPendingLeaves.setText(it.data.pendingLeaveList.size.toString())
                }
            }
        })
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initViews() {

    }

    override fun initActionView() {
        Log.d(TAG, "initActionView: ")
    }

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

}