package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.Artists
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.TrackItems
import com.example.contestifyfirsttry.model.Item
import com.example.contestifyfirsttry.model.TrackAudioFeatures
import com.example.contestifyfirsttry.top.ArtistsAdapter
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.artists_fragment.*
import kotlinx.android.synthetic.main.fragment_detailed_track.*
import kotlinx.android.synthetic.main.fragment_detailed_track.imageView
import kotlinx.android.synthetic.main.fragment_item_detailed.*


class DetailedTrackFragment : Fragment() {
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
        val id = sharedPreferences.getString("id","")
        val artistId = sharedPreferences.getString("artistId","")
        val image = sharedPreferences.getString("image","")

        // ViewModel components
        var factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.trackAudioFeatures.observe(viewLifecycleOwner,
            Observer<TrackAudioFeatures> { t -> generateAudioFeature(t!!) })
        viewmodel!!.artist.observe(viewLifecycleOwner,
            Observer<Item> { t -> setArtistImage(t!!) })
        viewmodel!!.track.observe(viewLifecycleOwner,
            Observer<TrackItems> { t -> setTrack(t!!) })
        viewmodel.getTrackAudioFeatures(token!!,id!!)
        viewmodel.getArtist(token,artistId!!)
        viewmodel.getTrack(token,artistId!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val name = bundle!!.getString("name")
        val image = bundle!!.getString("image")
        setData(name!!,image!!)
    }

    private fun generateAudioFeature(trackAudioFeatures: TrackAudioFeatures){
        pbDanceability.progress = trackAudioFeatures.danceability.toFloat()*100
        pbEnergy.progress = trackAudioFeatures.energy.toFloat()*100
        pbLoudness.progress = trackAudioFeatures.loudness.toFloat()*100
        pbSpeechiness.progress = trackAudioFeatures.speechiness.toFloat()*100
        pbAcousticness.progress = trackAudioFeatures.acousticness.toFloat()*100
        pbInstrumentalness.progress = trackAudioFeatures.instrumentalness.toFloat()*100
        pbLiveness.progress = trackAudioFeatures.liveness.toFloat()*100
        pbValence.progress = trackAudioFeatures.valence.toFloat()*100

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
    fun setArtistImage(artist:Item){
        Picasso.get()
            .load(artist.images[0].url)
            .fit().centerCrop()
            .into(imageViewArtist)
        textViewArtistName.text = artist.name

        imageViewArtist.setOnClickListener {
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