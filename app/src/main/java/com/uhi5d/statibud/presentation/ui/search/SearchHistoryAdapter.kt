package com.uhi5d.statibud.presentation.ui.search

/*
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.R


class SearchHistoryAdapter (
    var context: Context,
    var dataList: List<SearchHistory>,
    var clickListener : OnItemClickListener
) : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryHolder>() {


    class SearchHistoryHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivImage = itemView.findViewById<ImageView>(R.id.ivSearchHistoryImage)
        var ivArtistImage = itemView.findViewById<ImageView>(R.id.ivSearchHistoryArtistImage)
        var liArtist = itemView.findViewById<LinearLayout>(R.id.liArtist)
        var liAlbum = itemView.findViewById<LinearLayout>(R.id.liAlbum)
        var ivImageDelete = itemView.findViewById<ImageView>(R.id.ivSearchHistoryDelete)
        var tvName = itemView.findViewById<TextView>(R.id.tvSearchHistoryName)
        var tvNameArtist = itemView.findViewById<TextView>(R.id.tvSearchHistoryNameArtist)
        var tvArtistName = itemView.findViewById<TextView>(R.id.tvSearchHistoryArtistName)


        fun bind(searchHistory: SearchHistory, clickListener: OnItemClickListener){
            Log.d("debug", "bind: ${searchHistory.name}")
            Log.d("debug", "bind: ${searchHistory.artistName}")
            Log.d("debug", "bind: ${searchHistory.type}")
            if (searchHistory.type == "artist"){
                ivArtistImage.visibility = View.VISIBLE
                liArtist.visibility = View.VISIBLE
                liAlbum.visibility = View.GONE
                ivImage.visibility = View.GONE
                Picasso.get()
                    .load(searchHistory.cImage)
                    .fit().centerCrop()
                    .into(ivArtistImage)
                tvNameArtist.text = searchHistory.name
            }else{
                ivArtistImage.visibility = View.GONE
                liArtist.visibility = View.GONE
                liAlbum.visibility = View.VISIBLE
                ivImage.visibility = View.VISIBLE
                Picasso.get()
                    .load(searchHistory.cImage)
                    .fit().centerCrop()
                    .into(ivImage)
                tvName.text = searchHistory.name
                tvArtistName.text = searchHistory.artistName
            }



            ivImageDelete.setOnClickListener {
                clickListener.onDeleteClicked(searchHistory)
            }

            itemView.setOnClickListener {
                clickListener.onItemClicked(searchHistory)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.search_history_albumtrack,parent,false)
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
        fun onDeleteClicked(searchHistory: SearchHistory)
    }

}*/
