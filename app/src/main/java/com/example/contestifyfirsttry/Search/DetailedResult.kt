package com.example.contestifyfirsttry.Search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.*
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_detailed_result.*
import kotlinx.android.synthetic.main.fragment_search.*


class DetailedResult : Fragment(),
    QueryResultArtistAdapter.OnItemClickListener,
    QueryResultTrackAdapter.OnItemClickListener,
    QueryResultAlbumAdapter.OnItemClickListener {
    private val TAG = "DetailedResult Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_result, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //disabling onbackpressed
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
        callback.isEnabled = true
        //getting currentId
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        val bundle = requireArguments()
        val type = bundle.get("type") as String
        val q = bundle.get("q") as String
        //setting header
        detailedResultHeader.text = getString(R.string.detailed_search_header,q,typeToString(type))

        // ViewModel components
        var factory = CustomViewModelFactory(this,requireContext())
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)
        viewmodel!!.queryResults.observe(viewLifecycleOwner,
            { t -> generateData(t!!,type!!) })
        viewmodel.getQueryResultDefined(token!!,type!!, q)

        init()

    }
    private fun generateData(queryResults:QueryResults, type:String){

        when(type){
            "artist" ->{
                val adapter = QueryResultArtistAdapter(requireContext(),queryResults.artists,this,true)
                val layoutManager = LinearLayoutManager(requireContext())
                recyclerDetailedResult.layoutManager = layoutManager
                recyclerDetailedResult.adapter = adapter
            }
            "track" ->{
                val adapter = QueryResultTrackAdapter(requireContext(),queryResults.tracks,this)
                val layoutManager = LinearLayoutManager(requireContext())
                recyclerDetailedResult.layoutManager = layoutManager
                recyclerDetailedResult.adapter = adapter
            }
            "album" ->{
                val adapter = QueryResultAlbumAdapter(requireContext(),queryResults.albums,this)
                val layoutManager = LinearLayoutManager(requireContext())
                recyclerDetailedResult.layoutManager = layoutManager
                recyclerDetailedResult.adapter = adapter
            }
        }
    }
    private fun init(){
        buttonBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun onItemClicked(currentArtist: QueryResultArtistsItem) {
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",currentArtist.id)
        editor.apply()
        val bundle = Bundle()
        bundle.putString("id",currentArtist.id)
        bundle.putString("name",currentArtist.name)
        bundle.putString("image",currentArtist.images[0].url)
        findNavController().navigate(R.id.action_detailedResult_to_itemDetailedFragment,bundle)
    }

    override fun onItemClicked(currentTrack: QueryResultTrackItem) {
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",currentTrack.id)
        editor.putString("artistId",currentTrack.artists[0].id)
        editor.apply()
        val bundle = Bundle()
        bundle.putString("id",currentTrack.id)
        bundle.putString("name",currentTrack.name)
        bundle.putString("image",currentTrack.album.images[0].url)
        findNavController().navigate(R.id.action_detailedResult_to_detailedTrackFragment,bundle)
    }

    override fun onItemClicked(currentAlbum: QueryResultAlbumItem) {
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",currentAlbum.id)
        editor.putString("artistId",currentAlbum.artists[0].id)
        editor.apply()
        val bundle = Bundle()
        bundle.putString("id",currentAlbum.id)
        bundle.putString("name",currentAlbum.name)
        bundle.putString("image",currentAlbum.images[0].url)
        findNavController().navigate(R.id.action_detailedResult_to_detailedAlbumFragment,bundle)
    }
    private fun typeToString(type:String): String {
        var string : String? = null
        when(type){
            "artist" -> string = getString(R.string.artist_header)
            "track" -> string = getString(R.string.track_header)
            "album" -> string = getString(R.string.album_header)
        }
        return string!!
    }



}