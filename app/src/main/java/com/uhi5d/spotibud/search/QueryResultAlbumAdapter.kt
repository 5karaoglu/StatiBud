package com.uhi5d.spotibud.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.model.QueryResultAlbums
import com.uhi5d.spotibud.model.QueryResultAlbumItem
import com.squareup.picasso.Picasso

class QueryResultAlbumAdapter (
    var context: Context,
    var dataList: QueryResultAlbums,
    var clickListener : OnItemClickListener) : RecyclerView.Adapter<QueryResultAlbumAdapter.QueryResultAlbumHolder>() {


    class QueryResultAlbumHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        var tvName = itemView.findViewById<TextView>(R.id.tvName)
        var tvArtistName = itemView.findViewById<TextView>(R.id.tvNameArtist)


        fun bind(currentAlbum: QueryResultAlbumItem, clickListener: OnItemClickListener){
            Picasso.get()
                .load(currentAlbum.images[1].url)
                .fit().centerCrop()
                .into(ivImage)
            tvName.text = currentAlbum.name
            tvArtistName.text = currentAlbum.artists[0].name

            itemView.setOnClickListener {
                clickListener.onItemClicked(currentAlbum)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryResultAlbumHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.search_albumtrack_single,parent,false)
        return QueryResultAlbumHolder(view)
    }


    override fun onBindViewHolder(holder: QueryResultAlbumHolder, position: Int) {
        var resultTrack= dataList.items[position]
        holder.bind(resultTrack, clickListener)
    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }
    interface OnItemClickListener{
        fun onItemClicked(currentAlbum: QueryResultAlbumItem)
    }

}