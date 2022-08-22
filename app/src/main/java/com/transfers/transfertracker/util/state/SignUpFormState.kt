package com.transfers.transfertracker.util.state

/**
 * Data validation state of the login form.
 */
data class SignUpFormState(
    val firstnameError: Int? = null,
    val lastnameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)