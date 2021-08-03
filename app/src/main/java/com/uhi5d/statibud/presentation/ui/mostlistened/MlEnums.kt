package com.uhi5d.statibud.presentation.ui.mostlistened

enum class TimeRange(val str: String){
    SHORT("short_term"),
    MEDIUM("medium_term"),
    LONG("long_term")
}

val timeRangeList = listOf<String>("short_term","medium_term","long_term")

enum class MlType{
    ARTISTS,TRACKS
}