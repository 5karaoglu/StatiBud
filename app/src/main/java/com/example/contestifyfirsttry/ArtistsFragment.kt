package com.example.contestifyfirsttry

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

class ArtistsFragment : Fragment() {

    companion object {
        fun newInstance() = ArtistsFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.artists_fragment, container, false)
    }

    fun newInstance(text:String):ArtistsFragment {
        var artistsFragment = ArtistsFragment()
        var bundle = Bundle()
        bundle.putString("msg",text)

        artistsFragment.arguments = bundle
        return artistsFragment
    }

}