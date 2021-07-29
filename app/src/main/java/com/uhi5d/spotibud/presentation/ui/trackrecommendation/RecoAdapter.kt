package com.uhi5d.spotibud.presentation.ui.trackrecommendation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.databinding.TrResultsSingleItemBinding
import com.uhi5d.spotibud.domain.model.recommendations.RecommendationsTrack
import com.uhi5d.spotibud.util.BaseViewHolder
import com.uhi5d.spotibud.util.loadWithPicasso

class RecoAdapter (
    var context: Context,
    var clickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(item:RecommendationsTrack)
    }

    private var list = listOf<RecommendationsTrack>()
    fun setList(list: List<RecommendationsTrack>) {
        this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            TrResultsSingleItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return RecoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecoViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    inner class RecoViewHolder(
        val binding: TrResultsSingleItemBinding
    ) : BaseViewHolder<RecommendationsTrack>(binding.root) {
        override fun bind(item: RecommendationsTrack) {
            with(binding) {
                if (item.album?.images!!.isNotEmpty()) {
                    iv.loadWithPicasso(item.album.images[0].url!!)
                }
                tvName.text = item.name
                tvArtistName.text = item.artists!![0].name

                root.setOnClickListener {
                    clickListener.onItemClicked(item)
                }
            }
        }

    }


}