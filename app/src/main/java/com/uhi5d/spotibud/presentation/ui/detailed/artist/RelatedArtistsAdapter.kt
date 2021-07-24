package com.uhi5d.spotibud.presentation.ui.detailed.artist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.databinding.DetailedRelatedArtistSingleItemBinding
import com.uhi5d.spotibud.domain.model.relatedartists.RelatedArtistsArtist
import com.uhi5d.spotibud.util.BaseViewHolder
import com.uhi5d.spotibud.util.loadWithPicasso

class RelatedArtistsAdapter (
    var context: Context,
    var itemClickListener: OnItemClickListener
) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemClicked(item: RelatedArtistsArtist)
    }

    private var list = listOf<RelatedArtistsArtist>()
    fun setList(list: List<RelatedArtistsArtist>){
        this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DetailedRelatedArtistSingleItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return RelatedViewHolder(binding)
    }


    override fun getItemCount(): Int = list.size



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RelatedViewHolder -> holder.bind(list[position])
        }
    }

    inner class RelatedViewHolder(
        private val binding: DetailedRelatedArtistSingleItemBinding
    ): BaseViewHolder<RelatedArtistsArtist>(binding.root){
        override fun bind(item: RelatedArtistsArtist) {
            with(binding){
                ivRelatedArtist.loadWithPicasso(item.images!![0].url!!)
                tvRelatedArtist.text = item.name

                root.setOnClickListener {
                    itemClickListener.onItemClicked(item)
                }
            }
        }
    }
}