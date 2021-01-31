package com.example.contestifyfirsttry

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tracks_fragment.*

class TracksFragment : Fragment() {
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tracks_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRadioGroup()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        // ViewModel components
        var factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.tracksListShortTerm.observe(viewLifecycleOwner,
            Observer<Tracks> { t -> generateDataTracks(t!!) })
        viewmodel.getMyTracks(token!!,"short_term")
        viewmodel.getMyTracks(token!!,"medium_term")
        viewmodel.getMyTracks(token!!,"long_term")
    }
    private fun generateDataTracks(tracks: Tracks){
        var adapter : TracksAdapter = TracksAdapter(requireContext(),tracks,viewmodel)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerTracks.layoutManager = layoutManager
        recyclerTracks.adapter = adapter
    }
    private fun initRadioGroup(){
        radioGroupTracks.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                radioButton1Tracks.id -> generateDataTracks(viewmodel.tracksListShortTerm.value!!)
                radioButton2Tracks.id -> generateDataTracks(viewmodel.tracksListMidTerm.value!!)
                radioButton3Tracks.id -> generateDataTracks(viewmodel.tracksListLongTerm.value!!)
            }
        }
    }



}