package com.uhi5d.spotibud.top

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.TrackItems
import com.uhi5d.spotibud.Tracks
import com.uhi5d.spotibud.main.MainViewModel
import com.uhi5d.spotibud.model.Artists
import com.uhi5d.spotibud.model.Item
import com.uhi5d.spotibud.main.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_short_term.*


class ShortTermFragment : Fragment(),
    TracksAdapter.OnItemClickListener,
    ArtistsAdapter.OnItemClickListener{
    private var TAG = "ShortTerm Fragment"
    private lateinit var viewModel: MainViewModel
    private var token : String? = null
    //variable for handle radiogroup lifecycle behaviour
    private var checkedRadio : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_short_term, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       Thread{
           //getting token
           val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
           token = sharedPreferences.getString("token","")
       }.start()

        // ViewModel components
        val factory = CustomViewModelFactory(this,requireContext())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        initRadioGroup()
    }

    override fun onStart() {
        super.onStart()
        radioButton1ShortTerm.isSelected=true
        viewModel.artistsListShortTerm.observe(viewLifecycleOwner,
           { t ->
                Log.d(TAG, "onViewCreated: Short term artists")
                if (checkedRadio==1){
                    generateDataArtists(t!!)
                }
           })
        viewModel.tracksListShortTerm.observe(viewLifecycleOwner,
            { t ->
                Log.d(TAG, "onViewCreated: Short term tracks")
                if (checkedRadio==2){
                    generateDataTracks(t!!)
                }
            })

        Thread{
            viewModel.getMyArtists(requireContext(),token!!,"short_term")
        }.start()
        Thread{
            viewModel.getMyTracks(requireContext(),token!!,"short_term")
        }.start()
    }
    private fun initRadioGroup() {
        radioGroupShortTerm.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                radioButton1ShortTerm.id -> {
                    checkedRadio = 1
                    generateDataArtists(viewModel.artistsListShortTerm.value!!)

                }
                radioButton2ShortTerm.id -> {
                    checkedRadio = 2
                    generateDataTracks(viewModel.tracksListShortTerm.value!!)

                }
            }
        }
    }
    private fun generateDataTracks(tracks: Tracks){
        val adapter = TracksAdapter(requireContext(),tracks,this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerShortTerm.layoutManager = layoutManager
        recyclerShortTerm.adapter = adapter
    }
    private fun generateDataArtists(artists: Artists){
        val adapter = ArtistsAdapter(requireContext(),artists,this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerShortTerm.layoutManager = layoutManager
        recyclerShortTerm.adapter = adapter
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