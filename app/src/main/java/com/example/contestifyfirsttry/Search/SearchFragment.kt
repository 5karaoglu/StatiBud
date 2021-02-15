package com.example.contestifyfirsttry.Search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.Functions
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.*
import com.example.contestifyfirsttry.util.AppDatabase
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment(),
    QueryResultArtistAdapter.OnItemClickListener,
    QueryResultTrackAdapter.OnItemClickListener,
    QueryResultAlbumAdapter.OnItemClickListener,
    SearchHistoryAdapter.OnItemClickListener{
    private val TAG = "Search Fragment"
    private lateinit var viewmodel: MainViewModel
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //disabling onbackpressed
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
        visibilityGone()
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        // ViewModel components
        var factory = CustomViewModelFactory(this,requireContext())
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)


        viewmodel.queryResults.observe(viewLifecycleOwner,
        Observer { t -> setResults(t!!) })
        init(token!!)
        viewmodel.searchHistory.observe(viewLifecycleOwner,
            Observer { t -> setSearchHistory(t!!) })
        Thread{
            viewmodel.getAll()
        }.start()

    }
    private fun setSearchHistory(searchHistory: List<SearchHistory>){
        var adapter = SearchHistoryAdapter(requireContext(),searchHistory,this)
        var layoutManager = LinearLayoutManager(requireContext())
        recyclerSearchHistory.layoutManager = layoutManager
        recyclerSearchHistory.adapter = adapter
    }
    private fun init(token:String){
        ivSearch.setOnClickListener {
            if (etSearch.editableText != null){
                etSearch.text.clear()
                visibilityGone()
                hideKeyboard()
            }
        }

        var q : String? = null
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //makes visible if only gone
                    if (recyclerSearchArtists.visibility == View.GONE){
                        visibilityVisible()
                    }
                    q = Functions().stringToQuery(s.toString())
                    viewmodel.getQueryResult(token, q!!)
                }else{
                    visibilityGone()
                }
            }
        })
        tvArtistsMore.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type","artist")
            bundle.putString("q",q)
            findNavController().navigate(R.id.action_searchFragment_to_detailedResult,bundle)
            hideKeyboard()
        }
        tvTracksMore.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type","track")
            bundle.putString("q",q)
            findNavController().navigate(R.id.action_searchFragment_to_detailedResult,bundle)
            hideKeyboard()
        }
        tvAlbumsMore.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type","album")
            bundle.putString("q",q)
            findNavController().navigate(R.id.action_searchFragment_to_detailedResult,bundle)
            hideKeyboard()
        }
    }

    private fun setResults(queryResults: QueryResults){
        //recycler adapter for artists
        Log.d(TAG, "setResults: ${queryResults.artists}")
        val artistsAdapter = QueryResultArtistAdapter(requireContext(),queryResults.artists,this,false)
        val artistLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        recyclerSearchArtists.layoutManager = artistLayoutManager
        recyclerSearchArtists.adapter = artistsAdapter
        //recycler adapter for tracks
        Log.d(TAG, "setResults: ${queryResults.tracks}")
        val tracksAdapter = QueryResultTrackAdapter(requireContext(),queryResults.tracks,this)
        val tracksLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerSearchTracks.layoutManager = tracksLayoutManager
        recyclerSearchTracks.adapter = tracksAdapter
        //recycler adapter for albums
        Log.d(TAG, "setResults: ${queryResults.albums}")
        val albumsAdapter = QueryResultAlbumAdapter(requireContext(),queryResults.albums,this)
        val albumsLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerSearchAlbums.layoutManager = albumsLayoutManager
        recyclerSearchAlbums.adapter = albumsAdapter
    }

    override fun onItemClicked(currentArtist: QueryResultArtistsItem) {
        val bundle = Bundle()
        bundle.putString("id",currentArtist.id)
        bundle.putString("name",currentArtist.name)
        if (currentArtist.images.isNotEmpty()){
        bundle.putString("image",currentArtist.images[0].url)}
        hideKeyboard()
        val searchHistory = SearchHistory(null,getString(R.string.type_artist),currentArtist.id,currentArtist.name,null,currentArtist.images[0].url)
        Thread{
            viewmodel.insert(searchHistory)
        }.start()
        visibilityGone()
        findNavController().navigate(R.id.action_searchFragment_to_itemDetailedFragment,bundle)
    }

    override fun onItemClicked(currentTrack: QueryResultTrackItem) {
        val bundle = Bundle()
        bundle.putString("id",currentTrack.id)
        bundle.putString("artistId",currentTrack.artists[0].id)
        bundle.putString("name",currentTrack.name)
        bundle.putString("image",currentTrack.album.images[0].url)
        hideKeyboard()
        val searchHistory = SearchHistory(null,getString(R.string.type_artist),currentTrack.id,currentTrack.name,currentTrack.artists[0].id,currentTrack.album.images[0].url)
        Thread{
            viewmodel.insert(searchHistory)
        }.start()
        visibilityGone()
        findNavController().navigate(R.id.action_searchFragment_to_detailedTrackFragment,bundle)
    }

    override fun onItemClicked(currentAlbum: QueryResultAlbumItem) {
        val bundle = Bundle()
        bundle.putString("id",currentAlbum.id)
        bundle.putString("artistId",currentAlbum.artists[0].id)
        bundle.putString("name",currentAlbum.name)
        bundle.putString("image",currentAlbum.images[0].url)
        hideKeyboard()
        val searchHistory = SearchHistory(null,getString(R.string.type_artist),currentAlbum.id,currentAlbum.name,currentAlbum.artists[0].id,currentAlbum.images[0].url)
        Thread{
            viewmodel.insert(searchHistory)
        }.start()
        visibilityGone()
        findNavController().navigate(R.id.action_searchFragment_to_detailedAlbumFragment,bundle)
    }

    override fun onItemClicked(searchHistory: SearchHistory) {
        val bundle = Bundle()
        bundle.putString("id",searchHistory.aid)
        bundle.putString("artistId",searchHistory.artistId)
        bundle.putString("name",searchHistory.name)
        bundle.putString("image",searchHistory.aImage)
        hideKeyboard()
        visibilityGone()
        when(searchHistory.type){
            "artist" -> findNavController().navigate(R.id.action_searchFragment_to_itemDetailedFragment,bundle)
            "track" -> findNavController().navigate(R.id.action_searchFragment_to_detailedTrackFragment,bundle)
            "album" -> findNavController().navigate(R.id.action_searchFragment_to_detailedAlbumFragment,bundle)
        }


    }
    private fun visibilityGone(){
        tvArtists.visibility = View.GONE
        tvTracks.visibility = View.GONE
        tvAlbums.visibility = View.GONE
        tvArtistsMore.visibility = View.GONE
        tvTracksMore.visibility = View.GONE
        tvAlbumsMore.visibility = View.GONE
        recyclerSearchArtists.visibility = View.GONE
        recyclerSearchTracks.visibility = View.GONE
        recyclerSearchAlbums.visibility = View.GONE
        reLayoutSearchHistory.visibility = View.VISIBLE

        setSearchImageCross(false)
    }
    private fun visibilityVisible(){
        tvArtists.visibility = View.VISIBLE
        tvTracks.visibility = View.VISIBLE
        tvAlbums.visibility = View.VISIBLE
        tvArtistsMore.visibility = View.VISIBLE
        tvTracksMore.visibility = View.VISIBLE
        tvAlbumsMore.visibility = View.VISIBLE
        recyclerSearchArtists.visibility = View.VISIBLE
        recyclerSearchTracks.visibility = View.VISIBLE
        recyclerSearchAlbums.visibility = View.VISIBLE
        reLayoutSearchHistory.visibility = View.GONE

        setSearchImageCross(true)
    }
    private fun hideKeyboard(){
        val inputManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken,0)

        etSearch.text.clear()
    }
    private fun setSearchImageCross(isCross : Boolean){
        if(isCross){
            ivSearch.setImageResource(R.drawable.ic_close_gray)
        }else{
            ivSearch.setImageResource(R.drawable.ic_search)
        }
    }




}