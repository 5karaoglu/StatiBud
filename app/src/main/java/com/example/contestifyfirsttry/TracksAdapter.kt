package com.example.contestifyfirsttry

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.squareup.picasso.Picasso

class TracksAdapter(var context: Context, var dataList:Tracks,var viewModel: MainViewModel) : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {


    class TracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageViewTrack)
        var textView = itemView.findViewById<TextView>(R.id.textViewTrack)
        var textViewPop = itemView.findViewById<TextView>(R.id.textViewPopTrack)
        /*var tvAlbumName = itemView.findViewById<TextView>(R.id.tvAlbumName)
        var tvArtist = itemView.findViewById<TextView>(R.id.tvArtist)
        var tvPopularity = itemView.findViewById<TextView>(R.id.tvPopularity)
        var ivExplicit = itemView.findViewById<ImageView>(R.id.ivExplicit)
        var buttonPlaySong = itemView.findViewById<Button>(R.id.buttonPlaySong)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.single_track_layout,parent,false)
        return TracksViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        //ImageView-album photo
        Picasso.get()
            .load(dataList.items[position].album.images[0].url)
            .fit().centerCrop()
            .into(holder.imageView)
        //position
        holder.textViewPop.text = "#${position+1}"
        //track name
        holder.textView.text = dataList.items[position].name
        /*//album name
        if (dataList.items[position].album.album_type == "ALBUM"){
        holder.tvAlbumName.text = dataList.items[position].album.name}
        else{
            holder.tvAlbumName.text = ""
        }
        //artist
        holder.tvArtist.text = dataList.items[position].artists[0].name
        //popularity
        holder.tvPopularity.text = dataList.items[position].popularity.toString()
        //isExplicit
        var isExplicit : Boolean= dataList.items[position].explicit
        if (isExplicit){
            holder.ivExplicit.setImageResource(R.drawable.ic_check)
        }else{
            holder.ivExplicit.setImageResource(R.drawable.ic_close)
        }
        //play song
        holder.buttonPlaySong.setOnClickListener {
            viewModel.playSong(context,dataList.items[position].uri)
        }*/


    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }
}