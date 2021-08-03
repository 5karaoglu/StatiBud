package com.uhi5d.statibud.presentation.ui.search.detailedresults

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.statibud.databinding.SearchAlbumtrackSingleBinding
import com.uhi5d.statibud.databinding.SearchArtistSingleBinding
import com.uhi5d.statibud.domain.model.searchresults.SearchResults
import com.uhi5d.statibud.domain.model.searchresults.SearchResultsAlbumsItem
import com.uhi5d.statibud.domain.model.searchresults.SearchResultsArtistsItem
import com.uhi5d.statibud.domain.model.searchresults.SearchResultsTracksItem
import com.uhi5d.statibud.util.BaseViewHolder
import com.uhi5d.statibud.util.loadWithPicasso

class DetailedResultsAdapter(
    private val context: Context,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnItemClickListener{
        fun onTrackItemClicked(item: SearchResultsTracksItem)
        fun onArtistItemClicked(item: SearchResultsArtistsItem)
        fun onAlbumItemClicked(item: SearchResultsAlbumsItem)
    }


    private var searchResults: SearchResults? = null
    fun setSearchResults(searchResults: SearchResults){
        this.searchResults = searchResults
        notifyDataSetChanged()
    }
    private var type: String? = null
    fun setDataType(string: String){
        when(string){
            "Artists" -> type = "artist"
            "Tracks" -> type = "track"
            "Albums" -> type = "album"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> { val itemBinding = SearchAlbumtrackSingleBinding.inflate(
                LayoutInflater.from(context),
                parent,false)
                TrackViewHolder(itemBinding)}
            1 -> { val itemBinding = SearchAlbumtrackSingleBinding.inflate(
                LayoutInflater.from(context),
                parent,false)
                AlbumViewHolder(itemBinding)}
            2 -> {val itemBinding = SearchArtistSingleBinding.inflate(
                LayoutInflater.from(context),
                parent,false)
                ArtistViewHolder(itemBinding)}
            else -> throw RuntimeException("Could not inflate layout")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TrackViewHolder -> holder.bind(searchResults?.tracks?.items?.get(position)!!)
            is AlbumViewHolder -> holder.bind(searchResults?.albums?.items?.get(position)!!)
            is ArtistViewHolder -> holder.bind(searchResults?.artists?.items?.get(position)!!)
        }
    }

    override fun getItemCount(): Int {
        return when(type){
            "track" -> searchResults?.tracks?.items?.size ?: 0
            "album" ->  searchResults?.albums?.items?.size ?: 0
            "artist" ->  searchResults?.artists?.items?.size ?: 0
            else -> -1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            "track" -> 0
            "album" -> 1
            "artist" -> 2
            else -> -1
        }
    }

    inner class TrackViewHolder(
        private val binding: SearchAlbumtrackSingleBinding
    ): BaseViewHolder<SearchResultsTracksItem>(binding.root){
        override fun bind(item: SearchResultsTracksItem) {
            with(binding){
                if (item.album!!.images!!.isNotEmpty()) {
                    ivImage.loadWithPicasso(item.album.images!![0].url!!)}

                tvName.text = item.name
                tvNameArtist.text = item.artists?.get(0)?.name

                binding.root.setOnClickListener {
                    itemClickListener.onTrackItemClicked(item)
                }
            }
        }
    }
    inner class AlbumViewHolder(
        private val binding: SearchAlbumtrackSingleBinding
    ): BaseViewHolder<SearchResultsAlbumsItem>(binding.root){
        override fun bind(item: SearchResultsAlbumsItem) {
            with(binding){
                if (item.images!!.isNotEmpty())
                    ivImage.loadWithPicasso(item.images[0].url!!)

                tvName.text = item.name
                tvNameArtist.text = item.artists?.get(0)?.name

                binding.root.setOnClickListener {
                    itemClickListener.onAlbumItemClicked(item)
                }
            }
        }
    }
    inner class ArtistViewHolder(
        private val binding: SearchArtistSingleBinding
    ): BaseViewHolder<SearchResultsArtistsItem>(binding.root){
        override fun bind(item: SearchResultsArtistsItem) {
            with(binding){
                if (item.images!!.isNotEmpty())
                    ivImage.loadWithPicasso(item.images[0].url!!)

                tvName.text = item.name

                binding.root.setOnClickListener {
                    itemClickListener.onArtistItemClicked(item)
                }
            }
        }
    }


}