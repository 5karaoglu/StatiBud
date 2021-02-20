package com.example.contestifyfirsttry.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.RecommendationTrack
import com.example.contestifyfirsttry.model.Recommendations
import com.squareup.picasso.Picasso

class RecommendationsAdapter(
    var context: Context,
    var dataList: Recommendations,
    var clickListener : OnItemClickListener) : RecyclerView.Adapter<RecommendationsAdapter.RecommendationsViewHolder>() {


    class RecommendationsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivImage = itemView.findViewById<ImageView>(R.id.ivRecommendation)
        var tvName = itemView.findViewById<TextView>(R.id.tvRecommendation)
        var tvArtistName = itemView.findViewById<TextView>(R.id.tvRecommendationArtist)


        fun bind(recommendationTrack: RecommendationTrack, clickListener: OnItemClickListener){
            Picasso.get()
                .load(recommendationTrack.album.images[0].url)
                .fit().centerCrop()
                .into(ivImage)
            tvName.text = recommendationTrack.name
            tvArtistName.text = recommendationTrack.album.artists[0].name

            itemView.setOnClickListener {
                clickListener.onItemClicked(recommendationTrack)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.recommendation_single_item,parent,false)
        return RecommendationsViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int) {
        var recommendationTrack= dataList.tracks[position]
        holder.bind(recommendationTrack,clickListener)
    }

    override fun getItemCount(): Int {
        return dataList.tracks.size
    }
    interface OnItemClickListener{
        fun onItemClicked(recommendationTrack: RecommendationTrack)
    }

}