package com.saptarshidas.technohrms.ui.training

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.training.TrainingDto
import com.saptarshidas.technohrms.data.exchanges.training.TrainingEmployeeDto
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.model.Designation
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.model.Training
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentAssignedTrainingListBinding
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_assigned_training_list.*
import kotlinx.android.synthetic.main.fragment_leave_request_list.*
import kotlinx.android.synthetic.main.fragment_training_list.*


class AssignedTrainingListFragment : BaseFragment<FragmentAssignedTrainingListBinding, TrainingViewModel>(),
BaseAdapter.OnRecyclerClickListener{


    private val TAG = "AssignedTrainingListFra"
    private lateinit var adapter: AssignedTrainingAdapter<TrainingEmployeeDto>

    private var employeeList: MutableList<Employee>? = null
    private var trainingList: MutableList<TrainingDto>? = null

    private val blankSpinnerOpt = Employee(
        0,
        "All",
        Department("",0,"",0),
        Designation("", 0, ""),
        "", "","", "", ""
    )

    private val blankTrainingSpinner = TrainingDto(
        0,
        "",
        0,
        "All",
        0,
        0
    )

    private var empId = 0
    private var trainingId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAssignedTraining.showShimmer()
        vModel.getAllAssignedTraining()
        vModel.getAllEmployees()
        vModel.getAllTraining()
        
        vModel.assignedTrainingLiveData.observe(viewLifecycleOwner, {
            recyclerAssignedTraining.hideShimmer()
            when(it){
                is Resource.Success -> { adapter.updateList(ArrayList(it.data.trainingEmployeeDtoList)) }
                
                is Resource.Error ->{ requireView().snackBar("Unable to load"){
                    vModel.getAllAssignedTraining()
                } }
            }
        })

        vModel.employeesLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {

                    initSpinner(ArrayList(it.data.employeeList))
                }
                is Resource.Error -> {
                    handleApiError(it){}
                }
            }
        })

        vModel.allTrainingLiveData.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success -> {

                    initTrainingSpinner(ArrayList(it.data.trainingDtoList))
                }
                is Resource.Error -> {
                    handleApiError(it){}
                }
            }
        })

    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssignedTrainingListBinding {
        return FragmentAssignedTrainingListBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        recyclerAssignedTraining.layoutManager = LinearLayoutManager(requireContext())
        adapter = AssignedTrainingAdapter(this, ArrayList())
        recyclerAssignedTraining.adapter = adapter
    }

    override fun initActionView() {
        btnAssignTraining.setOnClickListener {
            val action = AssignedTrainingListFragmentDirections.actionAssignedTrainingListFragmentToAssignTrainingFragment()
            navController.navigate(action)
        }
    }

    override fun getViewModel(): Class<TrainingViewModel> {
        return TrainingViewModel::class.java
    }

    override fun onClick(data: Any?) {
        val action = AssignedTrainingListFragmentDirections.
        actionAssignedTrainingListFragmentToDetailedAssignedTrainingFragment(data as TrainingEmployeeDto)

        navController.navigate(action)
    }

    private fun initSpinner(list: ArrayList<Employee>) {

        list.add(blankSpinnerOpt)
        employeeList = list
        employeeSpinner.item = employeeList

        employeeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                empId = (employeeList as ArrayList<Employee>).get(position).id

                if(empId == 0){
                    vModel.getAllAssignedTraining()
                }else{
                    vModel.getAllAssignedTrainingByEmployee(empId)
                }


            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
    }
    private fun initTrainingSpinner(list: ArrayList<TrainingDto>) {

        list.add(blankTrainingSpinner)
        trainingList = list
        trainingSpinner.item = trainingList

        trainingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                trainingId = (trainingList as ArrayList<TrainingDto>).get(position).id

                Log.d(TAG, "onItemSelected: $trainingId")
                if(trainingId == 0){
                    vModel.getAllAssignedTraining()
                }else{
                    vModel.getAllAssignedTrainingByTraining(trainingId)
                }


            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
    }

}