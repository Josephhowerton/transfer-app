package com.transfers.transfertracker.util.state

import java.util.regex.Matcher
import java.util.regex.Pattern

object AuthFormat {
    private val VALID_EMAIL_ADDRESS_REGEX: Pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    fun validate(emailStr: String): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }

    fun removeWhitespace(str: String): String = str.replace("\\s".toRegex(), "")
}