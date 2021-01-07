package com.example.contestifyfirsttry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ArtistsAdapter(var context:Context,var dataList:Artists) : RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>() {

    class ArtistsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var textView = itemView.findViewById<TextView>(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.single_artist_layout,parent,false)
        return ArtistsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {
       Picasso.get()
           .load(dataList.items[position].images[2].url)
           .into(holder.imageView)

        holder.textView.text = dataList.items[position].name

    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }
}