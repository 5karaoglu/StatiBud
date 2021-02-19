package com.example.contestifyfirsttry.share

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.contestifyfirsttry.main.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.Artists
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_share_layout_one.*


class ShareLayoutOne : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_layout_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeInvisible()
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        // ViewModel components
        var factory = CustomViewModelFactory(this,requireContext())
        viewModel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewModel!!.artistsListShortTerm.observe(viewLifecycleOwner,
            Observer<Artists> { t ->
                generateLayout(t!!)
                makeVisible()
            })
        viewModel!!.getMyArtistsLimited(token!!,"short_term",1)
    }
    private fun generateLayout(artists: Artists){
        tvShareOneArtistName.text = artists.items[0].name
        Picasso.get()
            .load(artists.items[0].images[0].url)
            .fit().centerCrop()
            .into(ivShareOne)
    }
    private fun makeVisible(){
        shareLayout1Holder.visibility  = View.VISIBLE
        shareLayout1Pb.visibility = View.GONE
    }
    private fun makeInvisible(){
        shareLayout1Holder.visibility  = View.GONE
        shareLayout1Pb.visibility = View.VISIBLE
    }

}