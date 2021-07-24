package com.uhi5d.spotibud.presentation.ui.detailed.artist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.uhi5d.spotibud.databinding.FragmentDetailedArtistBinding
import com.uhi5d.spotibud.domain.model.artistalbums.ArtistAlbumsItem
import com.uhi5d.spotibud.domain.model.artisttoptracks.ArtistTopTracksTrack
import com.uhi5d.spotibud.domain.model.relatedartists.RelatedArtistsArtist
import com.uhi5d.spotibud.presentation.ui.detailed.ARTIST_URI
import com.uhi5d.spotibud.presentation.ui.detailed.DetailedViewModel
import com.uhi5d.spotibud.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detailed_artist.*

@AndroidEntryPoint
class DetailedArtistFragment : Fragment(),
    ArtistAlbumsAdapter.OnItemClickListener,
    ArtistTopTracksAdapter.OnItemClickListener,
    RelatedArtistsAdapter.OnItemClickListener {
    private val viewModel : DetailedViewModel by viewModels()
    private val args : DetailedArtistFragmentArgs by navArgs()

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
        init(args.detailedArtistFragmentModel.id,args.detailedArtistFragmentModel.name)
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
        viewModel.getArtistsTopTracks(id, getLocation(requireContext())!!)
        viewModel.artistsTopTracks.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> topTracksAdapter.setList(state.data.tracks!!)
                DataState.Empty -> showToast("Error : No data")
                is DataState.Fail -> showToast("Error : ${state.e.localizedMessage}")
                DataState.Loading -> {}
            }
        }
        viewModel.getArtistsAlbums(id,market)
        viewModel.artistsAlbums.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> albumsAdapter.setList(state.data.items!!.toList())
                DataState.Empty -> showToast("Error : No data")
                is DataState.Fail -> showToast("Error : ${state.e.localizedMessage}")
                DataState.Loading -> {}
            }
        }
        viewModel.getRelatedArtists(id,market)
        viewModel.relatedArtists.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> relatedArtistsAdapter.setList(state.data.artists!!)
                DataState.Empty -> showToast("Error : No data")
                is DataState.Fail -> showToast("Error : ${state.e.localizedMessage}")
                DataState.Loading -> {}
            }
        }
    }

    private fun init(id:String, name:String){

        detailedToolbar.setNavigationOnClickListener {
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
                collapsingToolbarArtist.title = name
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
                Toast.makeText(requireContext(), "Error: ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClicked(item: ArtistAlbumsItem) {
        TODO("Not yet implemented")
    }

    override fun onItemClicked(item: ArtistTopTracksTrack) {
        TODO("Not yet implemented")
    }

    override fun onItemClicked(item: RelatedArtistsArtist) {
        TODO("Not yet implemented")
    }
}