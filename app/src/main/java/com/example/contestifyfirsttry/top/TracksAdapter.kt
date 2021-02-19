package com.example.contestifyfirsttry.top

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.TrackItems
import com.example.contestifyfirsttry.Tracks
import com.squareup.picasso.Picasso

class TracksAdapter(var context: Context, var dataList: Tracks, var clickListener: OnItemClickListener) : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {


    class TracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageViewTrack)
        var textView = itemView.findViewById<TextView>(R.id.textViewTrack)
        var textViewPop = itemView.findViewById<TextView>(R.id.textViewPopTrack)

        fun bind(track: TrackItems, position: Int, clickListener: OnItemClickListener){
            //ImageView-album photo
            Picasso.get()
                .load(track.album.images[0].url)
                .fit().centerCrop()
                .into(imageView)
            //position
            textViewPop.text = String.format("#%d",position+1)
            //track name
            textView.text = track.name
            itemView.setOnClickListener {
                clickListener.onItemClicked(track)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.single_track_layout,parent,false)


        return TracksViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        var track = dataList.items[position]
        holder.bind(track,position,clickListener)

    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }
    interface OnItemClickListener{
        fun onItemClicked(track: TrackItems)
    }
}