package com.example.contestifyfirsttry.share

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.main.MainViewModel
import com.example.contestifyfirsttry.model.Artists
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_share_layout_four.*
import kotlinx.android.synthetic.main.fragment_share_layout_two.*


class ShareLayoutFour : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_layout_four, container, false)
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
        viewModel!!.getMyArtistsLimited(token!!,"short_term",3)

    }
    private fun generateLayout(artists: Artists){
        //shape #1
        tvshareFourItemOne.text = artists.items[0].name
        Picasso.get()
            .load(artists.items[0].images[1].url)
            .fit().centerCrop()
            .into(ivShareFourOne)
        //shape #2
        tvshareFourItemTwo.text = artists.items[1].name
        Picasso.get()
            .load(artists.items[1].images[1].url)
            .fit().centerCrop()
            .into(ivShareFourTwo)
        //shape #3
        tvshareFourItemThree.text = artists.items[2].name
        Picasso.get()
            .load(artists.items[2].images[1].url)
            .fit().centerCrop()
            .into(ivShareFourThree)
    }
    private fun makeVisible(){
        pbShareFourOne.visibility = View.GONE
        pbShareFourTwo.visibility = View.GONE
        pbShareFourThree.visibility = View.GONE

        ivShareFourOne.visibility = View.VISIBLE
        tvshareFourItemOne.visibility = View.VISIBLE
        ivShareFourTwo.visibility = View.VISIBLE
        tvshareFourItemTwo.visibility = View.VISIBLE
        ivShareFourThree.visibility = View.VISIBLE
        tvshareFourItemThree.visibility = View.VISIBLE
    }
    private fun makeInvisible(){
        pbShareFourOne.visibility = View.VISIBLE
        pbShareFourTwo.visibility = View.VISIBLE
        pbShareFourThree.visibility = View.VISIBLE

        ivShareFourOne.visibility = View.GONE
        tvshareFourItemOne.visibility = View.GONE
        ivShareFourTwo.visibility = View.GONE
        tvshareFourItemTwo.visibility = View.GONE
        ivShareFourThree.visibility = View.GONE
        tvshareFourItemThree.visibility = View.GONE
    }


}