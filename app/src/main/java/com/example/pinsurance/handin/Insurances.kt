package com.example.pinsurance.handin

data class Insurances (
    val name: String?,
    val maturity: String?,
    val start: String?,
    val paymentYear: String?,
    val guaranteeYear: String?,
    val category: String?,
    val cost: String?,
    var expandable: Boolean=false) {

}