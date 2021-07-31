package com.uhi5d.spotibud.presentation.ui.mostlistened

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.databinding.SingleMlLayoutBinding
import com.uhi5d.spotibud.databinding.SingleMlTrackLayoutBinding
import com.uhi5d.spotibud.domain.model.MyArtistsItem
import com.uhi5d.spotibud.domain.model.mytracks.MyTracksItem
import com.uhi5d.spotibud.presentation.ui.mostlistened.MlType.ARTISTS
import com.uhi5d.spotibud.presentation.ui.mostlistened.MlType.TRACKS
import com.uhi5d.spotibud.util.BaseViewHolder
import com.uhi5d.spotibud.util.loadWithPicasso

class MostListenedAdapter(
    private val context: Context,
    private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnItemClickListener{
        fun onTrackItemClicked(item: MyTracksItem)
        fun onArtistItemClicked(item: MyArtistsItem)
    }


    private var artistsList = listOf<MyArtistsItem>()
    fun setArtistsList(list: List<MyArtistsItem>){
        artistsList = list
        mlType = ARTISTS
        notifyDataSetChanged()
    }

    private var tracksList = listOf<MyTracksItem>()
    fun setTracksList(list: List<MyTracksItem>){
        tracksList = list
        mlType = TRACKS
        notifyDataSetChanged()
    }

    private var mlType : MlType = TRACKS
    fun setMlType(type: MlType){
        mlType = type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> {
                val itemBinding = SingleMlLayoutBinding.inflate(LayoutInflater.from(context),parent,
                    false)
                ArtistsViewHolder(itemBinding)
            }
            1 -> {
                val itemBinding = SingleMlTrackLayoutBinding.inflate(LayoutInflater.from(context),parent,
                    false)
                TracksViewHolder(itemBinding)
            }
            else -> {
                val itemBinding = SingleMlTrackLayoutBinding.inflate(LayoutInflater.from(context),parent,
                    false)
                TracksViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TracksViewHolder -> {holder.bind(tracksList[position])}
            is ArtistsViewHolder -> {holder.bind(artistsList[position])}
        }
    }

    override fun getItemCount(): Int {
        return when(mlType){
            ARTISTS -> {artistsList.size}
            TRACKS -> {tracksList.size}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(mlType){
            ARTISTS -> {0}
            TRACKS -> {1}
        }
    }

    inner class TracksViewHolder(
        private val itemBinding : SingleMlTrackLayoutBinding
    ): BaseViewHolder<MyTracksItem>(itemBinding.root){
        override fun bind(item: MyTracksItem) {
            with(itemBinding){
                Picasso.get()
                    .load(item.album?.images?.get(0)?.url)
                    .fit().centerInside()
                    .into(iv)

                tvName.text = item.name
                tvArtistName.text = item.artists?.get(0)?.name ?: ""
                tvRank.text = (tracksList.indexOf(item) + 1).toString()

                root.setOnClickListener {
                    itemClickListener.onTrackItemClicked(item)
                }
            }
        }

    }

    inner class ArtistsViewHolder(
        private val itemBinding : SingleMlLayoutBinding
    ): BaseViewHolder<MyArtistsItem>(itemBinding.root){
        override fun bind(item: MyArtistsItem) {
            with(itemBinding){
                iv.loadWithPicasso(item.images?.get(0)?.url!!)

                tvName.text = item.name
                tvRank.text = (artistsList.indexOf(item) + 1).toString()

                root.setOnClickListener {
                    itemClickListener.onArtistItemClicked(item)
                }
            }
        }
    }
}