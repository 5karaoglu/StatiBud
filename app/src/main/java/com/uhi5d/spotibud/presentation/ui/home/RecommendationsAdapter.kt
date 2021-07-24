package com.uhi5d.spotibud.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.databinding.RecommendationSingleItemBinding
import com.uhi5d.spotibud.domain.model.recommendations.RecommendationsTrack
import com.uhi5d.spotibud.util.BaseViewHolder

class RecommendationsAdapter(
    var context: Context,
    var clickListener : OnItemClickListener
) : RecyclerView.Adapter<BaseViewHolder<RecommendationsTrack>>() {
    interface OnItemClickListener{
        fun onItemClicked(recommendationsTrack: RecommendationsTrack)
    }

    private var trackList = listOf<RecommendationsTrack>()

    fun setTrackList(list: List<RecommendationsTrack>){
        trackList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RecommendationsTrack> {
        val itemBinding = RecommendationSingleItemBinding.inflate(LayoutInflater.from(context),
        parent,false)

        return RecommendationsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<RecommendationsTrack>, position: Int) {
        when(holder){
            is RecommendationsViewHolder -> holder.bind(trackList[position])
        }
    }

    override fun getItemCount(): Int = trackList.size

    private inner class RecommendationsViewHolder(
        private val binding: RecommendationSingleItemBinding
    ): BaseViewHolder<RecommendationsTrack>(binding.root){
        override fun bind(item: RecommendationsTrack) {
           with(binding){
               Picasso.get()
                   .load(item.album?.images?.get(0)?.url)
                   .fit().centerInside()
                   .into(ivRecommendation)

               tvRecommendation.text = item.name
               tvRecommendationArtist.text = item.artists?.get(0)?.name

               binding.root.setOnClickListener {
                   clickListener.onItemClicked(item)
               }
           }
        }

    }
}