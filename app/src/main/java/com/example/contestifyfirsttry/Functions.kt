package com.example.contestifyfirsttry

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class Functions {
    fun getTime(timestamp:String): String? {
        var parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(timestamp)
        var diff = Date().time - parsedDate.time
        var displayTimeDay = SimpleDateFormat("dd",Locale.ENGLISH).format(diff)
        return if (displayTimeDay > "00"){
            displayTimeDay+"d"
        }else{
            displayTimeDay = SimpleDateFormat("HH:mm",Locale.ENGLISH).format(parsedDate)
            displayTimeDay
        }
        //var displayTime = SimpleDateFormat("dd'T'HH",Locale.ENGLISH).format(diff)

    }
}