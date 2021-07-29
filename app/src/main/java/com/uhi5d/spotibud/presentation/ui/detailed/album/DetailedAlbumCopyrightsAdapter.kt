package com.uhi5d.spotibud.presentation.ui.detailed.album

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.databinding.CopyrightSingleItemBinding
import com.uhi5d.spotibud.domain.model.album.Copyright
import com.uhi5d.spotibud.util.BaseViewHolder

class DetailedAlbumCopyrightsAdapter (
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Copyright> = listOf()
    fun setList(list: List<Copyright>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CopyrightSingleItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CrViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CrViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class CrViewHolder(
        private val binding: CopyrightSingleItemBinding
    ): BaseViewHolder<Copyright>(binding.root){
        override fun bind(item: Copyright) {
            with(binding){
                tvText.text = item.text
                tvType.text = item.type
            }
        }
    }

}