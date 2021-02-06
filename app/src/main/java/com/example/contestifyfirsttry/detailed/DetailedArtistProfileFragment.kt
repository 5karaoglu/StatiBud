package com.example.contestifyfirsttry.detailed

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.Artists
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.Tracks
import com.example.contestifyfirsttry.model.*
import com.example.contestifyfirsttry.top.ArtistsAdapter
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.artists_fragment.*
import kotlinx.android.synthetic.main.fragment_detailed_artist_profile.*


class DetailedArtistProfileFragment : Fragment(), ArtistAlbumsAdapter.OnItemClickListener {
    private val TAG = "DetailedArtistProfile Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_artist_profile, container, false)
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
        viewmodel!!.artist.observe(viewLifecycleOwner,
            Observer<Item> { t -> generateData(t!!) })
        viewmodel!!.artistTopTracks.observe(viewLifecycleOwner,
            Observer<ArtistTopTracks> { t -> generateTopTracks(t!!) })
        viewmodel!!.artistAlbums.observe(viewLifecycleOwner,
            Observer<ArtistAlbums> { t -> generateAlbums(t!!) })
        viewmodel.getArtist(token!!,id!!)
        viewmodel.getArtistTopTracks(token,id)
        viewmodel.getArtistAlbums(token,id)
    }
    private fun generateData(artist:Item){
        Log.d(TAG, "generateData: ${artist.name} 5")

    }
    private fun generateTopTracks(tracks:ArtistTopTracks){
        var adapter : ArtistTopTracksAdapter = ArtistTopTracksAdapter(requireContext(),tracks)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerTopTracks.layoutManager = layoutManager
        recyclerTopTracks.adapter = adapter
    }
    private fun generateAlbums(albums:ArtistAlbums){
        var adapter : ArtistAlbumsAdapter = ArtistAlbumsAdapter(requireContext(),albums,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        recyclerAlbums.layoutManager = layoutManager
        recyclerAlbums.adapter = adapter
    }

    override fun onItemClicked(album: ArtistAlbumsItems) {
        val bundle = Bundle()
        bundle.putString("name",album.name)
        bundle.putString("id",album.id)
        bundle.putString("image",album.images[0].url)
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",album.id)
        editor.apply()
        findNavController().navigate(R.id.action_itemDetailedFragment_to_detailedAlbumFragment,bundle)
    }


}