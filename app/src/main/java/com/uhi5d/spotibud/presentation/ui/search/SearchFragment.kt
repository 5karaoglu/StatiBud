package com.uhi5d.spotibud.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhi5d.spotibud.application.ToastHelper
import com.uhi5d.spotibud.databinding.FragmentSearchBinding
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsAlbumsItem
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsArtistsItem
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsTracksItem
import com.uhi5d.spotibud.presentation.ui.detailed.album.toDetailedAlbumFragmentModel
import com.uhi5d.spotibud.presentation.ui.detailed.artist.toDetailedArtistFragmentModel
import com.uhi5d.spotibud.presentation.ui.detailed.track.toDetailedTrackFragmentModel
import com.uhi5d.spotibud.util.DataState
import com.uhi5d.spotibud.util.showIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(),
    SearchResultsAdapter.OnItemClickListener {
    private val TAG = "Search Fragment"
    private val viewModel: SearchViewModel by viewModels()

    @Inject
    lateinit var toastHelper: ToastHelper

    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private val margin = 10

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var token: String
    private lateinit var query: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchResultsAdapter = SearchResultsAdapter(requireContext(),this)
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(listener)
        with(binding.recyclerSearch){
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,
                false)
            adapter = searchResultsAdapter
            addItemDecoration(SearchResultsItemDecoration(margin))
        }
        /*binding.etSearch.addTextChangedListener(search)*/
        viewModel.searchResults.observe(viewLifecycleOwner){ state ->
            binding.shimmerLayout.showIf { state is DataState.Loading }
            binding.recyclerSearch.showIf { state is DataState.Success }
            when(state){
                is DataState.Success -> {
                    searchResultsAdapter.setSearchResultsTracks(state.data.tracks!!)
                    searchResultsAdapter.setSearchResultsArtists(state.data.artists!!)
                    searchResultsAdapter.setSearchResultsAlbums(state.data.albums!!)
                }
                is DataState.Fail -> {
                    toastHelper.errorMessage(state.e.message!!)
                }
            }

        }
        viewModel.token.observe(viewLifecycleOwner){
            if (it.length > 10){
                token = it
            }
        }

    }



    override fun onTrackItemClicked(item: SearchResultsTracksItem) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailedTrackFragment(
            item.toDetailedTrackFragmentModel()
        )
        findNavController().navigate(action)
    }

    override fun onArtistItemClicked(item: SearchResultsArtistsItem) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailedArtistFragment2(
            item.toDetailedArtistFragmentModel()
        )
        findNavController().navigate(action)
    }

    override fun onAlbumItemClicked(item: SearchResultsAlbumsItem) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailedAlbumFragment2(
            item.toDetailedAlbumFragmentModel()
        )
        findNavController().navigate(action)
    }

    override fun onHeaderItemClicked(header: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailedResultsFragment(header,query)
        findNavController().navigate(action)
    }

    @InternalCoroutinesApi
    val listener = object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            binding.searchView.clearFocus()
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null && this@SearchFragment::token.isInitialized) {
                viewModel.search(token,newText)
                query = newText
            }
            return false
        }
    }

}