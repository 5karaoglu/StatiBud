package com.uhi5d.spotibud.detailed

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.main.MainViewModel
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.model.*
import com.uhi5d.spotibud.util.CustomViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detailed_artist.*
import kotlinx.android.synthetic.main.fragment_detailed_artist.imageView

class DetailedArtistFragment : Fragment(),
    ArtistAlbumsAdapter.OnItemClickListener,
    ArtistTopTracksAdapter.OnItemClickListener,
    RelatedArtistsAdapter.OnItemClickListener{
    private val TAG = "Detailed Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_artist, container, false)
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
        val name = bundle.get("name") as String
        val id = bundle.get("id") as String
        if (bundle.get("image") != null ){
            var image : String? = null
            image =   bundle.get("image") as String
            init(id, name)
            initImage(image)
        }else{
            var image : Int? = null
            image =   R.drawable.ic_close_gray
            init(id, name)
            initImage(image)
        }
        // ViewModel components
        var factory = CustomViewModelFactory(this,requireContext())
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)
        viewmodel!!.artist.observe(viewLifecycleOwner,
            Observer<Item> { t -> /*generateRelatedArtists(t!!)*/ })
        viewmodel!!.artistTopTracks.observe(viewLifecycleOwner,
            Observer<ArtistTopTracks> { t ->
                generateTopTracks(t!!)
                doVisibility()})
        viewmodel!!.artistAlbums.observe(viewLifecycleOwner,
            Observer<ArtistAlbums> { t -> generateAlbums(t!!) })
        viewmodel!!.relatedArtists.observe(viewLifecycleOwner,
            Observer<RelatedArtists> { t -> generateRelatedArtists(t!!) })

        viewmodel.getArtist(token!!,id!!)
        viewmodel.getArtistTopTracks(token, id!!)
        viewmodel.getArtistAlbums(token, id!!)
        viewmodel.getRelatedArtists(token!!,id!!)
    }
    private fun initImage(image: String){
        Picasso.get()
        .load(image)
        .fit().centerCrop()
        .into(imageView)
    }
    //in case image is empty
    private fun initImage(image: Int){
        Picasso.get()
            .load(image)
            .fit().centerCrop()
            .into(imageView)
    }
    private fun init(id:String, name:String){
        tvParallaxHeaderArtist.text = name

        detailedToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //collapsing toolbar design section
        var isShow = true
        var scrollRange = -1
        appBarDetailedArtist.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
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

        fabDetailedArtist.setOnClickListener {
            try {
                val uri = Uri.parse("http://open.spotify.com/artist/${id}")
                val intent = Intent(Intent.ACTION_VIEW,uri)
                startActivity(intent)
            }catch (ex: ActivityNotFoundException){
                Toast.makeText(requireContext(), "Error: ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun doVisibility(){
        nsvDetailedArtist.visibility = View.VISIBLE
        fabDetailedArtist.visibility = View.VISIBLE
        pbDetailedArtist.visibility = View.GONE
    }

    private fun generateTopTracks(tracks: ArtistTopTracks){
        var adapter : ArtistTopTracksAdapter = ArtistTopTracksAdapter(requireContext(),tracks,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerTopTracks.layoutManager = layoutManager
        recyclerTopTracks.adapter = adapter
    }
    private fun generateAlbums(albums: ArtistAlbums){
        var adapter : ArtistAlbumsAdapter = ArtistAlbumsAdapter(requireContext(),albums,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        recyclerAlbums.layoutManager = layoutManager
        recyclerAlbums.adapter = adapter
    }
    private fun generateRelatedArtists(relatedArtists:RelatedArtists){
        var adapter : RelatedArtistsAdapter = RelatedArtistsAdapter(requireContext(),relatedArtists,this)
        var gridLayoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL,false)
        recyclerRelatedArtists.layoutManager = gridLayoutManager
        recyclerRelatedArtists.adapter = adapter
    }

    override fun onItemClicked(album: ArtistAlbumsItems) {
        val bundle = Bundle()
        bundle.putString("name",album.name)
        bundle.putString("id",album.id)
        bundle.putString("image",album.images[0].url)
        findNavController().navigate(R.id.action_itemDetailedFragment_to_detailedAlbumFragment,bundle)
    }

    override fun onItemClicked(track: TracksTopTrack) {
        val bundle = Bundle()
        bundle.putString("name",track.name)
        bundle.putString("id",track.id)
        bundle.putString("image",track.album.images[0].url)
        bundle.putString("artistId",track.artists[0].id)
        findNavController().navigate(R.id.action_itemDetailedFragment_to_detailedTrackFragment,bundle)
    }

    override fun onItemClicked(relatedArtist: RelatedArtist) {
        val bundle = Bundle()
        bundle.putString("name",relatedArtist.name)
        bundle.putString("id",relatedArtist.id)
        bundle.putString("image",relatedArtist.images[0].url)
        findNavController().navigate(R.id.action_itemDetailedFragment_self,bundle)
    }

}