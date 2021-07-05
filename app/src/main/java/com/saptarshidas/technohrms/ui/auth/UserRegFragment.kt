package com.saptarshidas.technohrms.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.auth.UserRegistrationRequest
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentUserRegBinding
import com.saptarshidas.technohrms.utils.checkEdTextValidation
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_leave_request_list.*
import kotlinx.android.synthetic.main.fragment_user_reg.*


class UserRegFragment : BaseFragment<FragmentUserRegBinding, AuthViewModel>() {

    private  val TAG = "UserRegFragment"
    private var employeeList: MutableList<Employee>? = null

    private lateinit var selectedEmp: Employee

    private lateinit var selectedRole: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.getAllEmployees()

        vModel.regLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success ->{
                    requireView().snackBar(it.data.message)
                }

                is Resource.Error -> {
                    handleApiError(it){}
                }
            }
        })

        vModel.employeesLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    initEmpSpinner(ArrayList(it.data.employeeList))
                    initRoleSpinner()
                }

                is Resource.Error->{
                    requireView().snackBar("Unable to load employees, try again")
                }
            }
        })

    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserRegBinding {
        return FragmentUserRegBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        Log.d(TAG, "initViews: ")
    }

    override fun initActionView() {
        btnGenerateCred.setOnClickListener {
            val request = UserRegistrationRequest(
                edTxtUsername.text.toString(),
                edTxtPassword.text.toString(),
                true,
                selectedRole,
                selectedEmp
            )

            if(validate()){
                vModel.register(request)
            }

        }
    }

    override fun getViewModel(): Class<AuthViewModel> {
        return AuthViewModel::class.java
    }

    private fun initEmpSpinner(list: ArrayList<Employee>) {

        employeeList = list
        employeeSpinner.item = employeeList

        employeeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {

                selectedEmp = (employeeList as ArrayList<Employee>).get(position)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun initRoleSpinner(){
        val list = ArrayList<String>()
        list.add("ROLE_ADMIN")
        list.add("ROLE_USER")

        roleSpinner.item = list
        roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedRole = list.get(position)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

    }

    private fun validate(): Boolean{
        val list = ArrayList<EditText>()
        list.addAll(listOf(edTxtUsername, edTxtPassword))
        return checkEdTextValidation(list) && selectedEmp!=null && selectedRole!=null
    }

}