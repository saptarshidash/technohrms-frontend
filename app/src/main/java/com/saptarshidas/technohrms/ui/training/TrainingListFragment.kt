package com.saptarshidas.technohrms.ui.training

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.training.TrainingDto
import com.saptarshidas.technohrms.data.model.Training
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentTrainingListBinding
import com.saptarshidas.technohrms.utils.afterTextChanged
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_training_list.*

class TrainingListFragment : BaseFragment<FragmentTrainingListBinding,TrainingViewModel>(),
BaseAdapter.OnRecyclerClickListener{


    private val TAG = "TrainingListFragment"

    private lateinit var adapter : TrainingAdapter<TrainingDto>
    private var dataList: List<TrainingDto> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerTraining.showShimmer()
        vModel.getAllTraining()
        
        vModel.allTrainingLiveData.observe(viewLifecycleOwner, {
            recyclerTraining.hideShimmer()
            when(it){
                is Resource.Success -> {
                    dataList = it.data.trainingDtoList
                    adapter.updateList(ArrayList(it.data.trainingDtoList))
                }
                
                is Resource.Error -> { requireView().snackBar("Unable to load , try again"){
                    vModel.getAllTraining()
                } }
            }
        })
        
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrainingListBinding {
        return FragmentTrainingListBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        recyclerTraining.layoutManager = LinearLayoutManager(requireContext())
        adapter = TrainingAdapter(this, ArrayList())
        recyclerTraining.adapter = adapter
    }

    override fun initActionView() {
        btnAddTraining.setOnClickListener {
            val action = TrainingListFragmentDirections.actionTrainingListFragmentToAddTrainingFragment()
            navController.navigate(action)
        }
        
        edTxtSearchTraining.afterTextChanged {
            filter(it)
        }
    }

    override fun getViewModel(): Class<TrainingViewModel> {
        return TrainingViewModel::class.java
    }

    override fun onClick(data: Any?) {
       val trainingDto = data as TrainingDto
        val action = TrainingListFragmentDirections.actionTrainingListFragmentToDetailedTrainingFragment(trainingDto)
        navController.navigate(action)
    }

    private fun filter(s: String){
        val list = ArrayList<TrainingDto>()

        for(data in dataList){
            if (data.name.toLowerCase().contains(s)){
                list.add(data)
            }
        }

        adapter.updateList(list)
    }


}