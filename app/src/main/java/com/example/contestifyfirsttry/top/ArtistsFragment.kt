package com.example.contestifyfirsttry.top

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.*
import com.example.contestifyfirsttry.model.Item
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.artists_fragment.*

class ArtistsFragment : Fragment(), ArtistsAdapter.OnItemClickListener {
    private val TAG = "Artists Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.artists_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRadioGroup()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")


        // ViewModel components
        var factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.artistsListShortTerm.observe(viewLifecycleOwner,
            Observer<Artists> { t -> generateDataArtists(t!!) })
        viewmodel.getMyArtists(token!!,"short_term")
        viewmodel.getMyArtists(token!!,"medium_term")
        viewmodel.getMyArtists(token!!,"long_term")
    }
    private fun initRadioGroup(){
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                radioButton1.id -> generateDataArtists(viewmodel.artistsListShortTerm.value!!)
                radioButton2.id -> generateDataArtists(viewmodel.artistsListMidTerm.value!!)
                radioButton3.id -> generateDataArtists(viewmodel.artistsListLongTerm.value!!)
            }
        }
    }


    private fun generateDataArtists(artists: Artists){
        var adapter : ArtistsAdapter = ArtistsAdapter(requireContext(),artists,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerArtists.layoutManager = layoutManager
        recyclerArtists.adapter = adapter
    }


    override fun onItemClicked(artist: Item) {
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
    }

}