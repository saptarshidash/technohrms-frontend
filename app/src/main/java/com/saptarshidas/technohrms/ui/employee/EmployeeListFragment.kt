package com.saptarshidas.technohrms.ui.employee

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.training.TrainingDto
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.model.Designation
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentEmployeeListBinding
import com.saptarshidas.technohrms.utils.afterTextChanged
import com.saptarshidas.technohrms.utils.handleApiError
import kotlinx.android.synthetic.main.fragment_assigned_training_list.*
import kotlinx.android.synthetic.main.fragment_employee_list.*
import kotlinx.coroutines.launch

class EmployeeListFragment : BaseFragment<FragmentEmployeeListBinding, EmployeeViewModel>(),
BaseAdapter.OnRecyclerClickListener{

    private  val TAG = "EmployeeOverviewFragment"

    private lateinit var adapter: EmployeeAdapter<Employee>
    private var dataList: List<Employee> = ArrayList()

    private var deptList: MutableList<Department>? = null

    private val blankSpinnerOpt = Department(
        "",
        0,
        "All",
        0
    )

    private var deptId = 0


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vModel.getAllEmployees()
        vModel.getAllDepartments()

        vModel.employeesLiveData.observe(viewLifecycleOwner, {
            when(it){

                is Resource.Success-> {
                    dataList = it.data.employeeList
                    adapter.updateList(ArrayList(it.data.employeeList))
                }

                is Resource.Error-> {
                    handleApiError(it) {  }
                }
            }
        })

        vModel.departmentsLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    initDeptSpinner(ArrayList(it.data.departmentDtoList))
                }
            }
        })

    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEmployeeListBinding {
        return FragmentEmployeeListBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        recyclerEmployee.layoutManager = LinearLayoutManager(requireContext())
        adapter = EmployeeAdapter(this, ArrayList())
        recyclerEmployee.adapter = adapter
    }

    override fun initActionView() {
        edTxtSearchEmployee.afterTextChanged {
            filter(it)
        }

    }

    override fun getViewModel(): Class<EmployeeViewModel> {
        return EmployeeViewModel::class.java
    }

    override fun onClick(data: Any?) {
        val employee = data as Employee
        var action = EmployeeListFragmentDirections.actionEmployeeOverviewFragmentToDetailedEmployeeFragment(employee)
        navController.navigate(action)
    }

    private fun filter(s: String){
        val list = ArrayList<Employee>()

        if(s.equals("All")){
            adapter.updateList(ArrayList(dataList))
            return
        }

        for(data in dataList){
            if(data.department.name.contains(s) || data.name.toLowerCase().contains(data.name) || data.mobile.contains(s) || data.email.contains(s)){
                list.add(data)

            }

            adapter.updateList(list)

        }
    }

    private fun initDeptSpinner(list: ArrayList<Department>) {

        list.add(blankSpinnerOpt)
        deptList = list
        departmentSpinner.item = deptList

        departmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {

                val dept = (deptList as ArrayList<Department>).get(position).name
                filter(dept)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
    }

}