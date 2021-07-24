package com.uhi5d.spotibud.presentation.ui.trackrecommendation

/*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.model.QueryResultTrackItem
import com.uhi5d.spotibud.model.QueryResults

import com.squareup.picasso.Picasso

class TfSearchResultsAdapter(
    var context: Context,
    var dataList: QueryResults,
    var clickListener : OnItemClickListener
) : RecyclerView.Adapter<TfSearchResultsAdapter.TfViewHolder>() {


    class TfViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivImage = itemView.findViewById<ImageView>(R.id.ivTfItem)
        var tvName = itemView.findViewById<TextView>(R.id.tvTfItem)
        var tvArtistName = itemView.findViewById<TextView>(R.id.tvTfItemArtist)


        fun bind(queryResultTrackItem: QueryResultTrackItem, clickListener: OnItemClickListener){
            Picasso.get()
                .load(queryResultTrackItem.album.images[0].url)
                .fit().centerCrop()
                .into(ivImage)
            tvName.text = queryResultTrackItem.name
            tvArtistName.text = queryResultTrackItem.album.artists[0].name

            itemView.setOnClickListener {
                clickListener.onItemClicked(queryResultTrackItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TfViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.tf_search_item,parent,false)
        return TfViewHolder(view)
    }


    override fun onBindViewHolder(holder: TfViewHolder, position: Int) {
        var currentResult= dataList.tracks.items[position]
        holder.bind(currentResult,clickListener)
    }

    override fun getItemCount(): Int {
        return dataList.tracks.items.size
    }
    interface OnItemClickListener{
        fun onItemClicked(queryResultTrackItem: QueryResultTrackItem)
    }

}*/
