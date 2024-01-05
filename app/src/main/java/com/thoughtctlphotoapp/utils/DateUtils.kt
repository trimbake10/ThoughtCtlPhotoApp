package com.thoughtctlphotoapp.utils

import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {

    fun getPostDate(timeMillis: Long):String{
        val simpleDateFormat=SimpleDateFormat("DD/MM/YYYY hh:mm aa")
        val date=Date()
        date.time= timeMillis.toLong()
        return simpleDateFormat.format(date)
    }
}