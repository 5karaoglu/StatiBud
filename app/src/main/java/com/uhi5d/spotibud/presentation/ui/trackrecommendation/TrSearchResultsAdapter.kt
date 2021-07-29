package com.uhi5d.spotibud.presentation.ui.trackrecommendation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.databinding.SearchAlbumtrackSingleBinding
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsTracksItem
import com.uhi5d.spotibud.util.BaseViewHolder
import com.uhi5d.spotibud.util.loadWithPicasso

class TrSearchResultsAdapter(
    var context: Context,
    var clickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(searchResultsTracksItem: SearchResultsTracksItem)
    }

    private var list = listOf<SearchResultsTracksItem>()
    fun setList(list: List<SearchResultsTracksItem>) {
        this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            SearchAlbumtrackSingleBinding.inflate(LayoutInflater.from(context), parent, false)
        return TrViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TrViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    inner class TrViewHolder(
        val binding: SearchAlbumtrackSingleBinding
    ) : BaseViewHolder<SearchResultsTracksItem>(binding.root) {
        override fun bind(item: SearchResultsTracksItem) {
            with(binding) {
                if (item.album?.images!!.isNotEmpty()) {
                    ivImage.loadWithPicasso(item.album.images[0].url!!)
                }
                tvName.text = item.name
                tvNameArtist.text = item.artists!![0].name

                root.setOnClickListener {
                    clickListener.onItemClicked(item)
                }
            }
        }

    }


}

