package com.example.contestifyfirsttry.Search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.QueryResultTrackItem
import com.example.contestifyfirsttry.model.QueryResultTracks
import com.example.contestifyfirsttry.model.SearchHistory
import com.squareup.picasso.Picasso

class SearchHistoryAdapter (
    var context: Context,
    var dataList: List<SearchHistory>,
    var clickListener : OnItemClickListener) : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryHolder>() {


    class SearchHistoryHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
        var tvName = itemView.findViewById<TextView>(R.id.tvName)
        var tvArtistName = itemView.findViewById<TextView>(R.id.tvNameArtist)


        fun bind(searchHistory: SearchHistory, clickListener: OnItemClickListener){
            Picasso.get()
                .load(searchHistory.aImage)
                .fit().centerCrop()
                .into(ivImage)
            tvName.text = searchHistory.name


            itemView.setOnClickListener {
                clickListener.onItemClicked(searchHistory)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.search_albumtrack_single,parent,false)
        return SearchHistoryHolder(view)
    }


    override fun onBindViewHolder(holder: SearchHistoryHolder, position: Int) {
        var resultHistory= dataList[position]
        holder.bind(resultHistory,clickListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    interface OnItemClickListener{
        fun onItemClicked(searchHistory: SearchHistory)
    }

}