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
import com.example.contestifyfirsttry.model.RelatedArtist
import com.example.contestifyfirsttry.model.RelatedArtists
import com.squareup.picasso.Picasso

class RelatedArtistsAdapter (
    var context: Context,
    var dataList: RelatedArtists,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RelatedArtistsAdapter.RelatedViewHolder>() {


    class RelatedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.ivRelatedArtist)
        var textView = itemView.findViewById<TextView>(R.id.tvRelatedArtist)

        fun bind(relatedArtist: RelatedArtist, position: Int,clickListener:OnItemClickListener) {
            if (relatedArtist.images.isNotEmpty()){
                Picasso.get()
                    .load(relatedArtist.images[0].url)
                    .fit().centerCrop()
                    .into(imageView)
            }
            textView.text = relatedArtist.name

            itemView.setOnClickListener {
                clickListener.onItemClicked(relatedArtist)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.detailed_related_artist_single_item, parent, false)

        return RelatedViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RelatedViewHolder,
        position: Int
    ) {
        var relatedArtist = dataList.artists[position]
        holder.bind(relatedArtist, position,itemClickListener)
    }

    override fun getItemCount(): Int {
        return dataList.artists.size
    }

    interface OnItemClickListener {
        fun onItemClicked(relatedArtist: RelatedArtist)

    }


}