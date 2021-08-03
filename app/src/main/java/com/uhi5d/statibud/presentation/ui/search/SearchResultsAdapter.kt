package com.uhi5d.statibud.presentation.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.statibud.databinding.SearchAlbumtrackSingleBinding
import com.uhi5d.statibud.databinding.SearchArtistSingleBinding
import com.uhi5d.statibud.databinding.SearchResultsHeaderBinding
import com.uhi5d.statibud.domain.model.searchresults.*
import com.uhi5d.statibud.util.BaseViewHolder
import com.uhi5d.statibud.util.loadWithPicasso

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



    private var searchResultsTracks = listOf<SearchResultsTracksItem>()
    fun setSearchResultsTracks(searchResultsTracks: SearchResultsTracks){
        this.searchResultsTracks = searchResultsTracks.items!!
        notifyDataSetChanged()
    }
    private var searchResultsAlbums = listOf<SearchResultsAlbumsItem>()
    fun setSearchResultsAlbums(searchResultsAlbums: SearchResultsAlbums){
        this.searchResultsAlbums = searchResultsAlbums.items!!
        notifyDataSetChanged()
    }
    private var searchResultsArtists = listOf<SearchResultsArtistsItem>()
    fun setSearchResultsArtists(searchResultsArtists: SearchResultsArtists){
        this.searchResultsArtists = searchResultsArtists.items!!
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
                    searchResultsTracks.size + 1 -> holder.bind("Artists")
                    searchResultsTracks.size + searchResultsArtists.size + 2 -> holder.bind("Albums")
                }
            }
            is TrackViewHolder -> holder.bind(searchResultsTracks[position-1])
            is ArtistViewHolder -> holder.bind(searchResultsArtists[position-(searchResultsTracks.size+2)])
            is AlbumViewHolder -> holder.bind(searchResultsAlbums[position-(searchResultsArtists.size+searchResultsTracks.size+3)])
        }
    }

    override fun getItemCount(): Int =
        searchResultsAlbums.size+searchResultsTracks.size+searchResultsArtists.size+3

    override fun getItemViewType(position: Int): Int {
        return if (position == 0){
            TrackHeaderPos
        }else if (position < searchResultsTracks.size + 1){
            TrackBodyPos
        }else if(position == searchResultsTracks.size +1){
            ArtistHeaderPos
        }else if (position > searchResultsTracks.size + 1 && position < searchResultsTracks.size + searchResultsArtists.size + 2){
            ArtistBodyPos
        }else if (position == searchResultsTracks.size + searchResultsArtists.size + 2){
            AlbumHeaderPos
        }else{
            AlbumBodyPos
        }
    }

    inner class TrackViewHolder(
        private val binding: SearchAlbumtrackSingleBinding
    ): BaseViewHolder<SearchResultsTracksItem>(binding.root){
        override fun bind(item: SearchResultsTracksItem) {
            with(binding){
                if (item.album!!.images!!.isNotEmpty()) {
                    ivImage.loadWithPicasso(item.album.images!![0].url.toString())
                }

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
                if (item.images!!.isNotEmpty()) {
                    ivImage.loadWithPicasso(item.images[0].url.toString())
                }

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
            with(binding) {
                if (item.images!!.isNotEmpty()) {
                    ivImage.loadWithPicasso(item.images[0].url.toString())
                }

                tvName.text = item.name

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
            binding.tv.text = item

            binding.root.setOnClickListener {
                itemClickListener.onHeaderItemClicked(binding.tv.text.toString())
            }
        }
    }

}