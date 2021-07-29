package com.uhi5d.spotibud.presentation.ui.detailed.album

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.uhi5d.spotibud.databinding.FragmentDetailedAlbumBinding
import com.uhi5d.spotibud.domain.model.album.Album
import com.uhi5d.spotibud.domain.model.albumstracks.AlbumsTracksItem
import com.uhi5d.spotibud.presentation.ui.detailed.ALBUM_URI
import com.uhi5d.spotibud.presentation.ui.detailed.DetailedViewModel
import com.uhi5d.spotibud.presentation.ui.detailed.track.toDetailedTrackFragmentModel
import com.uhi5d.spotibud.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detailed_album.*

@AndroidEntryPoint
class DetailedAlbumFragment : Fragment(),
    DetailedAlbumTracksAdapter.OnItemClickListener {
    private val TAG = "DetailedAlbum Fragment"

    private val viewModel : DetailedViewModel by viewModels()
    private val args: DetailedAlbumFragmentArgs by navArgs()

    private lateinit var albumTracksAdapter: DetailedAlbumTracksAdapter
    private lateinit var crAdapter: DetailedAlbumCopyrightsAdapter

    private var _binding : FragmentDetailedAlbumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedAlbumBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumTracksAdapter = DetailedAlbumTracksAdapter(requireContext(),this)
        crAdapter = DetailedAlbumCopyrightsAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerAlbumTracks){
            adapter = albumTracksAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(CustomItemDecoration(DEFAULT_MARGIN))
        }
        with(binding.recyclerCopyright){
            adapter = crAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(CustomItemDecoration(0))
        }
        viewModel.token.observe(viewLifecycleOwner){
            if (it.length > 10){
                viewModel.getAlbum(it,args.detailedAlbumFragmentModel.id)
                viewModel.getAlbumsTracks(it,args.detailedAlbumFragmentModel.id)
            }
        }

        viewModel.albumTracks.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> {albumTracksAdapter.setAlbumTracks(state.data.items!!) }

            }
        }
        viewModel.album.observe(viewLifecycleOwner){ state ->
            binding.success.showIf { state is DataState.Success }
            when(state){
                is DataState.Success -> {init(state.data)
                    state.data.copyrights?.let { crAdapter.setList(it) }
                    binding.shimmer.hide()}

            }
        }
    }

    private fun init(album:Album){
        with(binding){
            iv.loadWithPicasso(album.images!![0].url!!)
            tvAlbumName.text = album.name
            dtRatingBar.rating = (album.popularity!!.toFloat()/20)
            tvRelease.text = album.releaseDate
            tvLabel.text = album.label
        }

        binding.detailedAlbumToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //collapsing toolbar design section
        var isShow = true
        var scrollRange = -1
        binding.appBarDetailed.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                appBarLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = appBarLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                binding.collapsingToolbar.title = album.name
                isShow = true
            } else if (isShow){
                binding.collapsingToolbar.title = " "
                isShow = false
            }})


        fabDetailedAlbum.setOnClickListener {
            try {

                val uri = Uri.parse("$ALBUM_URI${id}")
                val intent = Intent(Intent.ACTION_VIEW,uri)
                startActivity(intent)
            }catch (ex: ActivityNotFoundException){
                showToast("Error : ${ex.localizedMessage}")
            }
        }
    }

    override fun onItemClicked(item: AlbumsTracksItem) {
        val action = DetailedAlbumFragmentDirections.actionDetailedAlbumFragmentToDetailedTrackFragment(
            item.toDetailedTrackFragmentModel()
        )
        findNavController().navigate(action)
    }
}