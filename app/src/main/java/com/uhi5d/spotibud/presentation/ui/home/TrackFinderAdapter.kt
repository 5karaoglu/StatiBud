package com.uhi5d.spotibud.presentation.ui.home
/*


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.R

class TrackFinderAdapter(
    var context: Context,
    var dataList: List<TrackFinderTracks>,
    var clickListener : OnItemClickListener
) : RecyclerView.Adapter<TrackFinderAdapter.TfViewHolder>() {


    class TfViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivImage = itemView.findViewById<ImageView>(R.id.ivRecommendation)
        var tvName = itemView.findViewById<TextView>(R.id.tvRecommendation)
        var tvArtistName = itemView.findViewById<TextView>(R.id.tvRecommendationArtist)


        fun bind(tracks: TrackFinderTracks, clickListener: OnItemClickListener){
            Picasso.get()
                .load(tracks.albumImage)
                .fit().centerCrop()
                .into(ivImage)
            tvName.text = tracks.trackName
            tvArtistName.text = tracks.artistName

            itemView.setOnClickListener {
                clickListener.onItemClicked(tracks)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TfViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.recommendation_single_item,parent,false)
        return TfViewHolder(view)
    }


    override fun onBindViewHolder(holder: TfViewHolder, position: Int) {
        var track= dataList[position]
        holder.bind(track,clickListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    interface OnItemClickListener{
        fun onItemClicked(tracks: TrackFinderTracks)
    }

}*/
