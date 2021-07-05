package com.saptarshidas.technohrms.ui.training

import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.data.exchanges.training.TrainingEmployeeDto
import kotlinx.android.synthetic.main.list_assigned_training.view.*

class AssignedTrainingAdapter<T>(l: OnRecyclerClickListener, list: ArrayList<T>?) : BaseAdapter<T>() {

    init {
        dataList = list!!
        listener = l
        layoutId = R.layout.list_assigned_training
    }

    override fun onViewHolderBind(holder: ViewHolder, position: Int, data: Any?) {

        val trainingEmployee = data as TrainingEmployeeDto

        holder.view.txtViewEmpName.text = trainingEmployee.employee.name
        holder.view.txtViewTrainingName.text = "Assigned " + trainingEmployee.training.name

        if(trainingEmployee.completionStatus){
            holder.view.btnCompletion.text = "Completed"
        }else{
            holder.view.btnCompletion.text = "Ongoing"
        }



    }
}