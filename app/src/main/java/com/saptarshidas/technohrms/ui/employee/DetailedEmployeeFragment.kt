package com.saptarshidas.technohrms.ui.employee

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.model.Designation
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentDetailedEmployeeBinding
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_detailed_employee.*
import kotlinx.coroutines.launch

class DetailedEmployeeFragment : BaseFragment<FragmentDetailedEmployeeBinding, EmployeeViewModel>() {

    private val TAG = "DetailedEmployeeFragmen"
    private val args: DetailedEmployeeFragmentArgs by navArgs()

    private lateinit var deptAdapter: ArrayAdapter<Department>

    private lateinit var designationAdapter: ArrayAdapter<Designation>

    private lateinit var workTypeAdapter: ArrayAdapter<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.getAllDepartments()
        vModel.getAllDesignations()
        vModel.departmentsLiveData.observe(viewLifecycleOwner,{

            when(it){

                is Resource.Success->lifecycleScope.launch {
                    deptAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, it.data.departmentDtoList)
                    departmentTextView.setAdapter(deptAdapter)
                }
                is Resource.Error-> {
                    requireView().snackBar("Unable to load departments") {
                        vModel.getAllDepartments()
                    }
                }
            }

        })

        vModel.designationsLiveData.observe(viewLifecycleOwner,{
            when(it){

                is Resource.Success->lifecycleScope.launch {
                    designationAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, it.data.designationDtoList)
                    designationTextView.setAdapter(designationAdapter)
                }
                is Resource.Error-> {
                    requireView().snackBar("Unable to load designations") {
                        vModel.getAllDesignations()
                    }
                }
            }
        })

        vModel.empUpdationLiveData.observe(viewLifecycleOwner,{
            when(it){

                is Resource.Success->lifecycleScope.launch {
                    requireView().snackBar("Employee updated successfully")
                }
                is Resource.Error-> {
                    requireView().snackBar("Unable to update employee, try again") {
                    }
                }
            }
        })

    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailedEmployeeBinding {
        return FragmentDetailedEmployeeBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        edTxtEmpId.setText(args.employee.id.toString())
        edTxtEmpName.setText(args.employee.name)
        edTxtEmpDob.setText(args.employee.dob.toString())
        edTxtEmpJoining.setText((args.employee.joiningDate.toString()))
        departmentTextView.setText("${args.employee.department?.id} ${args.employee.department?.name}")
        designationTextView.setText("${args.employee.designation?.id} ${args.employee.designation?.name}")
        edTxtEmpEmail.setText(args.employee.email)
        edTxtEmpPhone.setText(args.employee.mobile)
        workTypeTextView.setText(args.employee.workType)

        workTypeAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, getWorkTypeList())
        workTypeTextView.setAdapter(workTypeAdapter)


    }

    override fun initActionView() {

        btnUpdateEmp.setOnClickListener {

            val deptId = departmentTextView.text.toString().split(" ").toTypedArray()[0].toInt()
            val designationId = designationTextView.text.toString().split(" ").toTypedArray()[0].toInt()
            
            val employee = Employee(
                args.employee.id,
                args.employee.name,
                Department(args.employee.department.name, deptId, args.employee.department.name, args.employee.department.totalEmployee),
                Designation(args.employee.designation.name, designationId, args.employee.designation.name),
                edTxtEmpDob.text.toString(),
                edTxtEmpEmail.text.toString(),
                edTxtEmpJoining.text.toString(),
                edTxtEmpPhone.text.toString(),
                workTypeTextView.text.toString()
            )

            vModel.updateEmployee(employee)
            Log.d(TAG, "initActionView: $employee")
        }

        btnSetupLeave.setOnClickListener {
            val action = DetailedEmployeeFragmentDirections.actionDetailedEmployeeFragmentToLeaveSetupFragment(args.employee)
            navController.navigate(action)
        }
    }

    override fun getViewModel(): Class<EmployeeViewModel> {
        return EmployeeViewModel::class.java
    }

    private fun getWorkTypeList(): ArrayList<String>{

        val list = ArrayList<String>()

        list.add("PERMANENT")
        list.add("INTERN")
        list.add("CONTRACT")
        list.add("PART TIME")
        list.add("PROBATION")

        return list
    }



}