package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.*
import com.example.contestifyfirsttry.model.ArtistTopTracks
import com.example.contestifyfirsttry.model.Item
import com.example.contestifyfirsttry.model.TracksTopTrack
import com.example.contestifyfirsttry.top.ArtistsAdapter
import com.squareup.picasso.Picasso

class ArtistTopTracksAdapter(
    var context: Context,
    var dataList: ArtistTopTracks,
    var clickListener: OnItemClickListener
) : RecyclerView.Adapter<ArtistTopTracksAdapter.ArtistTopTracksViewHolder>() {


    class ArtistTopTracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.ivDetailedTopTrack)
        var textView = itemView.findViewById<TextView>(R.id.tvDetailedTopTrack)
        var textViewRank = itemView.findViewById<TextView>(R.id.tvDetailedTopTrackRank)

        fun bind(track: TracksTopTrack, position: Int,clickListener: OnItemClickListener){
            Picasso.get()
                .load(track.album.images[0].url)
                .fit().centerCrop()
                .into(imageView)
            textViewRank.text = String.format("%d ",position+1)
            textView.text = track.name

            itemView.setOnClickListener {
                clickListener.onItemClicked(track)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistTopTracksViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.detailed_single_track_item, parent, false)

        return ArtistTopTracksViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistTopTracksViewHolder, position: Int) {

        var track = dataList.tracks[position]
        holder.bind(track,position, clickListener)


    }

    override fun getItemCount(): Int {
        return dataList.tracks.size
    }

    interface OnItemClickListener{
        fun onItemClicked(track: TracksTopTrack)
    }
}