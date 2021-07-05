package com.saptarshidas.technohrms.ui.training

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.databinding.FragmentDetailedTrainingBinding
import com.saptarshidas.technohrms.ui.employee.DetailedEmployeeFragmentArgs
import kotlinx.android.synthetic.main.fragment_detailed_training.*

class DetailedTrainingFragment : BaseFragment<FragmentDetailedTrainingBinding, TrainingViewModel>() {

    private val TAG = "DetailedTrainingFragmen"
    private val args: DetailedTrainingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailedTrainingBinding {
        return FragmentDetailedTrainingBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        edTxtTrainingId.setText(args.training.id.toString())
        edTxtTrainingName.setText(args.training.name.toString())
        edTxtTrainingDesc.setText(args.training.description)
        txtViewTotalParticipant.setText(args.training.totalParticipation.toString())
        txtViewOngoingParticipant.setText(args.training.onGoingParticipant.toString())
        txtViewCompleteParticipant.setText(args.training.completed.toString())
    }

    override fun initActionView() {
        Log.d(TAG, "initActionView: ")
    }

    override fun getViewModel(): Class<TrainingViewModel> {
        return TrainingViewModel::class.java
    }

}