package com.example.contestifyfirsttry.detailed

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.*
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detailed_album.*
import kotlinx.android.synthetic.main.fragment_detailed_album.collapsingToolbarArtist
import kotlinx.android.synthetic.main.fragment_detailed_album.imageView

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //disabling onbackpressed
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
        callback.isEnabled = true
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        val bundle = requireArguments()
        val name = bundle.get("name") as String
        val id = bundle.get("id") as String
        val image = bundle.get("image") as String
        albumCover = image
        init(id,name,image)

        // ViewModel components
        var factory = CustomViewModelFactory(this,requireContext())
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.album.observe(viewLifecycleOwner,
            Observer<Album> { t ->
                generateAlbumTracks(t!!)
                doVisibility()})
        viewmodel.getAlbum(token!!,id!!)
    }
    private fun doVisibility(){
        nsvDetailedAlbum.visibility = View.VISIBLE
        fabDetailedAlbum.visibility = View.VISIBLE
        pbDetailedAlbum.visibility = View.GONE
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
        bundle.putString("artistId",track.artists[0].id)
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",track.id)
        editor.apply()
        findNavController().navigate(R.id.action_detailedAlbumFragment_to_detailedTrackFragment,bundle)
    }
    private fun init(id:String, name:String, image:String){
        Picasso.get()
            .load(image)
            .fit().centerCrop()
            .into(imageView)

        tvParallaxHeaderAlbum.text = name

        detailedAlbumToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //collapsing toolbar design section
        var isShow = true
        var scrollRange = -1
        appBarDetailedAlbum.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                appBarLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = appBarLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                collapsingToolbarArtist.title = name
                isShow = true
            } else if (isShow){
                collapsingToolbarArtist.title = " "
                isShow = false
            }})
        /////////////////////////////////////////////////////////////////////////////////////
        detailedAlbumToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        fabDetailedAlbum.setOnClickListener {
            try {

                var uri = Uri.parse("http://open.spotify.com/album/${id}")
                var intent = Intent(Intent.ACTION_VIEW,uri)
                startActivity(intent)
            }catch (ex: ActivityNotFoundException){
                Log.d(TAG, "setTrack: ${ex.message}")
            }
        }
    }
}