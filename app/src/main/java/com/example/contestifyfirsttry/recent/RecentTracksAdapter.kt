package com.example.contestifyfirsttry.recent

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
import com.example.contestifyfirsttry.model.RecentTracks
import com.squareup.picasso.Picasso

class RecentTracksAdapter(var context: Context, var dataList: RecentTracks, var viewModel: MainViewModel) : RecyclerView.Adapter<RecentTracksAdapter.RecentTracksViewHolder>() {


    class RecentTracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivRecentTrack = itemView.findViewById<ImageView>(R.id.ivRecentTrack)
        var tvRecentTrack = itemView.findViewById<TextView>(R.id.tvRecentTrack)
        var tvRecentTrackArtist = itemView.findViewById<TextView>(R.id.tvRecentTrackArtist)
        var tvHistory = itemView.findViewById<TextView>(R.id.tvHistory)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTracksViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.single_recent_played,parent,false)
        return RecentTracksViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecentTracksViewHolder, position: Int) {
        Picasso.get()
            .load(dataList.items[position].track.album.images[0].url)
            .into(holder.ivRecentTrack)

        holder.tvRecentTrack.text = dataList.items[position].track.name
        holder.tvRecentTrackArtist.text = dataList.items[position].track.artists[0].name
        holder.tvHistory.text = Functions().getTime(dataList.items[position].played_at).toString()

    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }
}