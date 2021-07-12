package com.uhi5d.spotibud.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.databinding.SearchAlbumtrackSingleBinding
import com.uhi5d.spotibud.databinding.SearchArtistSingleBinding
import com.uhi5d.spotibud.databinding.SearchResultsHeaderBinding
import com.uhi5d.spotibud.domain.model.searchresults.SearchResults
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsAlbumsItem
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsArtistsItem
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsTracksItem
import com.uhi5d.spotibud.util.BaseViewHolder

class SearchResultsAdapter(
    private val context: Context,
    private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnItemClickListener{
        fun onTrackItemClicked(item: SearchResultsTracksItem)
        fun onArtistItemClicked(item: SearchResultsArtistsItem)
        fun onAlbumItemClicked(item: SearchResultsAlbumsItem)
        fun onHeaderItemClicked(header: String)
    }
    private val TrackHeaderPos = 0
    private val TrackBodyPos = 1
    private val AlbumHeaderPos = 2
    private val AlbumBodyPos = 3
    private val ArtistHeaderPos = 4
    private val ArtistBodyPos = 5

    private var searchResults: SearchResults? = null
    fun setSearchResults(searchResults: SearchResults){
        this.searchResults = searchResults
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TrackHeaderPos -> { val itemBinding = SearchResultsHeaderBinding.inflate(LayoutInflater.from(context),
                parent,false)
            HeaderViewHolder(itemBinding)}
            TrackBodyPos -> { val itemBinding = SearchAlbumtrackSingleBinding.inflate(LayoutInflater.from(context),
                parent,false)
            TrackViewHolder(itemBinding)}
            AlbumHeaderPos -> {val itemBinding = SearchResultsHeaderBinding.inflate(LayoutInflater.from(context),
                parent,false)
                HeaderViewHolder(itemBinding)}
            AlbumBodyPos -> { val itemBinding = SearchAlbumtrackSingleBinding.inflate(LayoutInflater.from(context),
                parent,false)
                AlbumViewHolder(itemBinding)}
            ArtistHeaderPos -> {val itemBinding = SearchResultsHeaderBinding.inflate(LayoutInflater.from(context),
                parent,false)
                HeaderViewHolder(itemBinding)}
            ArtistBodyPos -> { val itemBinding = SearchArtistSingleBinding.inflate(LayoutInflater.from(context),
                parent,false)
                ArtistViewHolder(itemBinding)}
            else -> throw RuntimeException("Could not inflate layout")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HeaderViewHolder -> {
                when(position){
                    0 -> holder.bind("Tracks")
                    4 -> holder.bind("Albums")
                    8 -> holder.bind("Artists")
                }
            }
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
        return when(position){
            0 -> TrackHeaderPos
            1 -> TrackBodyPos
            2 -> TrackBodyPos
            3 -> TrackBodyPos
            4 -> ArtistHeaderPos
            5 -> ArtistBodyPos
            6 -> ArtistBodyPos
            7 -> ArtistBodyPos
            8 -> AlbumHeaderPos
            9 -> AlbumBodyPos
            10 -> AlbumBodyPos
            11 -> AlbumBodyPos
            else -> -1
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
    inner class HeaderViewHolder(
        private val binding: SearchResultsHeaderBinding
    ) : BaseViewHolder<String>(binding.root) {
        override fun bind(item: String) {
            binding.tv.text = ""

            binding.root.setOnClickListener {
                itemClickListener.onHeaderItemClicked(binding.tv.text.toString())
            }
        }
    }

}