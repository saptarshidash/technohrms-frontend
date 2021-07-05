package com.saptarshidas.technohrms.ui.attendance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.saptarshidas.technohrms.base.BaseAdapter
import com.saptarshidas.technohrms.base.BaseFragment
import com.saptarshidas.technohrms.data.exchanges.attendance.GetAttendanceResponseItem
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.model.Designation
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.network.Resource
import com.saptarshidas.technohrms.databinding.FragmentAttendanceListBinding
import com.saptarshidas.technohrms.utils.handleApiError
import kotlinx.android.synthetic.main.fragment_attendance_list.*
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList


class AttendanceListFragment : BaseFragment<FragmentAttendanceListBinding,AttendanceViewModel>(),
CalendarListener, BaseAdapter.OnRecyclerClickListener{


    private  val TAG = "AttendanceListFragment"
    private lateinit var adapter: AttendanceAdapter<GetAttendanceResponseItem>

    private var employeeList: MutableList<Employee>? = null

    private val blankSpinnerOpt = Employee(
        0,
        "All",
        Department("",0,"",0),
        Designation("", 0, ""),
        "", "","", "", ""
    )

    private var totalEmp = 0
    private var presentEmp = 0
    private var empId = 0


    private lateinit var st: Date
    private lateinit var end: Date

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar.setCalendarListener(this)

        recyclerAttendance.showShimmer()

        st = Date(System.currentTimeMillis())
        end = st
        btnCalendar.setText("\uD83D\uDCC5 "+st.toString())

        vModel.getAllAttendanceByDate(st.toString(), end.toString())
        vModel.getAllEmployees()

        vModel.attendanceResponseLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    presentEmp = it.data.size
                    adapter.updateList(it.data)
                    val t = "ATTENDANCE  "+presentEmp.toString()
                    txtViewDailyAttendance.setText(t)
                    recyclerAttendance.hideShimmer()
                }
                is Resource.Error -> {
                    handleApiError(it){}
                }
            }
        })

        vModel.employeesLiveData.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success -> {
                    totalEmp = it.data.employeeList.size
                    txtViewTotalEmp.setText(" | "+totalEmp.toString())
                    initSpinner(ArrayList(it.data.employeeList))
                }
                is Resource.Error -> {
                    handleApiError(it){}
                }
            }
        })





    }

    private fun initSpinner(list: ArrayList<Employee>) {

        list.add(blankSpinnerOpt)
        employeeList = list




        attendanceSpinner.item = employeeList

        attendanceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                cardDailyAttendance.visibility = View.GONE
                empId = (employeeList as ArrayList<Employee>).get(position).id
                if(empId != 0) {
                    vModel.getAllAttendanceByEmpAndDate(empId, st.toString(), end.toString())
                }else{
                    vModel.getAllAttendanceByDate(st.toString(), end.toString())
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAttendanceListBinding {
        return FragmentAttendanceListBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        recyclerAttendance.layoutManager = LinearLayoutManager(requireContext())
        adapter = AttendanceAdapter(this, ArrayList())
        recyclerAttendance.adapter = adapter

    }

    override fun initActionView() {
        btnCalendar.setOnClickListener {
            calendarView.visibility = View.VISIBLE
        }
    }

    override fun getViewModel(): Class<AttendanceViewModel> {
        return AttendanceViewModel::class.java
    }

    override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {

        st = Date(startDate.time.time)
        end = Date(endDate.time.time)

        if(st.toString().equals(end.toString()) && empId == 0){
            cardDailyAttendance.visibility = View.VISIBLE
        }else{
            cardDailyAttendance.visibility = View.GONE
        }
        Log.d(TAG, "onDateRangeSelected: $startDate $endDate")
        calendarView.visibility = View.GONE

        if(empId == 0) {
            vModel.getAllAttendanceByDate(st.toString(), end.toString())
        }else{
            vModel.getAllAttendanceByEmpAndDate(empId,st.toString(), end.toString())
        }
        btnCalendar.setText(st.toString()+" to "+end.toString())
    }

    override fun onFirstDateSelected(startDate: Calendar) {
        Log.d(TAG, "onFirstDateSelected: ")
    }

    override fun onClick(data: Any?) {
        TODO("Not yet implemented")
    }

}