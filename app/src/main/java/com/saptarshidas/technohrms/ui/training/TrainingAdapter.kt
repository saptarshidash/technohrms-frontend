package com.saptarshidas.technohrms.ui.training

import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.data.exchanges.training.TrainingDto
import com.saptarshidas.technohrms.data.model.Training
import kotlinx.android.synthetic.main.list_trainings.view.*

class TrainingAdapter<T>(l: OnRecyclerClickListener, list: ArrayList<T>?) : BaseAdapter<T>() {

    init {
        dataList = list!!
        listener = l
        layoutId = R.layout.list_trainings
    }

    override fun onViewHolderBind(holder: ViewHolder, position: Int, data: Any?) {

        val training = data as TrainingDto

        holder.view.txtViewTrainingName.text = training.name
        holder.view.txtViewTrainingDesc.text = training.description
    }
}