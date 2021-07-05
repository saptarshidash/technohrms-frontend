package com.saptarshidas.technohrms.data.exchanges.employee

import com.saptarshidas.technohrms.data.model.Designation

data class GetDesignationsResponse(
    val designationDtoList: List<Designation>
)