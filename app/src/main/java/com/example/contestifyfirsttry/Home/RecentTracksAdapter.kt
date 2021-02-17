package com.example.contestifyfirsttry.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.Functions
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.Items
import com.example.contestifyfirsttry.model.QueryResultTrackItem
import com.example.contestifyfirsttry.model.RecentTracks
import com.squareup.picasso.Picasso

class RecentTracksAdapter(var context: Context, var dataList: RecentTracks, var clickListener: OnItemClickListener, var customItemCount: Int) : RecyclerView.Adapter<RecentTracksAdapter.RecentTracksViewHolder>() {


    class RecentTracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivRecentTrack = itemView.findViewById<ImageView>(R.id.ivRecentTrack)
        var tvRecentTrack = itemView.findViewById<TextView>(R.id.tvRecentTrack)
        var tvRecentTrackArtist = itemView.findViewById<TextView>(R.id.tvRecentTrackArtist)
        var tvHistory = itemView.findViewById<TextView>(R.id.tvHistory)

        fun bind(recentTrack: Items, clickListener: OnItemClickListener){
            Picasso.get()
                .load(recentTrack.track.album.images[0].url)
                .into(ivRecentTrack)

            tvRecentTrack.text = recentTrack.track.name
            tvRecentTrackArtist.text = recentTrack.track.artists[0].name
            tvHistory.text = Functions().getTime(recentTrack.played_at).toString()

            itemView.setOnClickListener {
                clickListener.onItemClicked(recentTrack)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTracksViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.single_recent_played,parent,false)
        return RecentTracksViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecentTracksViewHolder, position: Int) {
        var recentTrack = dataList.items[position]
        holder.bind(recentTrack,clickListener)

    }

    override fun getItemCount(): Int {
        return customItemCount
    }
    interface OnItemClickListener{
        fun onItemClicked(recentTrack: Items)
    }
}