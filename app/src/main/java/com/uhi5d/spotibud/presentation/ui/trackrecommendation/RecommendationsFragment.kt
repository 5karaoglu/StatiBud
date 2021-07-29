package com.uhi5d.spotibud.presentation.ui.trackrecommendation

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.application.ToastHelper
import com.uhi5d.spotibud.databinding.FragmentRecommendationsBinding
import com.uhi5d.spotibud.databinding.TrResultsBinding
import com.uhi5d.spotibud.databinding.TrackSearchBinding
import com.uhi5d.spotibud.domain.model.recommendations.RecommendationsTrack
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsTracksItem
import com.uhi5d.spotibud.presentation.ui.detailed.track.toDetailedTrackFragmentModel
import com.uhi5d.spotibud.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecommendationsFragment : Fragment(),
GenreAdapter.OnItemClickListener,
TrSearchResultsAdapter.OnItemClickListener,
RecoAdapter.OnItemClickListener{
    private var TAG = "TrackFinder Fragment"
    private var _binding: FragmentRecommendationsBinding? = null
    val binding: FragmentRecommendationsBinding
        get() = _binding!!

    @Inject
    lateinit var toastHelper: ToastHelper
    private lateinit var alertBinding: TrackSearchBinding
    private lateinit var dialog: AlertDialog

    private lateinit var recoAlertBinding: TrResultsBinding
    private lateinit var recoResultsDialog: AlertDialog

    private lateinit var token: String
    private lateinit var recoList: List<RecommendationsTrack>

    private val viewModel: RecommendationsViewModel by viewModels()

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var srAdapter: TrSearchResultsAdapter
    private lateinit var recoAdapter: RecoAdapter

    private val margin = 5
    private var track : SearchResultsTracksItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreAdapter = GenreAdapter(requireContext(),this)
        srAdapter = TrSearchResultsAdapter(requireContext(),this)
        recoAdapter = RecoAdapter(requireContext(),this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerGenre){
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(CustomItemDecoration(margin))
        }

       with(binding.clear){
           showIf { track != null }
           setOnClickListener(clearListener)
       }
        binding.trackLayout.setOnClickListener(trackListener)
        binding.buttonFind.setOnClickListener(findButton)

        viewModel.token.observe(viewLifecycleOwner){
            if (it.length > 10){
                token = it
                viewModel.getGenres(it)
            }
        }

        viewModel.genres.observe(viewLifecycleOwner){ state ->
            binding.recyclerGenre.showIf { state is DataState.Success }
            when(state){
                is DataState.Success -> {
                    genreAdapter.setList(state.data.genres)
                    binding.genreShimmer.hide()
                }
            }
        }
        viewModel.searchResults.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                srAdapter.setList(it)
            }
        }
        viewModel.recommendations.observe(viewLifecycleOwner){
            when(it){
                is DataState.Success -> {
                    recoAdapter.setList(it.data.tracks!!)
                    recoList = it.data.tracks
                    setRecoDialog()
                    recoResultsDialog.show()
                }
            }
        }
        setAlertDialog()
        recoAlertBinding.buttonCancel.setOnClickListener { recoResultsDialog.dismiss() }
    }

    override fun onItemClicked(genre: String) {
        genreAdapter.setSelectedList(genre)
    }

    private val clearListener = View.OnClickListener {
        binding.ivTfSelected.setImageResource(R.drawable.ic_baseline_not_interested_24)
        binding.tvTfSelected.text = getString(R.string.reco_selected_track)
        binding.tvTfSelectedArtist.text = getString(R.string.reco_selected_artist)
        track = null
    }
    private val trackListener = View.OnClickListener {
        if (this::dialog.isInitialized){
            dialog.show()
        }
    }
    private val findButton = View.OnClickListener {
        val list = genreAdapter.getSelectedList()
        with(binding){
            if(track != null && list.isNotEmpty()){
                viewModel.getRecommendations(
                    token, track!!.id!!,list.toSeed(),
                    (tfSbAcoustic.progress/1000).toString(),
                    (tfSbDance.progress/1000).toString(),
                    (tfSbEnergy.progress/1000).toString(),
                    (tfSbInstrumental.progress/1000).toString(),
                    (tfSbLive.progress/1000).toString(),
                    (tfSbValence.progress/1000).toString(),
                    getLocation(requireContext()).toString()
                )
            }else{
                toastHelper.sendToast(getString(R.string.tr_find_button_error))
            }
        }
    }

    private fun setAlertDialog(){
        if (track == null){
            if (!this::alertBinding.isInitialized){
                alertBinding = TrackSearchBinding.inflate(LayoutInflater.from(requireContext()))
            }

            dialog = AlertDialog.Builder(requireContext())
                .setView(alertBinding.root)
                .setCancelable(false)
                .create()
            alertBinding.buttonCancel.setOnClickListener { dialog.dismiss() }
            alertBinding.etSearchTrack.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s!!.isNotBlank() && this@RecommendationsFragment::token.isInitialized){
                        viewModel.search(token,s.toString())
                    }
                }

            })

            with(alertBinding.recyclerTfSearchTrack){
                adapter = srAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(CustomItemDecoration(margin))
            }

        }
    }
    private fun setRecoDialog(){
        if (this::recoList.isInitialized){
            if (!this::recoAlertBinding.isInitialized){
                recoAlertBinding = TrResultsBinding.inflate(LayoutInflater.from(requireContext()))
            }

            recoResultsDialog = AlertDialog.Builder(requireContext())
                .setView(recoAlertBinding.root)
                .setCancelable(false)
                .create()
            recoAlertBinding.buttonCancel.setOnClickListener { dialog.dismiss() }

            with(recoAlertBinding.recycler){
                adapter = recoAdapter
                layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL,false)
                addItemDecoration(CustomItemDecoration(margin))
            }
    }}
    private fun setTrack(track: SearchResultsTracksItem){
       with(binding){
           if (track.album?.images!!.isNotEmpty()){
               binding.ivTfSelected.loadWithPicasso(track.album.images[0].url)
               tvTfSelected.text = track.name
               tvTfSelectedArtist.text = track.artists!![0].name
           }
       }

    }

    override fun onItemClicked(searchResultsTracksItem: SearchResultsTracksItem) {
        track = searchResultsTracksItem
        setTrack(track!!)
        dialog.dismiss()
    }

    override fun onItemClicked(item: RecommendationsTrack) {
        val action = RecommendationsFragmentDirections.actionRecommendationsFragmentToDetailedTrackFragment(
            item.toDetailedTrackFragmentModel()
        )
        recoResultsDialog.dismiss()
        findNavController().navigate(action)
    }
}



/*    private fun helpButtonInit(){
        tfIvHelp.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            .setCancelable(false)

            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.help_dialog, null)
            dialogBuilder.setView(dialogView)
            val buttonGotIt: Button = dialogView.findViewById<View>(R.id.buttonGotIt) as Button
            val alertDialog = dialogBuilder.create()
            buttonGotIt.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }*/



