package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.TrackItems
import com.example.contestifyfirsttry.model.AlbumTrackItem
import com.example.contestifyfirsttry.model.AlbumTracks
import com.example.contestifyfirsttry.model.Item
import com.example.contestifyfirsttry.model.TrackAudioFeatures
import com.example.contestifyfirsttry.top.ArtistsAdapter
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.artists_fragment.*

class DetailedAlbumFragment : Fragment(), DetailedAlbumTracksAdapter.OnItemClickListener {
    private val TAG = "DetailedAlbum Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_album, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")
        val id = sharedPreferences.getString("id","")
        val artistId = sharedPreferences.getString("artistId","")
        val image = sharedPreferences.getString("image","")

        // ViewModel components
        var factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.albumTracks.observe(viewLifecycleOwner,
            Observer<AlbumTracks> { t -> generateAlbumTracks(t!!) })

        viewmodel.getAlbumTracks(token!!,id!!)

    }
    private fun generateAlbumTracks(albumTracks: AlbumTracks){
        var adapter : DetailedAlbumTracksAdapter = DetailedAlbumTracksAdapter(requireContext(),albumTracks,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerArtists.layoutManager = layoutManager
        recyclerArtists.adapter = adapter
    }

    override fun onItemClicked(track: AlbumTrackItem) {
        TODO("Not yet implemented")
    }
}