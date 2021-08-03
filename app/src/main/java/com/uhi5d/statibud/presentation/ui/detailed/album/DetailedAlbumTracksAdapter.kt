package com.uhi5d.statibud.presentation.ui.detailed.album

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.statibud.databinding.DetailedAlbumTrackSingleItemBinding
import com.uhi5d.statibud.domain.model.albumstracks.AlbumsTracksItem
import com.uhi5d.statibud.util.BaseViewHolder


class DetailedAlbumTracksAdapter(
    var context: Context,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = listOf<AlbumsTracksItem>()
    fun setAlbumTracks(list: List<AlbumsTracksItem>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTracksViewHolder {
        val binding = DetailedAlbumTrackSingleItemBinding.inflate(LayoutInflater.from(context)
            ,parent,false)

        return AlbumTracksViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder:  RecyclerView.ViewHolder,
        position: Int
    ) {
        when(holder){
            is AlbumTracksViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClicked(item: AlbumsTracksItem)

    }

    inner class AlbumTracksViewHolder(
        private val binding: DetailedAlbumTrackSingleItemBinding
    ): BaseViewHolder<AlbumsTracksItem>(binding.root){
        override fun bind(item: AlbumsTracksItem) {
            with(binding){
                tvTrackName.text = item.name
                tvArtistName.text = item.artists!![0].name
                if (item.explicit == true) {
                    ivIsExplicit.visibility = View.VISIBLE
                }

                itemView.setOnClickListener {
                    itemClickListener.onItemClicked(item)
                }
            }
        }

    }


}