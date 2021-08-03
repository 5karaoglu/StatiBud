package com.uhi5d.statibud.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhi5d.statibud.Functions
import com.uhi5d.statibud.databinding.SingleRecentPlayedBinding
import com.uhi5d.statibud.domain.model.recenttracks.RecentTracksItem
import com.uhi5d.statibud.util.BaseViewHolder

class RecentTracksAdapter(var context: Context, var clickListener: OnItemClickListener
                          ) : RecyclerView.Adapter<BaseViewHolder<RecentTracksItem>>() {

    interface OnItemClickListener{
        fun onItemClicked(recentTrack: RecentTracksItem)
    }

    var trackList = listOf<RecentTracksItem>()

    fun setRecentTracksList(list: List<RecentTracksItem>){
        trackList = list
       if(trackList.isNotEmpty()){
           notifyDataSetChanged()
       }
    }

    var iAdapterSize = 5
    fun setAdapterSize(size:Int){
        iAdapterSize = size
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RecentTracksItem> {
       val itemBinding = SingleRecentPlayedBinding.inflate(LayoutInflater.from(context),parent,false)
        return RecentTracksViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: BaseViewHolder<RecentTracksItem>, position: Int) {
       when(holder){
           is RecentTracksViewHolder -> {
               if (trackList.isNotEmpty()){
                   holder.bind(trackList[position])
               }
           }
       }

    }

    override fun getItemCount(): Int {
        return iAdapterSize
    }

    inner class RecentTracksViewHolder(
        private val binding: SingleRecentPlayedBinding
    ): BaseViewHolder<RecentTracksItem>(binding.root) {
        override fun bind(item: RecentTracksItem) {
            with(binding){
                Picasso.get()
                    .load(item.track?.album?.images?.get(0)?.url)
                    .into(ivRecentTrack)

                tvRecentTrack.text = item.track?.name
                tvRecentTrackArtist.text = item.track?.artists?.get(0)?.name
                tvHistory.text = Functions().getTime(item.playedAt!!).toString()

                itemView.setOnClickListener {
                    clickListener.onItemClicked(item)
                }
            }
        }

    }
}