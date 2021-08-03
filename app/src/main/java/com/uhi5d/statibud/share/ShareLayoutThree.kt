package com.uhi5d.statibud.share

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
import kotlinx.android.synthetic.main.fragment_share_layout_three.*


class ShareLayoutThree : Fragment() {
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_layout_three, container, false)
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
        viewModel.getMyTracksLimited(requireContext(),token!!,"short_term",3)

    }
    private fun generateLayout(tracks: Tracks){
        //shape #1
        tvshareThreeItemOne.text = tracks.items[0].name
        Picasso.get()
            .load(tracks.items[0].album.images[1].url)
            .fit().centerCrop()
            .into(ivShareThreeOne)
        //shape #2
        tvshareThreeItemTwo.text = tracks.items[1].name
        Picasso.get()
            .load(tracks.items[1].album.images[1].url)
            .fit().centerCrop()
            .into(ivShareThreeTwo)
        //shape #3
        tvshareThreeItemThree.text = tracks.items[2].name
        Picasso.get()
            .load(tracks.items[2].album.images[1].url)
            .fit().centerCrop()
            .into(ivShareThreeThree)
    }
    private fun makeVisible(){
        pbShareThreeOne.visibility = View.GONE
        pbShareThreeTwo.visibility = View.GONE
        pbShareThreeThree.visibility = View.GONE

        ivShareThreeOne.visibility = View.VISIBLE
        tvshareThreeItemOne.visibility = View.VISIBLE
        ivShareThreeTwo.visibility = View.VISIBLE
        tvshareThreeItemTwo.visibility = View.VISIBLE
        ivShareThreeThree.visibility = View.VISIBLE
        tvshareThreeItemThree.visibility = View.VISIBLE
    }
    private fun makeInvisible(){
        pbShareThreeOne.visibility = View.VISIBLE
        pbShareThreeTwo.visibility = View.VISIBLE
        pbShareThreeThree.visibility = View.VISIBLE

        ivShareThreeOne.visibility = View.GONE
        tvshareThreeItemOne.visibility = View.GONE
        ivShareThreeTwo.visibility = View.GONE
        tvshareThreeItemTwo.visibility = View.GONE
        ivShareThreeThree.visibility = View.GONE
        tvshareThreeItemThree.visibility = View.GONE
    }

}*/
