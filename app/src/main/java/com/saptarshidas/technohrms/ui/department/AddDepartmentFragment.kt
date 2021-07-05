package com.saptarshidas.technohrms.ui.department

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentAddDepartmentBinding
import com.saptarshidas.technohrms.utils.checkAutoTxtViewValidation
import com.saptarshidas.technohrms.utils.checkEdTextValidation
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_add_department.*
import kotlinx.android.synthetic.main.fragment_add_employee.*


class AddDepartmentFragment : BaseFragment<FragmentAddDepartmentBinding,DepartmentViewModel>() {

    private val TAG = "AddDepartmentFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.addDeptLiveData.observe(viewLifecycleOwner,{
            when(it){

                is Resource.Success -> { requireView().snackBar(it.data.message) }

                is Resource.Error -> {  handleApiError(it){} }

            }
        })
    }



    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddDepartmentBinding {
        return FragmentAddDepartmentBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        Log.d(TAG, "initViews: ")
    }

    override fun initActionView() {
        btnAddDept.setOnClickListener {

            if(validate()) {

                val dept = Department(
                    edTxtEmpDeptDesc.text.toString(),
                    1,
                    edTxtEmpDept.text.toString(),
                    0
                )
                Log.d(TAG, "initActionView: $dept ")
                vModel.addDepartment(dept)
            }

        }
    }

    override fun getViewModel(): Class<DepartmentViewModel> {
        return DepartmentViewModel::class.java
    }

    private fun validate(): Boolean{
        val list = arrayListOf<EditText>()
        list.addAll(listOf(edTxtEmpDept, edTxtEmpDeptDesc))

        return checkEdTextValidation(list)

    }

}