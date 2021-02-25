package com.uhi5d.spotibud.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.model.QueryResultTrackItem
import com.uhi5d.spotibud.model.QueryResultTracks
import com.squareup.picasso.Picasso

class QueryResultTrackAdapter (
    var context: Context,
    var dataList: QueryResultTracks,
    var clickListener : OnItemClickListener) : RecyclerView.Adapter<QueryResultTrackAdapter.QueryResultTrackHolder>() {


    class QueryResultTrackHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        var tvName = itemView.findViewById<TextView>(R.id.tvName)
        var tvArtistName = itemView.findViewById<TextView>(R.id.tvNameArtist)


        fun bind(currentTrack: QueryResultTrackItem, clickListener: OnItemClickListener){
            Picasso.get()
                .load(currentTrack.album.images[1].url)
                .fit().centerCrop()
                .into(ivImage)
            tvName.text = currentTrack.name
            tvArtistName.text = currentTrack.artists[0].name

            itemView.setOnClickListener {
                clickListener.onItemClicked(currentTrack)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryResultTrackHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.search_albumtrack_single,parent,false)
        return QueryResultTrackHolder(view)
    }


    override fun onBindViewHolder(holder: QueryResultTrackHolder, position: Int) {
        var resultTrack= dataList.items[position]
        holder.bind(resultTrack,clickListener)
    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }
    interface OnItemClickListener{
        fun onItemClicked(currentTrack: QueryResultTrackItem)
    }

}