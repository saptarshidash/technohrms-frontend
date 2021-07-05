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
import androidx.navigation.fragment.navArgs
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.employee.EmployeeIdDto
import com.saptarshidas.technohrms.data.exchanges.training.TrainingIdDto
import com.saptarshidas.technohrms.data.exchanges.training.UpdateAssignedTrainingRequest
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentDetailedAssignedTrainingBinding
import com.saptarshidas.technohrms.ui.employee.DetailedEmployeeFragmentArgs
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_add_employee.*
import kotlinx.android.synthetic.main.fragment_detailed_assigned_training.*
import kotlinx.android.synthetic.main.fragment_detailed_assigned_training.edTxtEmpName
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList


class DetailedAssignedTrainingFragment : BaseFragment<FragmentDetailedAssignedTrainingBinding, TrainingViewModel>(),
DatePickerDialog.OnDateSetListener{

    private  val TAG = "DetailedAssignedTrainin"
    private val args: DetailedAssignedTrainingFragmentArgs by navArgs()

    private lateinit var eval:String

    private lateinit var ratingAdapter : ArrayAdapter<Int>
    private lateinit var statusAdapter : ArrayAdapter<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.updateAssignedTrainingLiveData.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success -> { requireView().snackBar(it.data.message) }

                is Resource.Error -> { handleApiError(it){} }
            }
        })
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailedAssignedTrainingBinding {
        return FragmentDetailedAssignedTrainingBinding.inflate(inflater, container, false)
    }

    override fun initViews() {

        edTxtAssignedId.setText(args.trainingEmployee.id.toString())
        edTxtEmpName.setText(args.trainingEmployee.employee.name)
        edTxtEmpTraining.setText(args.trainingEmployee.training.name)
        edTxtFromDate.setText(args.trainingEmployee.startDate)
        edTxtToDate.setText(args.trainingEmployee.endDate)

        if(args.trainingEmployee.completionStatus){
            txtViewCompletion.isEnabled = false
            txtViewCompletion.setAdapter(ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, ArrayList<String>() ))
            txtViewRating.isEnabled = false
            txtViewCompletion.setAdapter(ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, ArrayList<Int>() ))
            txtViewRating.setText(args.trainingEmployee.rating.toString())
            txtViewCompletion.setText("Completed")
            edTxtFromDate.isEnabled = false
            edTxtToDate.isEnabled = false
        }else{
            txtViewCompletion.setText("Ongoing")
            txtViewRating.setText(args.trainingEmployee.rating.toString())
            ratingAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                getRatingList()
            )

            statusAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                getCompletionStatusList()
            )

            txtViewRating.setAdapter(ratingAdapter)
            txtViewCompletion.setAdapter(statusAdapter)
        }




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

        btnUpdateAssignTraining.setOnClickListener {
            val completion = txtViewCompletion.text.toString()
            var temp = false
            if(completion.equals("Completed")){
                temp = true
            }
            val request = UpdateAssignedTrainingRequest(
                EmployeeIdDto(args.trainingEmployee.employee.id),
                args.trainingEmployee.training,
                temp,
                edTxtToDate.text.toString(),
                txtViewRating.text.toString().toInt(),
                edTxtFromDate.text.toString()
            )

            vModel.updateAssignedTraining(args.trainingEmployee.id, request)
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
            edTxtToDate.setText((date.toString()))
        }
    }

    private fun bringDatePickerDialog(){
        val now = Calendar.getInstance(TimeZone.getDefault())
        val dpd = DatePickerDialog(requireContext(), this, now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DATE])
        dpd.show()
    }

    private fun getRatingList(): ArrayList<Int>{
        val list = ArrayList<Int>()
        list.addAll(listOf(1,2,3,4,5,6,7,8,9,10))
        return list
    }

    private fun getCompletionStatusList(): ArrayList<String>{
        val list = ArrayList<String>()
        list.addAll(listOf("Ongoing", "Completed"))
        return list
    }

}