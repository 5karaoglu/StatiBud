package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.StringUtil
import com.example.contestifyfirsttry.*
import com.example.contestifyfirsttry.model.*
import com.example.contestifyfirsttry.top.ArtistsAdapter
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.artists_fragment.*
import kotlinx.android.synthetic.main.fragment_detailed_track.*
import kotlinx.android.synthetic.main.fragment_detailed_track.imageView
import kotlinx.android.synthetic.main.fragment_item_detailed.*


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")
        Log.d(TAG, "onActivityCreated: $token")
        val id = sharedPreferences.getString("id","")
        Log.d(TAG, "onActivityCreated: $id")
        val artistId = sharedPreferences.getString("artistId","")
        val image = sharedPreferences.getString("image","")

        // ViewModel components
        var factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.trackAudioFeatures.observe(viewLifecycleOwner,
            Observer<TrackAudioFeatures> { t -> generateAudioFeature(t!!) })
        viewmodel!!.artist.observe(viewLifecycleOwner,
            Observer<Item> { t -> /*setArtistImage(t!!)*/ })
        viewmodel!!.track.observe(viewLifecycleOwner,
            Observer<TrackItems> { t ->
                setAlbumImage(t!!)
                setTrack(t)
                viewmodel.getMultipleArtist(token!!,getArtists(t))})
        viewmodel!!.multipleArtists.observe(viewLifecycleOwner,
            Observer<ArtistList> { t -> setArtists(t!!) })
        viewmodel.getTrackAudioFeatures(token!!,id!!)
        viewmodel.getArtist(token,artistId!!)
        viewmodel.getTrack(token,id!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //disabling onbackpressed
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
        callback.isEnabled = true
        val bundle = this.arguments
        val name = bundle!!.getString("name")
        val image = bundle!!.getString("image")
        setData(name!!,image!!)
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
        var adapter : DetailedTrackArtistAdapter = DetailedTrackArtistAdapter(requireContext(),artistList,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerTrackArtist.layoutManager = layoutManager
        recyclerTrackArtist.adapter = adapter
    }
    fun setData(name:String,image:String){
        Picasso.get()
            .load(image)
            .fit().centerCrop()
            .into(imageView)

        detailedTrackToolbar.title = name
    }
    private fun setTrack(track:TrackItems){

    }

    private fun setAlbumImage(track: TrackItems){
        Picasso.get()
            .load(track.album.images[0].url)
            .fit().centerCrop()
            .into(imageViewAlbum)
        textViewAlbumName.text = track.album.name

        imageViewAlbum.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name",track.album.name)
            bundle.putString("id",track.album.id)
            bundle.putString("image",track.album.images[0].url)
            val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("id",track.album.id)
            editor.apply()
            findNavController().navigate(R.id.action_detailedTrackFragment_to_detailedAlbumFragment,bundle)
        }
    }

    override fun onItemClicked(artist: ArtistListArtists) {
        val bundle = Bundle()
        bundle.putString("name",artist.name)
        bundle.putString("id",artist.id)
        bundle.putString("image",artist.images[0].url)
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",artist.id)
        editor.apply()
        findNavController().navigate(R.id.action_detailedTrackFragment_to_itemDetailedFragment,bundle)
    }


    /*override fun onItemClicked(artist: Item) {
        Log.d(TAG, "onClick: ${artist.name}")

        val bundle = Bundle()
        bundle.putString("name",artist.name)
        bundle.putString("id",artist.id)
        bundle.putString("image",artist.images[0].url)
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",artist.id)
        editor.apply()
        findNavController().navigate(R.id.action_topFragment_to_itemDetailedFragment,bundle)
    }*/
}