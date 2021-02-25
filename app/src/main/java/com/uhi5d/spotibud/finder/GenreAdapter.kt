package com.uhi5d.spotibud.finder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R

class GenreAdapter (
    var context: Context,
    var dataList: List<String>,
    var clickListener : OnItemClickListener) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {


    class GenreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.findViewById<TextView>(R.id.tvGenreText)

        fun bind(genre: String, clickListener: OnItemClickListener){

            tvName.text = genre

            itemView.setOnClickListener {
                clickListener.onItemClicked(genre)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.tf_genre_single,parent,false)
        return GenreViewHolder(view)
    }


    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = dataList[position]
        holder.bind(genre,clickListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    interface OnItemClickListener{
        fun onItemClicked(genre: String)
    }

}