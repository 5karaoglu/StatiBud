package com.uhi5d.spotibud.presentation.ui.home


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.databinding.FragmentHomeBinding
import com.uhi5d.spotibud.domain.model.RecommendationsTrack
import com.uhi5d.spotibud.domain.model.recenttracks.RecentTracksItem
import com.uhi5d.spotibud.util.DataState
import com.uhi5d.spotibud.util.showIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(),
RecommendationsAdapter.OnItemClickListener,
RecentTracksAdapter.OnItemClickListener{
    private var TAG = "Home Fragment"

    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding
        get() = _binding!!


    private val viewModel: HomeViewModel by viewModels()
    private lateinit var recommendationsAdapter: RecommendationsAdapter
    private lateinit var recentTracksAdapter: RecentTracksAdapter

    private val recentTracksAdapterCollapsedItemSize = 5
    private var recentTracksAdapterMaxItemSize = 0
    private var isCollapsed = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recommendationsAdapter = RecommendationsAdapter(requireContext(),this)
        recentTracksAdapter = RecentTracksAdapter(requireContext(),this)
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRecommendations()
        viewModel.recommendations.observe(viewLifecycleOwner){ state ->
            binding.shimmerLayout.showIf { state is DataState.Loading }
            when(state){
                is DataState.Success -> {
                    state.data.tracks?.let {
                        recommendationsAdapter.setTrackList(it)
                        recentTracksAdapterMaxItemSize = it.size}
                }
                DataState.Empty -> TODO()
                is DataState.Fail -> {
                }
                DataState.Loading -> TODO()
            }
        }
        viewModel.getRecentTracks()
        viewModel.recentTracks.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> {
                    state.data.items?.let { recentTracksAdapter.setRecentTracksList(it) }
                }
                DataState.Empty -> TODO()
                is DataState.Fail -> TODO()
                DataState.Loading -> TODO()
            }
        }

        binding.buttonRecyclerExpand.setOnClickListener(recentTrackButtonClick)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu,menu)

        val getItem = menu.findItem(R.id.get_item)
        if (getItem != null) {
            val button = getItem.actionView
            button.setBackgroundResource(R.drawable.ic_person)
            button.setOnClickListener(goProfile)
        }
    }

    private val goProfile = View.OnClickListener {
        val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    override fun onItemClicked(recommendationsTrack: RecommendationsTrack) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailedTrackFragment(
            null,recommendationsTrack,null)
        findNavController().navigate(action)
    }

    override fun onItemClicked(recentTrack: RecentTracksItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailedTrackFragment(
            recentTrack,null,null)
        findNavController().navigate(action)
    }

    val recentTrackButtonClick = View.OnClickListener {
        isCollapsed = if(isCollapsed){
            recentTracksAdapter.setAdapterSize(recentTracksAdapterMaxItemSize)
            false
        }else{
            recentTracksAdapter.setAdapterSize(recentTracksAdapterCollapsedItemSize)
            true
        }
    }


}