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
import kotlinx.android.synthetic.main.artists_fragment.*

class ArtistsFragment : Fragment() {

    companion object {
        fun newInstance() = ArtistsFragment()
    }

    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.artists_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var bundle = activity!!.intent.extras
        var token = bundle!!.getString("token")

        // ViewModel components
        var factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.artistsList.observe(this,
            Observer<Artists> { t -> generateDataArtists(t!!) })
        viewmodel.getMyArtists(token!!)
    }


    private fun generateDataArtists(artists: Artists){
        var adapter : ArtistsAdapter = ArtistsAdapter(requireContext(),artists)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerArtists.layoutManager = layoutManager
        recyclerArtists.adapter = adapter
    }

}