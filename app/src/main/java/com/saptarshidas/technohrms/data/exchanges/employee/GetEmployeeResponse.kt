package com.saptarshidas.technohrms.data.exchanges.employee

import com.saptarshidas.technohrms.data.model.Employee

data class GetEmployeeResponse(
    val employeeList: List<Employee>
    )