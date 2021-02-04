package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.TrackItems
import com.example.contestifyfirsttry.model.RelatedArtist
import com.example.contestifyfirsttry.model.RelatedArtists
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_detailed_related_artists.*


class DetailedRelatedArtistsFragment : Fragment(), RelatedArtistsAdapter.OnItemClickListener {
    private val TAG = "DetailedRelatedArtist Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_related_artists, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")
        val id = sharedPreferences.getString("id","")


        // ViewModel components
        var factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)
        viewmodel!!.relatedArtists.observe(viewLifecycleOwner,
            Observer<RelatedArtists> { t -> generateData(t!!) })
        viewmodel.getRelatedArtists(token!!,id!!)
    }
    private fun generateData(relatedArtists:RelatedArtists){
        var adapter : RelatedArtistsAdapter = RelatedArtistsAdapter(requireContext(),relatedArtists,this)
        var gridLayoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        recyclerRelatedArtists.layoutManager = gridLayoutManager
        recyclerRelatedArtists.adapter = adapter
    }

    override fun onItemClicked(relatedArtist: RelatedArtist) {
        val bundle = Bundle()
        bundle.putString("name",relatedArtist.name)
        bundle.putString("id",relatedArtist.id)
        bundle.putString("image",relatedArtist.images[0].url)
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",relatedArtist.id)
        editor.apply()
        findNavController().navigate(R.id.action_itemDetailedFragment_to_itemDetailedFragment,bundle)
    }


}