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
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.databinding.FragmentRecommendationsBinding
import com.uhi5d.spotibud.databinding.TrackSearchBinding
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsTracksItem
import com.uhi5d.spotibud.util.showIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationsFragment : Fragment(),
GenreAdapter.OnItemClickListener{
    private var TAG = "TrackFinder Fragment"
    private var _binding: FragmentRecommendationsBinding? = null
    val binding: FragmentRecommendationsBinding
        get() = _binding!!

    private val viewModel: RecommendationsViewModel by viewModels()

    private lateinit var genreAdapter: GenreAdapter
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerGenre){
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(GenreItemDecoration(margin))
        }
       with(binding.clear){
           showIf { track != null }
           setOnClickListener(clearListener)
       }
        binding.trackLayout.setOnClickListener(trackListener)
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
    val trackListener = View.OnClickListener {
        if (track == null){
            val binding = TrackSearchBinding.inflate(LayoutInflater.from(requireContext()))

            val dialog = AlertDialog.Builder(requireContext())
                .setView(binding.root)
                .setCancelable(false)
                .create()
            binding.buttonCancel.setOnClickListener { dialog.dismiss() }
            binding.etSearchTrack.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s!!.isNotBlank()){
                        viewModel.search(s.toString())
                    }
                }

            })
        }
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



