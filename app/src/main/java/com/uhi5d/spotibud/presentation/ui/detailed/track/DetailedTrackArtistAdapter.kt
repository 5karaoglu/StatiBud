package com.uhi5d.spotibud.presentation.ui.detailed.track

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.databinding.DetailedTrackLayoutArtistSingleItemBinding
import com.uhi5d.spotibud.domain.model.artist.Artist
import com.uhi5d.spotibud.util.BaseViewHolder

class DetailedTrackArtistAdapter(
    var context: Context,
    var clickListener: OnItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = listOf<Artist>()
    fun setArtistList(list: List<Artist>){
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
       val binding = DetailedTrackLayoutArtistSingleItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ArtistViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onItemClicked(item: Artist)
    }

    inner class ArtistViewHolder(
        private val binding: DetailedTrackLayoutArtistSingleItemBinding
    ): BaseViewHolder<Artist>(binding.root){
        override fun bind(item: Artist) {
            with(binding){
                Picasso.get()
                    .load(item.images?.get(0)?.url)
                    .fit().centerCrop()
                    .into(imageViewTrackArtist)

                textViewTrackArtistName.text = item.name

                itemView.setOnClickListener {
                    clickListener.onItemClicked(item)
                }
            }
        }

    }
}