package com.uhi5d.statibud.presentation.ui.detailed.artist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.statibud.databinding.DetailedSingleAlbumLayoutBinding
import com.uhi5d.statibud.domain.model.artistalbums.ArtistAlbumsItem
import com.uhi5d.statibud.util.BaseViewHolder
import com.uhi5d.statibud.util.loadWithPicasso

class ArtistAlbumsAdapter (
    var context: Context,
    var itemClickListener: OnItemClickListener
) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemClicked(item: ArtistAlbumsItem)
    }

    private var list = listOf<ArtistAlbumsItem>()
    fun setList(list: List<ArtistAlbumsItem>){
        this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DetailedSingleAlbumLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return AlbumsViewHolder(binding)
    }


    override fun getItemCount(): Int = list.size



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AlbumsViewHolder -> holder.bind(list[position])
        }
    }

    inner class AlbumsViewHolder(
        private val binding: DetailedSingleAlbumLayoutBinding
    ): BaseViewHolder<ArtistAlbumsItem>(binding.root){
        override fun bind(item: ArtistAlbumsItem) {
            with(binding){
                ivDetailedAlbum.loadWithPicasso(item.images!![0].url!!)
                tvDetailedAlbum.text = item.name
                root.setOnClickListener {
                    itemClickListener.onItemClicked(item)
                }
            }
        }

    }
}