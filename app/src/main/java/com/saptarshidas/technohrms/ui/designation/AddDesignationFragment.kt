package com.saptarshidas.technohrms.ui.designation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.designation.AddDesignationRequest
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.data.repository.BaseRepository
import com.saptarshidas.technohrms.databinding.FragmentAddDesignationBinding
import com.saptarshidas.technohrms.utils.checkEdTextValidation
import com.saptarshidas.technohrms.utils.handleApiError
import com.saptarshidas.technohrms.utils.snackBar
import kotlinx.android.synthetic.main.fragment_add_designation.*

class AddDesignationFragment : BaseFragment<FragmentAddDesignationBinding, DesignationViewModel>() {

    private  val TAG = "AddDesignationFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vModel.addDesignationLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    requireView().snackBar(it.data.message)
                }

                is Resource.Error ->{ handleApiError(it){  } }
            }
        })

    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddDesignationBinding {
        return FragmentAddDesignationBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        Log.d(TAG, "initViews: ")
    }

    override fun initActionView() {

        btnAddDesignation.setOnClickListener {

            if(validate()){
                val request = AddDesignationRequest(
                    edTxtEmpDesignationDesc.text.toString(),
                    edTxtEmpDesignation.text.toString(),

                )

                vModel.addDesignation(request)
            }



        }

    }

    override fun getViewModel(): Class<DesignationViewModel> {
        return DesignationViewModel::class.java
    }

    private fun validate(): Boolean{
        val list = arrayListOf<EditText>()
        list.addAll(listOf(edTxtEmpDesignationDesc, edTxtEmpDesignation))
        return checkEdTextValidation(list)
    }


}