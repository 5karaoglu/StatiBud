package com.uhi5d.spotibud.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhi5d.spotibud.databinding.FragmentDetailedResultBinding
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsAlbumsItem
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsArtistsItem
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsTracksItem
import com.uhi5d.spotibud.presentation.ui.detailed.album.toDetailedAlbumFragmentModel
import com.uhi5d.spotibud.presentation.ui.detailed.artist.toDetailedArtistFragmentModel
import com.uhi5d.spotibud.presentation.ui.detailed.track.toDetailedTrackFragmentModel
import com.uhi5d.spotibud.util.DataState
import com.uhi5d.spotibud.util.showIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedResultsFragment : Fragment(),
    DetailedResultsAdapter.OnItemClickListener {
    private val TAG = "Detailed Results"

    private var _binding: FragmentDetailedResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailedResultsViewModel by viewModels()
    private val args : DetailedResultsFragmentArgs by navArgs()

    private val margin = 10
    private lateinit var detailedResultsAdapter: DetailedResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailedResultsAdapter = DetailedResultsAdapter(requireContext(),this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerDetailedResult){
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            addItemDecoration(SearchResultsItemDecoration(margin))
        }
        viewModel.searchResults.observe(viewLifecycleOwner){state ->
            binding.shimmerLayout.showIf { state is DataState.Loading }
            when(state){
                is DataState.Success -> {
                    args.header?.let { detailedResultsAdapter.setDataType(it) }
                    detailedResultsAdapter.setSearchResults(state.data)
                }
                DataState.Empty -> {}
                is DataState.Fail -> {}
                DataState.Loading -> {}
            }
        }
    }

    override fun onTrackItemClicked(item: SearchResultsTracksItem) {
        val action =
            DetailedResultsFragmentDirections
                .actionDetailedResultsFragmentToDetailedTrackFragment(item.toDetailedTrackFragmentModel())
        findNavController().navigate(action)
    }

    override fun onArtistItemClicked(item: SearchResultsArtistsItem) {
        val action =
            DetailedResultsFragmentDirections
                .actionDetailedResultsFragmentToDetailedArtistFragment(item.toDetailedArtistFragmentModel())
        findNavController().navigate(action)
    }

    override fun onAlbumItemClicked(item: SearchResultsAlbumsItem) {
        val action =
            DetailedResultsFragmentDirections
                .actionDetailedResultsFragmentToDetailedAlbumFragment(item.toDetailedAlbumFragmentModel())
        findNavController().navigate(action)
    }
}