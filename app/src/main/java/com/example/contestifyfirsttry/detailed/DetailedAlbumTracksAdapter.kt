package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.AlbumTrackItem
import com.example.contestifyfirsttry.model.AlbumTracks


class DetailedAlbumTracksAdapter (
    var context: Context,
    var dataList: AlbumTracks,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DetailedAlbumTracksAdapter.AlbumTracksViewHolder>() {


    class AlbumTracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.ivIsExplicit)
        var textViewName = itemView.findViewById<TextView>(R.id.tvTrackName)
        var textViewCount = itemView.findViewById<TextView>(R.id.tvTrackCount)

        fun bind(track: AlbumTrackItem, position: Int, clickListener:OnItemClickListener) {

            textViewName.text = track.name
            textViewCount.text = String.format("#%d",position+1)
            if (track.explicit){
                imageView.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                clickListener.onItemClicked(track)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTracksViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.detailed_album_track_single_item, parent, false)

        return AlbumTracksViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AlbumTracksViewHolder,
        position: Int
    ) {
        var track = dataList.items[position]
        holder.bind(track, position,itemClickListener)
    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }

    interface OnItemClickListener {
        fun onItemClicked(track: AlbumTrackItem)

    }


}