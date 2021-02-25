package com.uhi5d.spotibud.top

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.uhi5d.spotibud.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_long_term.*


class LongTermFragment : Fragment(),
    TracksAdapter.OnItemClickListener,
    ArtistsAdapter.OnItemClickListener{
    private var TAG = "LongTerm Fragment"
    private lateinit var viewModel: MainViewModel
    private var token : String? = null
    //variable for handle radiogroup lifecycle behaviour
    private var checkedRadio : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_long_term, container, false)
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
        radioButton1LongTerm.isSelected=true
        viewModel.artistsListLongTerm.observe(viewLifecycleOwner,
            { t ->
                Log.d(TAG, "onViewCreated: LongTerm Artists")
                if (checkedRadio == 1){
                    generateDataArtists(t!!)
                }
            })
        viewModel.tracksListLongTerm.observe(viewLifecycleOwner,
            { t ->
                Log.d(TAG, "onViewCreated: LongTerm Tracks")
                if (checkedRadio == 2){
                    generateDataTracks(t!!)
                }
            })

        Thread{
            viewModel.getMyArtists(token!!,"long_term")
        }.start()
        Thread{
            viewModel.getMyTracks(token!!,"long_term")
        }.start()
    }
    private fun initRadioGroup() {
        radioGroupLongTerm.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                radioButton1LongTerm.id -> {
                    checkedRadio = 1
                    generateDataArtists(viewModel.artistsListLongTerm.value!!)
                }
                radioButton2LongTerm.id -> {
                    checkedRadio = 2
                    generateDataTracks(viewModel.tracksListLongTerm.value!!)
                }
            }
        }
    }
    private fun generateDataTracks(tracks: Tracks){
        val adapter = TracksAdapter(requireContext(),tracks,this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerLongTerm.layoutManager = layoutManager
        recyclerLongTerm.adapter = adapter
    }
    private fun generateDataArtists(artists: Artists){
        val adapter = ArtistsAdapter(requireContext(),artists,this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
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