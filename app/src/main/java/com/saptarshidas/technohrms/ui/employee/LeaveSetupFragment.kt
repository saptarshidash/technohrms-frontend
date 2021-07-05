package com.saptarshidas.technohrms.ui.employee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.model.LeaveSetup
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentLeaveSetupBinding
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_detailed_employee.*
import kotlinx.android.synthetic.main.fragment_leave_setup.*
import kotlinx.android.synthetic.main.fragment_leave_setup.edTxtEmpId
import kotlinx.android.synthetic.main.fragment_leave_setup.edTxtEmpName
import kotlinx.coroutines.launch

class LeaveSetupFragment : BaseFragment<FragmentLeaveSetupBinding, EmployeeViewModel>() {

    private val TAG = "LeaveSetupFragment"

    private lateinit var leaveTypeAdapter: ArrayAdapter<String>
    
    private val args: LeaveSetupFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.leaveSetupLiveData.observe(viewLifecycleOwner, {

            when(it){

                is Resource.Success->lifecycleScope.launch {
                    Log.d(TAG, "onViewCreated: ${it.data.message} ")
                    requireView().snackBar(it.data.message)
                }
                is Resource.Error-> {
                    handleApiError(it) {  }
                }

            }

        })
        
        
    }
    
    
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLeaveSetupBinding {
        return FragmentLeaveSetupBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        edTxtEmpId.setText(args.employee.id.toString())
        edTxtEmpName.setText(args.employee.name)

        leaveTypeAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, getLeaveType())
        leaveTypeTextView.setAdapter(leaveTypeAdapter)
    }

    override fun initActionView() {
        btnSaveSetup.setOnClickListener {
            val setup = LeaveSetup(
                args.employee,
                leaveTypeTextView.text.toString(),
                edTxtLeaveCount.text.toString().toInt()
            )

            vModel.saveLeaveSetup(setup)
            
        }

        btnViewSetup.setOnClickListener {
            val action = LeaveSetupFragmentDirections.actionLeaveSetupFragmentToViewLeaveSetupFragment(args.employee)
            navController.navigate(action)
        }
    }

    override fun getViewModel(): Class<EmployeeViewModel> {
        return EmployeeViewModel::class.java
    }

    private fun getLeaveType(): ArrayList<String>{
        val list = ArrayList<String>()

        list.add("Sick Leave")
        list.add("Travel Leave")
        list.add("Annual Leave")
        list.add("Maternity Leave")

        return list
    }

}