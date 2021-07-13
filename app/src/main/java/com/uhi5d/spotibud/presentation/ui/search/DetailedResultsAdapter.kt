package com.uhi5d.spotibud.presentation.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.databinding.SearchAlbumtrackSingleBinding
import com.uhi5d.spotibud.databinding.SearchArtistSingleBinding
import com.uhi5d.spotibud.domain.model.searchresults.SearchResults
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsAlbumsItem
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsArtistsItem
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsTracksItem
import com.uhi5d.spotibud.util.BaseViewHolder

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
    private var type: DataType? = null
    fun setDataType(string: String){
        type = DataType.valueOf(string)
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
            is TrackViewHolder -> holder.bind(searchResults?.tracks?.items?.get(position-1)!!)
            is AlbumViewHolder -> holder.bind(searchResults?.albums?.items?.get(position-1)!!)
            is ArtistViewHolder -> holder.bind(searchResults?.artists?.items?.get(position-1)!!)
        }
    }

    override fun getItemCount(): Int =
        searchResults?.albums?.items?.size!! +
                searchResults?.tracks?.items?.size!! +
                searchResults?.artists?.items?.size!!

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            DataType.TRACKS -> 0
            DataType.ALBUMS -> 1
            DataType.ARTISTS -> 2
            null -> -1
        }
    }

    inner class TrackViewHolder(
        private val binding: SearchAlbumtrackSingleBinding
    ): BaseViewHolder<SearchResultsTracksItem>(binding.root){
        override fun bind(item: SearchResultsTracksItem) {
            with(binding){
                Picasso.get()
                    .load(item.album?.images?.get(0)?.url)
                    .fit().centerInside()
                    .into(ivImage)

                tvName.text = item.name
                tvNameArtist.text = item.artists?.get(0)?.items?.get(0)?.name

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
                Picasso.get()
                    .load(item.images?.get(0)?.url)
                    .fit().centerInside()
                    .into(ivImage)

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
                Picasso.get()
                    .load(item.images?.get(0)?.url)
                    .fit().centerInside()
                    .into(ivArtistImage)

                tvArtistImage.text = item.name

                binding.root.setOnClickListener {
                    itemClickListener.onArtistItemClicked(item)
                }
            }
        }
    }


}