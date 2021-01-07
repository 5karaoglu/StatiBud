package com.example.contestifyfirsttry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class TracksAdapter(var context: Context, var dataList:Tracks) : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {

    class TracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var textView = itemView.findViewById<TextView>(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.single_artist_layout,parent,false)
        return TracksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        Picasso.get()
            .load(dataList.items[position].album.images[2].url)
            .into(holder.imageView)

        holder.textView.text = dataList.items[position].name

    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }
}