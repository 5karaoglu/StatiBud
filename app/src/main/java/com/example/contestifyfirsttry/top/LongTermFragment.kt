package com.example.contestifyfirsttry.top

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
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.TrackItems
import com.example.contestifyfirsttry.Tracks
import com.example.contestifyfirsttry.main.MainViewModel
import com.example.contestifyfirsttry.model.Artists
import com.example.contestifyfirsttry.model.Item
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_long_term.*


class LongTermFragment : Fragment(),
    TracksAdapter.OnItemClickListener,
    ArtistsAdapter.OnItemClickListener{
    private var TAG = "LongTerm Fragment"
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_long_term, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        // ViewModel components
        var factory = CustomViewModelFactory(this,requireContext())
        viewModel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewModel!!.artistsListLongTerm.observe(viewLifecycleOwner,
            Observer<Artists> { t ->
                Log.d(TAG, "onViewCreated: long sup")
                generateDataArtists (t!!)
            })

        viewModel.getMyArtists(token!!,"long_term")
        viewModel.getMyTracks(token!!,"long_term")
        initRadioGroup()
    }
    private fun initRadioGroup() {
        radioGroupLongTerm.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                radioButton1LongTerm.id -> generateDataArtists(viewModel.artistsListLongTerm.value!!)
                radioButton2LongTerm.id -> generateDataTracks(viewModel.tracksListLongTerm.value!!)
            }
        }
    }
    private fun generateDataTracks(tracks: Tracks){
        var adapter : TracksAdapter = TracksAdapter(requireContext(),tracks,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerLongTerm.layoutManager = layoutManager
        recyclerLongTerm.adapter = adapter
    }
    private fun generateDataArtists(artists: Artists){
        var adapter : ArtistsAdapter = ArtistsAdapter(requireContext(),artists,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerLongTerm.layoutManager = layoutManager
        recyclerLongTerm.adapter = adapter
    }
    //tracks onclick
    override fun onItemClicked(track: TrackItems) {
        val bundle = Bundle()
        bundle.putString("name",track.name)
        bundle.putString("id",track.id)
        bundle.putString("image",track.album.images[0].url)
        bundle.putString("artistId",track.artists[0].id)
        findNavController().navigate(R.id.action_topFragment_to_detailedTrackFragment,bundle)
    }
    //artists onclick
    override fun onItemClicked(artist: Item) {
        val bundle = Bundle()
        bundle.putString("name",artist.name)
        bundle.putString("id",artist.id)
        bundle.putString("image",artist.images[0].url)
        findNavController().navigate(R.id.action_topFragment_to_itemDetailedFragment,bundle)
    }

}