package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.TrackItems
import com.example.contestifyfirsttry.model.*
import com.example.contestifyfirsttry.top.ArtistsAdapter
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.artists_fragment.*
import kotlinx.android.synthetic.main.fragment_detailed_album.*
import kotlinx.android.synthetic.main.fragment_detailed_album.imageView
import kotlinx.android.synthetic.main.fragment_item_detailed.*
import kotlinx.android.synthetic.main.tracks_fragment.*

class DetailedAlbumFragment : Fragment(), DetailedAlbumTracksAdapter.OnItemClickListener {
    private val TAG = "DetailedAlbum Fragment"
    private lateinit var viewmodel: MainViewModel
    private var albumCover : String? = null

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

        viewmodel!!.album.observe(viewLifecycleOwner,
            Observer<Album> { t -> generateAlbumTracks(t!!) })

        viewmodel.getAlbum(token!!,id!!)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireArguments()
        val name = bundle.get("name") as String
        val id = bundle.get("id") as String
        val image = bundle.get("image") as String
        albumCover = image
        setData(name,image)
        init()
    }
    private fun init(){
        detailedAlbumToolbar.setNavigationOnClickListener { View.OnClickListener {
            findNavController().navigate(R.id.action_detailedAlbumFragment_to_topFragment)
        } }
    }
    private fun generateAlbumTracks(album: Album){
        var adapter : DetailedAlbumTracksAdapter = DetailedAlbumTracksAdapter(requireContext(),album,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerAlbumTracks.layoutManager = layoutManager
        recyclerAlbumTracks.adapter = adapter
        setCopyright(album)
    }
    private fun setCopyright(album: Album){
        tvCopyright.text = album.copyrights[0].text
        tvLabel.text = album.label
        tvRelease.text = String.format("Released: %s",album.release_date)
    }

    override fun onItemClicked(track: AlbumItems) {
        val bundle = Bundle()
        bundle.putString("name",track.name)
        bundle.putString("image", albumCover)
        bundle.putString("id",track.id)
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",track.id)
        editor.apply()
        findNavController().navigate(R.id.action_detailedAlbumFragment_to_detailedTrackFragment,bundle)
        findNavController().popBackStack()
    }
    fun setData(name:String,image:String){
        Picasso.get()
            .load(image)
            .fit().centerCrop()
            .into(imageView)

        detailedAlbumToolbar.title = name
    }
}