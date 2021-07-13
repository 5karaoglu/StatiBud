package com.uhi5d.spotibud.presentation.ui.mostlistened

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.databinding.SingleArtistLayoutBinding
import com.uhi5d.spotibud.databinding.SingleTrackLayoutBinding
import com.uhi5d.spotibud.domain.model.MyArtistsItem
import com.uhi5d.spotibud.domain.model.mytracks.MyTracksItem
import com.uhi5d.spotibud.presentation.ui.mostlistened.MlType.ARTISTS
import com.uhi5d.spotibud.presentation.ui.mostlistened.MlType.TRACKS
import com.uhi5d.spotibud.util.BaseViewHolder

class MostListenedAdapter(
    private val context: Context,
    private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnItemClickListener{
        fun OnTrackItemClicked(item: MyTracksItem)
        fun OnArtistItemClicked(item: MyArtistsItem)
    }

    private var artistsList = listOf<MyArtistsItem>()
    fun setArtistsList(list: List<MyArtistsItem>){
        artistsList = list
        notifyDataSetChanged()
    }

    private var tracksList = listOf<MyTracksItem>()
    fun setTracksList(list: List<MyTracksItem>){
        tracksList = list
        notifyDataSetChanged()
    }

    private var mlType : MlType? = null
    fun setMlType(type: MlType){
        mlType = type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> {
                val itemBinding = SingleArtistLayoutBinding.inflate(LayoutInflater.from(context),parent,
                    false)
                ArtistsViewHolder(itemBinding)
            }
            1 -> {
                val itemBinding = SingleTrackLayoutBinding.inflate(LayoutInflater.from(context),parent,
                    false)
                TracksViewHolder(itemBinding)
            }
            else -> {
                val itemBinding = SingleTrackLayoutBinding.inflate(LayoutInflater.from(context),parent,
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
            null -> -1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(mlType){
            ARTISTS -> {0}
            TRACKS -> {1}
            null -> -1
        }
    }

    inner class TracksViewHolder(
        private val itemBinding : SingleTrackLayoutBinding
    ): BaseViewHolder<MyTracksItem>(itemBinding.root){
        override fun bind(item: MyTracksItem) {
            with(itemBinding){
                Picasso.get()
                    .load(item.album?.images?.get(0)?.url)
                    .fit().centerInside()
                    .into(imageViewTrack)

                textViewTrack.text = item.name
                textViewPopTrack.text = (tracksList.indexOf(item) + 1).toString()

                root.setOnClickListener {
                    itemClickListener.OnTrackItemClicked(item)
                }
            }
        }

    }

    inner class ArtistsViewHolder(
        private val itemBinding : SingleArtistLayoutBinding
    ): BaseViewHolder<MyArtistsItem>(itemBinding.root){
        override fun bind(item: MyArtistsItem) {
            with(itemBinding){
                Picasso.get()
                    .load(item.images?.get(0)?.url)
                    .fit().centerInside()
                    .into(imageView)

                textView.text = item.name
                textViewPop.text = (artistsList.indexOf(item) + 1).toString()

                root.setOnClickListener {
                    itemClickListener.OnArtistItemClicked(item)
                }
            }
        }

    }
}