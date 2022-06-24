package com.transfers.transfertracker.util

import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

object NetworkUtil {

    fun getStatusCode(exception: Throwable): Int =
        if(exception is HttpException){
            exception.code()
        }
        else if(exception is ConnectException || exception is UnknownHostException){
            100
        }
        else{
            -1
        }
}