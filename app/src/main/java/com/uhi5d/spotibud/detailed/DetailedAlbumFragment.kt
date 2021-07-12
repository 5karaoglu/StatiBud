package com.uhi5d.spotibud.detailed

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.main.MainViewModel
import com.uhi5d.spotibud.model.*
import kotlinx.android.synthetic.main.fragment_detailed_album.*

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
        val factory = CustomViewModelFactory(this,requireContext())
        viewmodel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewmodel.album.observe(viewLifecycleOwner,
             { t ->
                generateAlbumTracks(t!!)
                doVisibility()})
        viewmodel.getAlbum(requireContext(),token!!, id)
    }
    private fun doVisibility(){
        nsvDetailedAlbum.visibility = View.VISIBLE
        fabDetailedAlbum.visibility = View.VISIBLE
        pbDetailedAlbum.visibility = View.GONE
    }

    private fun generateAlbumTracks(album: _root_ide_package_.com.uhi5d.spotibud.domain.model.Album) {
        val adapter = DetailedAlbumTracksAdapter(requireContext(), album, this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerAlbumTracks.layoutManager = layoutManager
        recyclerAlbumTracks.adapter = adapter
        setCopyright(album)
    }

    private fun setCopyright(album: _root_ide_package_.com.uhi5d.spotibud.domain.model.Album) {
        tvCopyright.text = album.copyrights[0].text
        tvLabel.text = album.label
        tvRelease.text = String.format("Released: %s", album.release_date)
    }

    override fun onItemClicked(track: _root_ide_package_.com.uhi5d.spotibud.domain.model.AlbumItems) {
        val bundle = Bundle()
        bundle.putString("name", track.name)
        bundle.putString("image", albumCover)
        bundle.putString("id", track.id)
        bundle.putString("artistId", track.artists[0].id)
        val sharedPreferences =
            requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", track.id)
        editor.apply()
        findNavController().navigate(
            R.id.action_detailedAlbumFragment_to_detailedTrackFragment,
            bundle
        )
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

                val uri = Uri.parse("http://open.spotify.com/album/${id}")
                val intent = Intent(Intent.ACTION_VIEW,uri)
                startActivity(intent)
            }catch (ex: ActivityNotFoundException){
                Log.d(TAG, "setTrack: ${ex.message}")
            }
        }
    }
}