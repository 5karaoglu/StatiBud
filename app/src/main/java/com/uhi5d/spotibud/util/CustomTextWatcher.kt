package com.uhi5d.spotibud.util

import android.text.Editable
import android.text.NoCopySpan

interface TextWatcher :NoCopySpan {
    fun afterTextChanged(s: Editable?)
}