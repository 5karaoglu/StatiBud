package com.uhi5d.spotibud.detailed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.model.*


class DetailedAlbumTracksAdapter (
    var context: Context,
    var dataList: Album,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DetailedAlbumTracksAdapter.AlbumTracksViewHolder>() {


    class AlbumTracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.ivIsExplicit)
        var textViewName = itemView.findViewById<TextView>(R.id.tvTrackName)
        var textViewArtist = itemView.findViewById<TextView>(R.id.tvArtistName)

        fun bind(track: AlbumItems, position: Int, clickListener:OnItemClickListener) {

            textViewName.text = track.name
            textViewArtist.text = track.artists[0].name
            if (track.explicit){
                imageView.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                clickListener.onItemClicked(track)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTracksViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.detailed_album_track_single_item, parent, false)

        return AlbumTracksViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AlbumTracksViewHolder,
        position: Int
    ) {
        var track = dataList.tracks.items[position]
        holder.bind(track, position,itemClickListener)
    }

    override fun getItemCount(): Int {
        return dataList.tracks.items.size
    }

    interface OnItemClickListener {
        fun onItemClicked(track: AlbumItems)

    }


}