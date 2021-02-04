package com.example.contestifyfirsttry.top

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.Artists
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.Item
import com.squareup.picasso.Picasso

class ArtistsAdapter(
    var context: Context,
    var dataList: Artists, var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>() {


    class ArtistsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var textView = itemView.findViewById<TextView>(R.id.textView)
        var textViewPop = itemView.findViewById<TextView>(R.id.textViewPop)

        fun bind(artist: Item, position: Int, clickListener: OnItemClickListener){
            Picasso.get()
                .load(artist.images[0].url)
                .fit().centerCrop()
                .into(imageView)
            textView.text = artist.name
            textViewPop.text = String.format("#%d",position+1)

            itemView.setOnClickListener {
                clickListener.onItemClicked(artist)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.single_artist_layout, parent, false)

        return ArtistsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {

        var artist = dataList.items[position]
        holder.bind(artist,position,itemClickListener)


    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }

    interface OnItemClickListener{
        fun onItemClicked(artist: Item)
    }
}