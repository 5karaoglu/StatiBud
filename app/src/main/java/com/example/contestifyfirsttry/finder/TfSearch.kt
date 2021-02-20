package com.example.contestifyfirsttry.finder

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.Functions
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.main.MainViewModel
import com.example.contestifyfirsttry.model.QueryResultTrackItem
import com.example.contestifyfirsttry.model.QueryResults
import com.example.contestifyfirsttry.search.QueryResultAlbumAdapter
import com.example.contestifyfirsttry.search.QueryResultArtistAdapter
import com.example.contestifyfirsttry.search.QueryResultTrackAdapter
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_tf_search.*
import kotlinx.android.synthetic.main.fragment_track_finder.*


class TfSearch : Fragment(),
    TfSearchResultsAdapter.OnItemClickListener{
    private var TAG = "TrackFinderSearch Fragment"
    private var viewModel: MainViewModel? = null
    private var functions = Functions()
    private var token: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tf_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences(
            "spotifystatsapp",
            Context.MODE_PRIVATE
        )
        token = sharedPreferences.getString("token", "")


        // ViewModel components
        var factory = CustomViewModelFactory(this, requireContext())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel!!.queryResults.observe(viewLifecycleOwner,
            Observer { t -> setResults(t!!) })
    }
    private fun init(){
        var q : String? = null
        etTfSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    //makes visible if only gone
                    if (recyclerTf.visibility == View.GONE) {
                        visibilityVisible()
                    }
                    q = functions.encodeString(s.toString())
                    Log.d(TAG, "afterTextChanged: $q")
                    viewModel!!.getQueryResult(token!!, q!!)
                } else {
                    visibilityGone()
                }
            }

        })
        ivTfSearch.setOnClickListener {
            hideKeyboard()
        }
        ivTfBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun hideKeyboard(){
        val inputManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (requireActivity().currentFocus != null){
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
        }
        etSearch.text.clear()
    }
    private fun setSearchImageCross(isCross: Boolean){
        if(isCross){
            ivTfSearch.setImageResource(R.drawable.ic_close_gray)
        }else{
            ivTfSearch.setImageResource(R.drawable.ic_search)
        }
    }
    private fun visibilityGone(){
        recyclerTf.visibility = View.GONE
        setSearchImageCross(false)
    }
    private fun visibilityVisible(){
        recyclerTf.visibility = View.VISIBLE
        setSearchImageCross(true)
    }
    private fun setResults(queryResults: QueryResults){
        //recycler adapter for artists
        val resultsAdapter = TfSearchResultsAdapter(
            requireContext(),
            queryResults,
            this,
        )
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerTf.layoutManager = layoutManager
        recyclerTf.adapter = resultsAdapter
    }

    override fun onItemClicked(queryResultTrackItem: QueryResultTrackItem) {
        val bundle = Bundle()
        bundle.putString("id", queryResultTrackItem.id)
        bundle.putString("artistId",queryResultTrackItem.album.artists[0].id)
        bundle.putString("name", queryResultTrackItem.name)
        bundle.putString("image", queryResultTrackItem.album.images[0].url)
        bundle.putString("artistName", queryResultTrackItem.album.artists[0].name)
        findNavController().navigate(R.id.action_tfSearch_to_trackFinder, bundle)
    }
}