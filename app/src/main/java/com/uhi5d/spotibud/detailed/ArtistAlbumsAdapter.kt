package com.uhi5d.spotibud.detailed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.model.ArtistAlbums
import com.uhi5d.spotibud.model.ArtistAlbumsItems
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