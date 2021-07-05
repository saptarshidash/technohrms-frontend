package com.saptarshidas.technohrms.ui.department

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.department.DepartmentDto
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentDepartmentOverviewBinding
import com.saptarshidas.technohrms.utils.afterTextChanged
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_department_overview.*

class DepartmentOverviewFragment : BaseFragment<FragmentDepartmentOverviewBinding, DepartmentViewModel>(),
BaseAdapter.OnRecyclerClickListener{

    private val TAG = "DepartmentOverviewFragm"
    private lateinit var adapter: DepartmentAdapter<Department>

    private var dataList: List<Department> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        recyclerDepartment.showShimmer()
        vModel.getAllDepartments()
        vModel.getDeptLiveData.observe(viewLifecycleOwner,{
            recyclerDepartment.hideShimmer()
            when(it){
                is Resource.Success ->{
                    dataList = it.data.departmentDtoList
                    adapter.updateList(ArrayList(it.data.departmentDtoList))
                }

                is Resource.Error ->{
                    requireView().snackBar("Unable to load departments"){ vModel.getAllDepartments() }
                }
            }
        })
    }


    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDepartmentOverviewBinding {
        return FragmentDepartmentOverviewBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        recyclerDepartment.layoutManager = LinearLayoutManager(requireContext())
        adapter = DepartmentAdapter(this, ArrayList())
        recyclerDepartment.adapter = adapter
    }

    override fun initActionView() {
        edTxtSearchDept.afterTextChanged {
            filter(it)
        }
    }

    override fun getViewModel(): Class<DepartmentViewModel> {
        return DepartmentViewModel::class.java
    }

    override fun onClick(data: Any?) {
        val dept = data as Department
        val deptDto = DepartmentDto(dept.description, dept.id,dept.name, dept.totalEmployee)
        val action = DepartmentOverviewFragmentDirections.actionDepartmentOverviewFragmentToDetailedDepartmentFragment(deptDto)
        navController.navigate(action)
    }

    private fun filter(s: String){
        val list = ArrayList<Department>()

        for(data in dataList){
            if(data.name.toLowerCase().contains(s)){
                list.add(data)
            }
        }

        adapter.updateList(list)
    }


}