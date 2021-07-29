package com.uhi5d.spotibud.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.domain.model.track.TrackArtist
import java.util.*

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        show()
    } else {
        hide()
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, duration)
}

fun getLocation(context: Context): String? {
    return context.resources.configuration.locales.getFirstMatch(Locale.getISOCountries())?.country
}

fun ImageView.loadWithPicasso(uri: String?) {
    if (uri != null) {
        Picasso.get()
            .load(uri)
            .fit().centerInside()
            .error(R.drawable.ic_baseline_not_interested_24)
            .into(this)

    }
}

fun List<TrackArtist>.toQuery(): String {
    var str = ""
    if (this.size > 1){
        for(i in this){
            str = "$str${i.id},"
        }
    }else{
        str = this[0].id.toString()
    }
    if (str.endsWith(",")){
        str = str.substring(0,str.length-1)
    }

    Log.d(TAG, "getArtists: $str")
    return str
}

fun List<String>.toSeed(): String{
    var str = ""
    for (i in this){
       str = "$str$i,"
    }
    if (str.endsWith(",")){
        str = str.substring(0,str.length - 1)
    }
    return str
}
