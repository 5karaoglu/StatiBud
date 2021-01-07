package com.example.contestifyfirsttry

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TracksFragment : Fragment() {

    companion object {
        fun newInstance() = TracksFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tracks_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    fun newInstance(text:String):TracksFragment {
        var tracksFragment = TracksFragment()
        var bundle = Bundle()
        bundle.putString("msg",text)

        tracksFragment.arguments = bundle
        return tracksFragment
    }

}