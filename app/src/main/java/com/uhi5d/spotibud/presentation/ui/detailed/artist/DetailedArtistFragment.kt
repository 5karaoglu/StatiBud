package com.uhi5d.spotibud.presentation.ui.detailed.artist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.uhi5d.spotibud.application.ToastHelper
import com.uhi5d.spotibud.databinding.FragmentDetailedArtistBinding
import com.uhi5d.spotibud.domain.model.artist.Artist
import com.uhi5d.spotibud.domain.model.artistalbums.ArtistAlbumsItem
import com.uhi5d.spotibud.domain.model.artisttoptracks.ArtistTopTracksTrack
import com.uhi5d.spotibud.domain.model.relatedartists.RelatedArtistsArtist
import com.uhi5d.spotibud.presentation.ui.detailed.ARTIST_URI
import com.uhi5d.spotibud.presentation.ui.detailed.DetailedViewModel
import com.uhi5d.spotibud.presentation.ui.detailed.album.toDetailedAlbumFragmentModel
import com.uhi5d.spotibud.presentation.ui.detailed.track.toDetailedTrackFragmentModel
import com.uhi5d.spotibud.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detailed_artist.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailedArtistFragment : Fragment(),
    ArtistAlbumsAdapter.OnItemClickListener,
    ArtistTopTracksAdapter.OnItemClickListener,
    RelatedArtistsAdapter.OnItemClickListener {
    private val viewModel : DetailedViewModel by viewModels()
    private val args : DetailedArtistFragmentArgs by navArgs()

    @Inject
    lateinit var toastHelper: ToastHelper
    
    private lateinit var topTracksAdapter : ArtistTopTracksAdapter
    private lateinit var albumsAdapter: ArtistAlbumsAdapter
    private lateinit var relatedArtistsAdapter: RelatedArtistsAdapter

    private var _binding : FragmentDetailedArtistBinding? = null
    private val binding get() =  _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentDetailedArtistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topTracksAdapter = ArtistTopTracksAdapter(requireContext(),this)
        albumsAdapter = ArtistAlbumsAdapter(requireContext(),this)
        relatedArtistsAdapter = RelatedArtistsAdapter(requireContext(),this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            with(recyclerTopTracks){
                adapter = topTracksAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(CustomItemDecoration(DEFAULT_MARGIN))
            }
            with(recyclerAlbums){
                adapter = albumsAdapter
                layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL,false)
                addItemDecoration(CustomItemDecoration(DEFAULT_MARGIN))
            }
            with(recyclerRelatedArtists){
                adapter = relatedArtistsAdapter
                layoutManager = GridLayoutManager(requireContext(),3)
                addItemDecoration(CustomItemDecoration(DEFAULT_MARGIN))
            }
        }
        val id = args.detailedArtistFragmentModel.id
        val market = getLocation(requireContext())!!
        
        viewModel.token.observe(viewLifecycleOwner){
            if (it.length > 10){
                viewModel.getArtists(it,args.detailedArtistFragmentModel.id)
                viewModel.getArtistsTopTracks(it,id, getLocation(requireContext())!!)
                viewModel.getArtistsAlbums(it,id,market)
                viewModel.getRelatedArtists(it,id,market)
            }
        }
        viewModel.artists.observe(viewLifecycleOwner){ state ->
            binding.success.showIf { state is DataState.Success }
            when(state){
                is DataState.Success -> {
                    state.data.artists?.get(0).let {
                        if (it != null) {
                            init(it)
                        }
                    }
                    binding.shimmer.hide()
                }
            }
        }
       
        viewModel.artistsTopTracks.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> {
                    topTracksAdapter.setList(state.data.tracks!!)
                }
                DataState.Empty -> toastHelper.sendToast("Error : No data")
                is DataState.Fail -> toastHelper.sendToast("Error : ${state.e.localizedMessage}")
                DataState.Loading -> {}
            }
        }
        
        viewModel.artistsAlbums.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> albumsAdapter.setList(state.data.items!!.toList())
                DataState.Empty -> toastHelper.sendToast("Error : No data")
                is DataState.Fail -> toastHelper.sendToast("Error : ${state.e.localizedMessage}")
                DataState.Loading -> {}
            }
        }
        viewModel.relatedArtists.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> relatedArtistsAdapter.setList(state.data.artists!!)
                DataState.Empty -> toastHelper.sendToast("Error : No data")
                is DataState.Fail -> toastHelper.sendToast("Error : ${state.e.localizedMessage}")
                DataState.Loading -> {}
            }
        }
    }

    private fun init(artist: Artist){
        with(binding){
            tvArtistName.text = artist.name
            dtRatingBar.rating = (artist.popularity!!.toFloat()/20)
            iv.loadWithPicasso(artist.images?.get(0)?.url)
        }

        binding.detailedToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //collapsing toolbar design section
        var isShow = true
        var scrollRange = -1
        appBarDetailedArtist.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                appBarLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = appBarLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                collapsingToolbarArtist.title = artist.name
                isShow = true
            } else if (isShow){
                collapsingToolbarArtist.title = " "
                isShow = false
        }})
        fabDetailedArtist.setOnClickListener {
            try {
                val uri = Uri.parse("$ARTIST_URI${id}")
                val intent = Intent(Intent.ACTION_VIEW,uri)
                startActivity(intent)
            }catch (ex: ActivityNotFoundException){
                toastHelper.sendToast(ex.message!!)
            }
        }
    }

    override fun onItemClicked(item: ArtistAlbumsItem) {
        val action = DetailedArtistFragmentDirections.actionDetailedArtistFragmentToDetailedAlbumFragment(
            item.toDetailedAlbumFragmentModel()
        )
        findNavController().navigate(action)
    }

    override fun onItemClicked(item: ArtistTopTracksTrack) {
        val action = DetailedArtistFragmentDirections.actionDetailedArtistFragmentToDetailedTrackFragment(
            item.toDetailedTrackFragmentModel()
        )
        findNavController().navigate(action)
    }

    override fun onItemClicked(item: RelatedArtistsArtist) {
        val action = DetailedArtistFragmentDirections.actionDetailedArtistFragmentSelf(
            item.toDetailedArtistFragmentModel()
        )
        findNavController().navigate(action)
    }
}