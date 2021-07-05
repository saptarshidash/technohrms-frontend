package com.saptarshidas.technohrms.ui.designation

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
import com.saptarshidas.technohrms.data.model.Designation
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentDesignationListBinding
import com.saptarshidas.technohrms.utils.afterTextChanged
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_designation_list.*

class DesignationListFragment : BaseFragment<FragmentDesignationListBinding, DesignationViewModel>(),
BaseAdapter.OnRecyclerClickListener{

    private val TAG = "DesignationListFragment"
    private lateinit var adapter: DesignationAdapter<Designation>
    private var dataList: List<Designation> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerDesignation.showShimmer()
        vModel.getAllDesignations()
        vModel.designationLiveData.observe(viewLifecycleOwner, {
            recyclerDesignation.hideShimmer()
            when(it){
                is Resource.Success -> {
                    dataList = it.data.designationDtoList
                    adapter.updateList(ArrayList(it.data.designationDtoList))}

                is Resource.Error -> { requireView().snackBar("Unable to load, try again"){
                    vModel.getAllDesignations()
                }}
            }
        })

    }


    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDesignationListBinding {
        return FragmentDesignationListBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        recyclerDesignation.layoutManager = LinearLayoutManager(requireContext())
        adapter = DesignationAdapter(this, ArrayList())
        recyclerDesignation.adapter = adapter
    }

    override fun initActionView() {
        edTxtSearchDesignation.afterTextChanged {
            filter(it.toLowerCase())
        }
    }

    override fun getViewModel(): Class<DesignationViewModel> {
        return DesignationViewModel::class.java
    }

    override fun onClick(data: Any?) {
        Log.d(TAG, "onClick: ")
    }

    private fun filter(s: String){
        val list = ArrayList<Designation>()

        for(data in dataList){
            if(data.name.toLowerCase().contains(s)){
                list.add((data))
            }
        }

        adapter.updateList(list)
    }

}