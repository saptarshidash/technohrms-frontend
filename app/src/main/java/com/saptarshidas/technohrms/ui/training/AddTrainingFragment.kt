package com.saptarshidas.technohrms.ui.training

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.model.Training
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentAddTrainingBinding
import com.saptarshidas.technohrms.utils.checkEdTextValidation
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_add_training.*

class AddTrainingFragment : BaseFragment<FragmentAddTrainingBinding, TrainingViewModel>() {

    private val TAG = "AddTrainingFragment"
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        vModel.addTrainingLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> { requireView().snackBar(it.data.message) }
                
                is Resource.Error -> {
                    handleApiError(it){}
                }
            }
        })
        
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddTrainingBinding {
        return FragmentAddTrainingBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        Log.d(TAG, "initViews: ")
    }

    override fun initActionView() {
        btnSaveTraining.setOnClickListener {

            if(validate()){

                val training = Training(
                    0,
                    edTxtTrainingDesc.text.toString(),
                    1,
                    edTxtTrainingName.text.toString(),
                    0,
                    0
                )
                
                vModel.addTraining(training)

            }

        }
    }

    override fun getViewModel(): Class<TrainingViewModel> {
        return TrainingViewModel::class.java
    }

    private fun validate(): Boolean{

        val list = ArrayList<EditText>()
        list.addAll(listOf(edTxtTrainingName, edTxtTrainingDesc))
        return checkEdTextValidation(list)
    }

}