package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.TrackItems
import com.example.contestifyfirsttry.model.ArtistAlbums
import com.example.contestifyfirsttry.model.ArtistAlbumsItems
import com.example.contestifyfirsttry.model.ArtistTopTracks
import com.example.contestifyfirsttry.model.TracksTopTrack
import com.squareup.picasso.Picasso

class ArtistAlbumsAdapter (
    var context: Context,
    var dataList: ArtistAlbums,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ArtistAlbumsAdapter.ArtistAlbumsViewHolder>() {


    class ArtistAlbumsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.ivDetailedAlbum)
        var textView = itemView.findViewById<TextView>(R.id.tvDetailedAlbum)

        fun bind(album: ArtistAlbumsItems, position: Int, clickListener: OnItemClickListener){
            Picasso.get()
                .load(album.images[0].url)
                .fit().centerCrop()
                .into(imageView)
            textView.text = album.name

            itemView.setOnClickListener {
                clickListener.onItemClicked(album)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistAlbumsViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.detailed_single_album_layout, parent, false)

        return ArtistAlbumsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistAlbumsViewHolder, position: Int) {

        var album = dataList.items[position]
        holder.bind(album,position,itemClickListener)


    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }

    interface OnItemClickListener{
        fun onItemClicked(album: ArtistAlbumsItems)
    }
}