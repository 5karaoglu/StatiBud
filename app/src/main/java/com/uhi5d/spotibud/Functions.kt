package com.uhi5d.spotibud

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Functions {
    companion object {
        private fun hideKeyboard(activity: Activity) {
            if (activity.currentFocus != null) {
                val inputManager: InputMethodManager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            }
        }
    }

    private val TAG = "Functions"
    private val SECOND = 1000
    private val MINUTE = 60 * SECOND
    private val HOUR = 60 * MINUTE
    private val DAY = 24 * HOUR
    fun getTime(timestamp: String): String? {
        var time: String? = null
        var parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        parsedDate.timeZone = TimeZone.getTimeZone("GMT")
        var date = parsedDate.parse(timestamp)

        var calendar = Calendar.getInstance()
        calendar.timeInMillis = date.time

        val currentDate = System.currentTimeMillis()
        val dif = currentDate - calendar.timeInMillis
        Log.d(TAG, "getTime: $dif")
        time = if (dif > DAY) {
            "${dif/DAY}d"
        }else if (dif > HOUR){
            "${dif/HOUR}h"
        }else{
            "${dif/MINUTE}m"
        }

    return time
    }
    fun msToMin(ms:Int):String{
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(ms.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(ms.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms.toLong()))
        )
    }

    fun trackKey(key:Int):String{
        var k :String? = null
        when (key){
            0 -> k = "C"
            1 ->  k =  "C#"
            2 ->  k =  "D"
            3 ->  k =  "D#"
            4 ->  k =  "E"
            5 ->  k =  "F"
            6 ->  k =  "F#"
            7 ->  k =  "G"
            8 ->  k =  "G#"
            9 ->  k =  "A"
            10 ->  k =  "A#"
            11 ->  k =  "B"
        }
        return k!!
    }
    //returns if the note is major or minor
    fun trackMode(mode:Int):String{
        var m: String? = null
        when(mode){
            0 -> m = "m"
            1 -> m = ""
        }
        return m!!
    }
    fun encodeString(str:String): String {
        return URLEncoder.encode(str, "utf-8")
    }
    fun selectArtistLayout(detailed: Boolean): Int {
        //returns layout if adapter for detailedresultfragment or searchfragment
        return if (detailed){
            R.layout.detailed_result_artist_single
        }else{
            R.layout.search_artist_single
        }
    }
}