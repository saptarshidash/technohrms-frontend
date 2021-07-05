package com.saptarshidas.technohrms.ui.training

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.employee.EmployeeIdDto
import com.saptarshidas.technohrms.data.exchanges.training.AssignTrainingRequest
import com.saptarshidas.technohrms.data.exchanges.training.TrainingDto
import com.saptarshidas.technohrms.data.exchanges.training.TrainingIdDto
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.model.Training
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentAssignTrainingBinding
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_assign_training.*
import kotlinx.android.synthetic.main.fragment_assign_training.edTxtFromDate
import kotlinx.android.synthetic.main.fragment_assign_training.edTxtToDate
import kotlinx.android.synthetic.main.fragment_detailed_assigned_training.*
import java.sql.Date
import java.util.*

class AssignTrainingFragment : BaseFragment<FragmentAssignTrainingBinding, TrainingViewModel>(),
DatePickerDialog.OnDateSetListener{
    private  val TAG = "AssignTrainingFragment"
    private lateinit var eval: String

    private lateinit var empAdapter: ArrayAdapter<Employee>
    private lateinit var trainingAdapter: ArrayAdapter<TrainingDto>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.getAllTraining()
        vModel.getAllEmployees()

        vModel.allTrainingLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    trainingAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, it.data.trainingDtoList)
                    trainingTextView.setAdapter(trainingAdapter)

                }
            }
        })

        vModel.employeesLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    empAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, it.data.employeeList)
                    employeeTextView.setAdapter(empAdapter)
                }
            }
        })

        vModel.assignTrainingLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> { requireView().snackBar(it.data.message) }

                is Resource.Error -> { requireView().snackBar("Unable to assign, try again") }
            }
        })

    }
    
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssignTrainingBinding {
        return FragmentAssignTrainingBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        Log.d(TAG, "initViews: ")
    }

    override fun initActionView() {
        edTxtFromDate.setOnClickListener {
            eval = "1"
            bringDatePickerDialog()
        }
        
        edTxtToDate.setOnClickListener {
            eval = "2"
            bringDatePickerDialog()
        }

        btnAssignTraining.setOnClickListener {
            val empIdDto = EmployeeIdDto(employeeTextView.text.toString().split(" ").toTypedArray()[0].toInt())
            val tIdDto = TrainingIdDto(trainingTextView.text.toString().split(" ").toTypedArray()[0].toInt())

            val request = AssignTrainingRequest(
                empIdDto,
                tIdDto,
                edTxtFromDate.text.toString(),
                edTxtToDate.text.toString()
            )

            vModel.assignTraining(request)
        }

    }

    override fun getViewModel(): Class<TrainingViewModel> {
        return TrainingViewModel::class.java
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date: Date = Date(year - 1900, month, dayOfMonth)

        if(eval.equals("1")){
            edTxtFromDate.setText(date.toString())
        }else if(eval.equals("2")){
            edTxtToDate.setText(date.toString())
        }
    }

    private fun bringDatePickerDialog(){
        val now = Calendar.getInstance(TimeZone.getDefault())
        val dpd = DatePickerDialog(requireContext(), this, now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DATE])
        dpd.show()
    }

}