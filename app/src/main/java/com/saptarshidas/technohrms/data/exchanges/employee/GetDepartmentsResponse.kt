package com.saptarshidas.technohrms.data.exchanges.employee

import com.saptarshidas.technohrms.data.model.Department

data class GetDepartmentsResponse(
    val departmentDtoList: List<Department>
)