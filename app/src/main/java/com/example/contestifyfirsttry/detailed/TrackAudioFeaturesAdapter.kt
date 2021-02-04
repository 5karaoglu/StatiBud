package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.RelatedArtist
import com.example.contestifyfirsttry.model.RelatedArtists
import com.example.contestifyfirsttry.model.TrackAudioFeatures
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.squareup.picasso.Picasso

class TrackAudioFeaturesAdapter (
    var context: Context,
    var dataList: TrackAudioFeatures
) : RecyclerView.Adapter<TrackAudioFeaturesAdapter.FeatureViewHolder>() {


    class FeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar = itemView.findViewById<CircularProgressBar>(R.id.pbTrackAudioFeature)
        var textView = itemView.findViewById<TextView>(R.id.tvTrackAudioFeature)

        fun bind(trackAudioFeatures: TrackAudioFeatures) {
           progressBar.progress = trackAudioFeatures.danceability.toFloat()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.detailed_track_audio_feature_single_item, parent, false)

        return FeatureViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FeatureViewHolder,
        position: Int
    ) {
        holder.bind(dataList)
    }

    override fun getItemCount(): Int {
        return 1
    }


}