package com.uhi5d.statibud.presentation.ui.detailed.artist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.statibud.databinding.DetailedSingleTrackItemBinding
import com.uhi5d.statibud.domain.model.artisttoptracks.ArtistTopTracksTrack
import com.uhi5d.statibud.util.BaseViewHolder
import com.uhi5d.statibud.util.loadWithPicasso

class ArtistTopTracksAdapter(
    var context: Context,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemClicked(item: ArtistTopTracksTrack)
    }

    private var list = listOf<ArtistTopTracksTrack>()
    fun setList(list: List<ArtistTopTracksTrack>){
        this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DetailedSingleTrackItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return TopTracksViewHolder(binding)
    }


    override fun getItemCount(): Int = list.size



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TopTracksViewHolder -> holder.bind(list[position])
        }
    }

    inner class TopTracksViewHolder(
        private val binding: DetailedSingleTrackItemBinding
    ): BaseViewHolder<ArtistTopTracksTrack>(binding.root){
        override fun bind(item: ArtistTopTracksTrack) {
            with(binding){
                tvRank.text = (list.indexOf(item) + 1).toString()
                iv.loadWithPicasso(item.album!!.images!![0].url!!)
                tvTrackName.text = item.name
                tvAlbumName.text = item.album.name

                root.setOnClickListener {
                    itemClickListener.onItemClicked(item)
                }
            }
        }

    }
}