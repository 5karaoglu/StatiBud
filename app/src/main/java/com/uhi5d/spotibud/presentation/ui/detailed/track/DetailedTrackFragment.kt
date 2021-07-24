package com.uhi5d.spotibud.presentation.ui.detailed.track

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
import com.uhi5d.spotibud.Functions
import com.uhi5d.spotibud.databinding.FragmentDetailedTrackBinding
import com.uhi5d.spotibud.domain.model.artist.Artist
import com.uhi5d.spotibud.domain.model.track.Track
import com.uhi5d.spotibud.domain.model.trackaudiofeatures.TrackAudioFeatures
import com.uhi5d.spotibud.presentation.ui.detailed.DetailedViewModel
import com.uhi5d.spotibud.presentation.ui.detailed.TRACK_URI
import com.uhi5d.spotibud.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedTrackFragment : Fragment(),
    DetailedTrackArtistAdapter.OnItemClickListener {
    private val TAG = "DetailedTrack Fragment"
    private val viewModel : DetailedViewModel by viewModels()
    private val args: DetailedTrackFragmentArgs by navArgs()

    private lateinit var artistAdapter: DetailedTrackArtistAdapter
    private lateinit var track: Track

    private var _binding: FragmentDetailedTrackBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedTrackBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistAdapter = DetailedTrackArtistAdapter(requireContext(),this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerTrackArtist) {
            adapter = artistAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(CustomItemDecoration(DEFAULT_MARGIN))
        }
        viewModel.getTracksAudioFeatures(args.detailedTrackFragmentModel.id)
        viewModel.audioFeatures.observe(viewLifecycleOwner) { state ->
            when(state) {
                is DataState.Success -> {generateAudioFeature(state.data)}
                DataState.Empty -> TODO()
                is DataState.Fail -> TODO()
                DataState.Loading -> TODO()
            }
        }
        viewModel.getTrack(args.detailedTrackFragmentModel.id)
        viewModel.track.observe(viewLifecycleOwner) { state ->
            when(state) {
                is DataState.Success -> {track = state.data}
                DataState.Empty -> TODO()
                is DataState.Fail -> TODO()
                DataState.Loading -> TODO()
            }
        }
        viewModel.getArtists(track.artists!!.toQuery())
            viewModel.artists.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is DataState.Success -> {
                        artistAdapter.setArtistList(state.data.artists!!)
                    }
                    DataState.Empty -> TODO()
                    is DataState.Fail -> TODO()
                    DataState.Loading -> TODO()
                }
            }


    }

    private fun generateAudioFeature(trackAudioFeatures: TrackAudioFeatures){
        with(binding){
            tvDuration.text = trackAudioFeatures.durationMs?.let { Functions().msToMin(it) }
            tvTempo.text = String.format("%d bpm", trackAudioFeatures.tempo?.toInt())
            tvKey.text = String.format("%s%s",
                trackAudioFeatures.key?.let { Functions().trackKey(it) },
                trackAudioFeatures.mode?.let { Functions().trackMode(it) })

            audio.pbDanceability.progress = (trackAudioFeatures.danceability?.times(100))!!.toInt()
            audio.pbEnergy.progress = (trackAudioFeatures.energy?.times(100))!!.toInt()
            audio.pbSpeechiness.progress = (trackAudioFeatures.speechiness?.times(100))!!.toInt()
            audio.pbAcousticness.progress = (trackAudioFeatures.acousticness?.times(100))!!.toInt()
            audio.pbInstrumentalness.progress = (trackAudioFeatures.instrumentalness?.times(100))!!.toInt()
            audio.pbLiveness.progress = (trackAudioFeatures.liveness?.times(100))!!.toInt()
            audio.pbValence.progress = (trackAudioFeatures.valence?.times(100))!!.toInt()
        }
    }
    //getting all artist ids for multiple search
    private fun init(name:String, image:String) {
        with(binding) {
            iv.loadWithPicasso(image)

            detailedToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            //collapsing toolbar design section
            var isShow = true
            var scrollRange = -1
            appBarDetailed.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                    appBarLayout, verticalOffset ->
                if (scrollRange == -1){
                    scrollRange = appBarLayout?.totalScrollRange!!
                }
                if (scrollRange + verticalOffset == 0){
                    collapsingToolbar.title = name
                    isShow = true
                } else if (isShow){
                    collapsingToolbar.title = " "
                    isShow = false
                }})
            /////////////////////////////////////////////////////////////////////////////////////

            detailedToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            fabDetailedTrack.setOnClickListener {
                try {

                    val uri = Uri.parse("$TRACK_URI${track.id}")
                    val intent = Intent(Intent.ACTION_VIEW,uri)
                    startActivity(intent)
                }catch (ex:ActivityNotFoundException){
                    showToast("Error: ${ex.message}")
                }
            }
            dtRatingBar.rating = track.popularity!!.toFloat()/20

            imageViewAlbum.loadWithPicasso(track.album?.images?.get(0)?.url.toString())
            textViewAlbumName.text = track.album!!.name
            textViewAlbumArtist.text = track.artists!![0].name

            imageViewAlbum.setOnClickListener {

            }
        }
        }

    override fun onItemClicked(item: Artist) {

    }
}
