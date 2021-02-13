package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.ArtistList
import com.example.contestifyfirsttry.model.ArtistListArtists
import com.squareup.picasso.Picasso

class DetailedTrackArtistAdapter(
    var context: Context,
    var dataList: ArtistList,
    var clickListener: OnItemClickListener
) : RecyclerView.Adapter<DetailedTrackArtistAdapter.TrackArtistAdapter>() {


    class TrackArtistAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageViewTrackArtist)
        var textView = itemView.findViewById<TextView>(R.id.textViewTrackArtistName)


        fun bind(artist:ArtistListArtists, clickListener: OnItemClickListener){
            Picasso.get()
                .load(artist.images[0].url)
                .fit().centerCrop()
                .into(imageView)

            textView.text = artist.name

            itemView.setOnClickListener {
                clickListener.onItemClicked(artist)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackArtistAdapter {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.detailed_track_layout_artist_single_item, parent, false)

        return TrackArtistAdapter(view)
    }

    override fun onBindViewHolder(holder: TrackArtistAdapter, position: Int) {

        holder.bind(dataList.artists[position], clickListener)


    }

    override fun getItemCount(): Int {
        return dataList.artists.size
    }

    interface OnItemClickListener{
        fun onItemClicked(artist: ArtistListArtists)
    }
}