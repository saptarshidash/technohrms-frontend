package com.saptarshidas.technohrms.ui.employee

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.model.Designation
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentAddEmployeeBinding
import com.saptarshidas.technohrms.utils.checkAutoTxtViewValidation
import com.saptarshidas.technohrms.utils.checkEdTextValidation
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_add_employee.*
import kotlinx.android.synthetic.main.fragment_add_employee.departmentTextView
import kotlinx.android.synthetic.main.fragment_add_employee.designationTextView
import kotlinx.android.synthetic.main.fragment_add_employee.edTxtEmpDob
import kotlinx.android.synthetic.main.fragment_add_employee.edTxtEmpEmail
import kotlinx.android.synthetic.main.fragment_add_employee.edTxtEmpJoining
import kotlinx.android.synthetic.main.fragment_add_employee.edTxtEmpName
import kotlinx.android.synthetic.main.fragment_add_employee.edTxtEmpPhone
import kotlinx.android.synthetic.main.fragment_add_employee.workTypeTextView
import kotlinx.android.synthetic.main.fragment_detailed_employee.*
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList

class AddEmployeeFragment : BaseFragment<FragmentAddEmployeeBinding, EmployeeViewModel>(),
android.app.DatePickerDialog.OnDateSetListener{

    private  val TAG = "AddEmployeeFragment"

    private lateinit var eval: String
    private lateinit var deptAdapter: ArrayAdapter<Department>
    private lateinit var designationAdapter: ArrayAdapter<Designation>
    private lateinit var workTypeAdapter: ArrayAdapter<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.AddEmployeeLiveData.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success ->{
                    requireView().snackBar(it.data.message)
                }
            }
        })

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


    }


    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddEmployeeBinding {
        return FragmentAddEmployeeBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        workTypeAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, getWorkTypeList())
        workTypeTextView.setAdapter(workTypeAdapter)
    }

    override fun initActionView() {
       btnAddEmp.setOnClickListener {

           if(validate()){
               val employee = Employee(
                   1,
                   edTxtEmpName.text.toString(),
                   Department("",departmentTextView.text.toString().split(" ").toTypedArray()[0].toInt(),"",0),
                   Designation("",designationTextView.text.toString().split(" ").toTypedArray()[0].toInt(),""),
                   edTxtEmpDob.text.toString(),
                   edTxtEmpEmail.text.toString(),
                   edTxtEmpJoining.text.toString(),
                   edTxtEmpPhone.text.toString(),
                   workTypeTextView.text.toString()
               )
               Log.d(TAG, "initActionView: $employee ")
               vModel.addEmployee(employee)
           }




       }

        edTxtEmpJoining.setOnClickListener {
            eval = "1"
            bringDatePickerDialog()
        }

        edTxtEmpDob.setOnClickListener {
            eval = "2"
            bringDatePickerDialog()
        }
    }

    override fun getViewModel(): Class<EmployeeViewModel> {
        return EmployeeViewModel::class.java
    }

    private fun bringDatePickerDialog(){
        val now = Calendar.getInstance(TimeZone.getDefault())
        val dpd = DatePickerDialog(requireContext(), this, now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DATE])
        dpd.show()
    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date: Date = Date(year - 1900, month, dayOfMonth)

        if(eval.equals("1")){
            edTxtEmpJoining.setText(date.toString())
        }else if(eval.equals("2")){
            edTxtEmpDob.setText((date.toString()))
        }

    }

    private fun validate(): Boolean{
        val list = arrayListOf<EditText>()
        list.addAll(listOf(edTxtEmpName, edTxtEmpEmail, edTxtEmpPhone, edTxtEmpJoining,
        edTxtEmpDob))

        val listTview = arrayListOf<AutoCompleteTextView>()
        listTview.addAll(listOf(departmentTextView, designationTextView, workTypeTextView))

        return checkEdTextValidation(list) && checkAutoTxtViewValidation(listTview)

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

