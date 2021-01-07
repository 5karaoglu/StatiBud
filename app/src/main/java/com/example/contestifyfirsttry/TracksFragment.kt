package com.example.contestifyfirsttry

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

    companion object {
        fun newInstance() = TracksFragment()
    }

    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tracks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var bundle = activity!!.intent.extras
        var token = bundle!!.getString("token")

        // ViewModel components
        var factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.tracksList.observe(this,
            Observer<Tracks> { t -> generateDataTracks(t!!) })
        viewmodel.getMyTracks(token!!)
    }
    private fun generateDataTracks(tracks: Tracks){
        var adapter : TracksAdapter = TracksAdapter(requireContext(),tracks)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerTracks.layoutManager = layoutManager
        recyclerTracks.adapter = adapter
    }



}