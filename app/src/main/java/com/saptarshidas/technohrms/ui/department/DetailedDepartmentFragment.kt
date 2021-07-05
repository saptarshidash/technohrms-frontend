package com.saptarshidas.technohrms.ui.department

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentDetailedDepartmentBinding
import com.saptarshidas.technohrms.ui.employee.DetailedEmployeeFragmentArgs
import com.saptarshidas.technohrms.utils.checkEdTextValidation
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_detailed_department.*

class DetailedDepartmentFragment : BaseFragment<FragmentDetailedDepartmentBinding, DepartmentViewModel>() {

    private  val TAG = "DetailedDepartmentFragm"
    private val args: DetailedDepartmentFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.getUpdatedDeptLiveData.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success -> { requireView().snackBar("Department updated") }

                is Resource.Error -> { requireView().snackBar("Update failed, try again") }
            }
        })


    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailedDepartmentBinding {
        return FragmentDetailedDepartmentBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        edTxtDeptId.setText(args.department.id.toString())
        edTxtEmpTotal.setText(args.department.totalEmployee.toString())
        edTxtDeptName.setText(args.department.name)
        edTxtDeptDesc.setText(args.department.description)
    }

    override fun initActionView() {
        btnUpdateDept.setOnClickListener {

            if(validate()){
                val dept = Department(
                    edTxtDeptDesc.text.toString(),
                    args.department.id,
                    edTxtDeptName.text.toString(),
                    args.department.totalEmployee
                )

                vModel.updateDepartment(dept)
            }



        }
    }

    override fun getViewModel(): Class<DepartmentViewModel> {
        return DepartmentViewModel::class.java
    }

    private fun validate(): Boolean{

        val list = arrayListOf<EditText>()
        list.addAll(listOf(edTxtDeptDesc, edTxtDeptName))
        return checkEdTextValidation(list)
    }


}