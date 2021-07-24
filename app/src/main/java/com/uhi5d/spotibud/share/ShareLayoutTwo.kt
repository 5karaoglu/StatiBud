package com.uhi5d.spotibud.share

/*
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.Tracks
import com.uhi5d.spotibud.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_share_layout_two.*


class ShareLayoutTwo : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_layout_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeInvisible()
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        // ViewModel components
        var factory = CustomViewModelFactory(this,requireContext())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.tracksListShortTerm.observe(viewLifecycleOwner,
            Observer<Tracks> { t ->
                generateLayout(t!!)
                makeVisible()
            })
        viewModel.getMyTracksLimited(requireContext(),token!!,"short_term",1)
    }
    private fun generateLayout(tracks: Tracks){
        tvShareTwoArtistName.text = tracks.items[0].name
        Picasso.get()
            .load(tracks.items[0].album.images[0].url)
            .fit().centerCrop()
            .into(ivShareTwo)
    }
    private fun makeVisible(){
        shareLayout2Holder.visibility  = View.VISIBLE
        shareLayout2Pb.visibility = View.GONE
    }
    private fun makeInvisible(){
        shareLayout2Holder.visibility  = View.GONE
        shareLayout2Pb.visibility = View.VISIBLE
    }
}*/
