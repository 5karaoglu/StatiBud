package com.example.contestifyfirsttry.recent

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.Functions
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment(),
    QueryResultArtistAdapter.OnItemClickListener,
    QueryResultTrackAdapter.OnItemClickListener,
    QueryResultAlbumAdapter.OnItemClickListener {
    private val TAG = "Search Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //disabling onbackpressed
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
        callback.isEnabled = true
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        // ViewModel components
        var factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)


        viewmodel.queryResults.observe(viewLifecycleOwner,
        Observer { t -> setResults(t!!) })
        init(token!!)

    }
    private fun init(token:String){
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    viewmodel.getQueryResult(token, Functions().stringToQuery(s.toString()))
                }
            }
        })
    }

    private fun setResults(queryResults: QueryResults){
        //recycler adapter for artists
        val artistsAdapter = QueryResultArtistAdapter(requireContext(),queryResults.artists,this)
        val artistLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        recyclerSearchArtists.layoutManager = artistLayoutManager
        recyclerSearchArtists.adapter = artistsAdapter
        //recycler adapter for tracks
        val tracksAdapter = QueryResultTrackAdapter(requireContext(),queryResults.tracks,this)
        val tracksLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerSearchTracks.layoutManager = tracksLayoutManager
        recyclerSearchTracks.adapter = tracksAdapter
        //recycler adapter for albums
        val albumsAdapter = QueryResultAlbumAdapter(requireContext(),queryResults.albums,this)
        val albumsLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerSearchAlbums.layoutManager = albumsLayoutManager
        recyclerSearchAlbums.adapter = albumsAdapter
    }

    override fun onItemClicked(currentArtist: QueryResultArtistsItem) {
        val bundle = Bundle()
        bundle.putString("id",currentArtist.id)
        bundle.putString("name",currentArtist.name)
        bundle.putString("image",currentArtist.images[0].url)
        findNavController().navigate(R.id.action_searchFragment_to_itemDetailedFragment,bundle)
    }

    override fun onItemClicked(currentTrack: QueryResultTrackItem) {
        val bundle = Bundle()
        bundle.putString("id",currentTrack.id)
        bundle.putString("name",currentTrack.name)
        bundle.putString("image",currentTrack.album.images[0].url)
        findNavController().navigate(R.id.action_searchFragment_to_detailedTrackFragment,bundle)
    }

    override fun onItemClicked(currentAlbum: QueryResultAlbumItem) {
        val bundle = Bundle()
        bundle.putString("id",currentAlbum.id)
        bundle.putString("name",currentAlbum.name)
        bundle.putString("image",currentAlbum.images[0].url)
        findNavController().navigate(R.id.action_searchFragment_to_detailedAlbumFragment,bundle)
    }


}