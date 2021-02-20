package com.example.contestifyfirsttry.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.Functions
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.*
import com.squareup.picasso.Picasso

class QueryResultArtistAdapter(
    var context: Context,
    var dataList: QueryResultArtists,
    var clickListener : OnItemClickListener,
    var isDetailed : Boolean) : RecyclerView.Adapter<QueryResultArtistAdapter.QueryResultArtistHolder>() {


    class QueryResultArtistHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivArtistImage = itemView.findViewById<ImageView>(R.id.ivArtistImage)
        var tvArtistName = itemView.findViewById<TextView>(R.id.tvArtistImage)


        fun bind(currentArtist: QueryResultArtistsItem, clickListener: OnItemClickListener){
            if (currentArtist.images.isNotEmpty()){
                Picasso.get()
                    .load(currentArtist.images[1].url)
                    .fit().centerCrop()
                    .into(ivArtistImage)
            }else{
                Picasso.get()
                    .load(R.drawable.ic_close_gray)
                    .into(ivArtistImage)
            }

            tvArtistName.text = currentArtist.name

            itemView.setOnClickListener {
                clickListener.onItemClicked(currentArtist)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryResultArtistHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(Functions().selectArtistLayout(isDetailed),parent,false)
        return QueryResultArtistHolder(view)
    }


    override fun onBindViewHolder(holder: QueryResultArtistHolder, position: Int) {
        var resultArtist = dataList.items[position]
        holder.bind(resultArtist,clickListener)
    }

    override fun getItemCount(): Int {
        return dataList.items.size
    }
    interface OnItemClickListener{
        fun onItemClicked(currentArtist: QueryResultArtistsItem)
    }

}