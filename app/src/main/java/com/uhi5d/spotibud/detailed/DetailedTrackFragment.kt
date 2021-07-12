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
import com.uhi5d.spotibud.*
import com.uhi5d.spotibud.main.MainViewModel
import com.uhi5d.spotibud.model.*
import kotlinx.android.synthetic.main.fragment_detailed_track.*


class DetailedTrackFragment : Fragment(), DetailedTrackArtistAdapter.OnItemClickListener {
    private val TAG = "DetailedTrack Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_track, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeInvisible()
        //disabling onbackpressed
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
        callback.isEnabled = true

        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        val bundle = this.arguments
        val name = bundle!!.getString("name")
        val id = bundle.getString("id")
        val image = bundle.getString("image")
        val artistId = bundle.getString("artistId")
        init(name!!,image!!)

        // ViewModel components
        val factory = CustomViewModelFactory(this,requireContext())
        viewmodel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewmodel.trackAudioFeatures.observe(viewLifecycleOwner,
             { t ->
                generateAudioFeature(t!!)
                })
        viewmodel.track.observe(viewLifecycleOwner,
             { t ->
                setRating(t!!)
                setAlbumImage(t)
                openInSpotify(t)
                viewmodel.getMultipleArtist(requireContext(),token!!,getArtists(t))})
        viewmodel.multipleArtists.observe(viewLifecycleOwner,
             { t -> setArtists(t!!)
                makeVisible()})
        viewmodel.getTrackAudioFeatures(requireContext(),token!!,id!!)
        viewmodel.getArtist(requireContext(),token,artistId!!)
        viewmodel.getTrack(requireContext(),token,id)


    }

    private fun generateAudioFeature(trackAudioFeatures: TrackAudioFeatures){
        tvDuration.text = Functions().msToMin(trackAudioFeatures.duration_ms)
        tvTempo.text = String.format("%d bpm",trackAudioFeatures.tempo.toInt())
        tvKey.text = String.format("%s%s",
            Functions().trackKey(trackAudioFeatures.key),
            Functions().trackMode(trackAudioFeatures.mode))

        pbDanceability.progress = (trackAudioFeatures.danceability*100).toInt()
        pbEnergy.progress = (trackAudioFeatures.energy*100).toInt()
        pbSpeechiness.progress = (trackAudioFeatures.speechiness*100).toInt()
        pbAcousticness.progress = (trackAudioFeatures.acousticness*100).toInt()
        pbInstrumentalness.progress = (trackAudioFeatures.instrumentalness*100).toInt()
        pbLiveness.progress = (trackAudioFeatures.liveness*100).toInt()
        pbValence.progress = (trackAudioFeatures.valence*100).toInt()

    }
    //getting all artist ids for multiple search
    private fun getArtists(track: TrackItems):String{
        var str = ""
        if (track.artists.size > 1){
            for(i in track.artists){
                str = "$str${i.id},"
            }
        }else{
            str = track.artists[0].id
        }
        if (str.endsWith(",")){
            str = str.substring(0,str.length-1)
        }

        Log.d(TAG, "getArtists: $str")
        return str
    }
    private fun setArtists(artistList: ArtistList){
        val adapter = DetailedTrackArtistAdapter(requireContext(),artistList,this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        recyclerTrackArtist.layoutManager = layoutManager
        recyclerTrackArtist.adapter = adapter
    }
    private fun init(name:String, image:String){
        Picasso.get()
            .load(image)
            .fit().centerCrop()
            .into(imageView)

        tvParallaxHeaderTrack.text = name

        detailedToolbarTrack.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //collapsing toolbar design section
        var isShow = true
        var scrollRange = -1
        appBarDetailedTrack.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                appBarLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = appBarLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                collapsingToolbarTrack.title = name
                isShow = true
            } else if (isShow){
                collapsingToolbarTrack.title = " "
                isShow = false
            }})
        /////////////////////////////////////////////////////////////////////////////////////

        detailedToolbarTrack.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun openInSpotify(track:TrackItems){
        fabDetailedTrack.setOnClickListener {
          try {

              val uri = Uri.parse("http://open.spotify.com/track/${track.id}")
              val intent = Intent(Intent.ACTION_VIEW,uri)
              startActivity(intent)
          }catch (ex:ActivityNotFoundException){
              Log.d(TAG, "setTrack: ${ex.message}")
          }
        }
    }
    private fun setRating(track: TrackItems){
        dtRatingBar.rating = track.popularity.toFloat()/20
        Log.d(TAG, "setRating: ${track.popularity.toFloat() / 20}")
    }

    private fun setAlbumImage(track: TrackItems){
        Picasso.get()
            .load(track.album.images[0].url)
            .fit().centerCrop()
            .into(imageViewAlbum)
        textViewAlbumName.text = track.album.name
        textViewAlbumArtist.text = track.artists[0].name

        imageViewAlbum.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name",track.album.name)
            bundle.putString("id",track.album.id)
            bundle.putString("image",track.album.images[0].url)
            findNavController().navigate(R.id.action_detailedTrackFragment_to_detailedAlbumFragment,bundle)
        }
    }

    override fun onItemClicked(artist: ArtistListArtists) {
        val bundle = Bundle()
        bundle.putString("name",artist.name)
        bundle.putString("id",artist.id)
        bundle.putString("image",artist.images[0].url)
        findNavController().navigate(R.id.action_detailedTrackFragment_to_itemDetailedFragment,bundle)
    }
    private fun makeInvisible(){
        dtRatingBar.visibility = View.GONE
        reLayoutDetailedTrack.visibility = View.GONE
        fabDetailedTrack.visibility = View.GONE
        pbDetailedTrack.visibility = View.VISIBLE
    }
    private fun makeVisible(){
        dtRatingBar.visibility = View.VISIBLE
        reLayoutDetailedTrack.visibility = View.VISIBLE
        fabDetailedTrack.visibility = View.VISIBLE
        pbDetailedTrack.visibility = View.GONE
    }



}