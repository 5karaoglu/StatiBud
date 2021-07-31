package com.uhi5d.spotibud.presentation.ui.mostlistened

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.application.ToastHelper
import com.uhi5d.spotibud.databinding.FragmentMlTracksBinding
import com.uhi5d.spotibud.domain.model.MyArtistsItem
import com.uhi5d.spotibud.domain.model.mytracks.MyTracksItem
import com.uhi5d.spotibud.presentation.ui.detailed.track.toDetailedTrackFragmentModel
import com.uhi5d.spotibud.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MlTracksFragment : Fragment(),
    MostListenedAdapter.OnItemClickListener {

    private val viewModel: MostListenedViewModel by viewModels()
    private var _binding: FragmentMlTracksBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var toastHelper: ToastHelper

    private lateinit var shortList: List<MyTracksItem>
    private lateinit var mediumList: List<MyTracksItem>
    private lateinit var longList: List<MyTracksItem>

    private var checkedRadioButton = 0
    private lateinit var mostListenedAdapter: MostListenedAdapter
    private val margin = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mostListenedAdapter = MostListenedAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMlTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recycler.adapter = mostListenedAdapter
            recycler.layoutManager =
                LinearLayoutManager(requireContext())
            recycler.addItemDecoration(CustomItemDecoration(DEFAULT_MARGIN))
            radioGroup.setOnCheckedChangeListener (changedListener())
        }

        viewModel.myTracksShort.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Success -> {
                    shortList = state.data.items!!
                    if (checkedRadioButton == 0){
                        mostListenedAdapter.setTracksList(shortList)
                    }
                    setScreenToSuccess()
                }
                is DataState.Fail -> {
                    toastHelper.errorMessage(state.e.message!!)
                }
            }
        }
        viewModel.myTracksMedium.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Success -> {
                    mediumList = state.data.items!!
                    setScreenToSuccess()
                }
                is DataState.Fail -> {
                    toastHelper.errorMessage(state.e.message!!)
                }
            }
        }
        viewModel.myTracksLong.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Success -> {
                    longList = state.data.items!!
                    setScreenToSuccess()
                }
                is DataState.Fail -> {
                    toastHelper.errorMessage(state.e.message!!)
                }
            }
        }

    }

    private fun changedListener() = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        Log.d(TAG, "$checkedId:")
        when (checkedId) {
            R.id.rb1 -> {
                if (this::shortList.isInitialized) {
                    mostListenedAdapter.setTracksList(shortList)
                    checkedRadioButton = 0
                }
            }
            R.id.rb2  -> {
                if (this::mediumList.isInitialized) {
                    mostListenedAdapter.setTracksList(mediumList)
                    checkedRadioButton = 1
                }
            }
            R.id.rb3 -> {
                if (this::longList.isInitialized) {
                    mostListenedAdapter.setTracksList(longList)
                    checkedRadioButton = 2
                }
            }
        }
    }

    private fun setScreenToSuccess() {
        with(binding) {
            if (shimmer.visibility == View.VISIBLE &&
                success.visibility == View.GONE) {
                binding.shimmer.hide()
                binding.success.show()
            }
        }
    }

    override fun onTrackItemClicked(item: MyTracksItem) {
        val action = MostListenedFragmentDirections.actionMlFragmentToDetailedTrackFragment(
            item.toDetailedTrackFragmentModel()
        )
        findNavController().navigate(action)
    }

    override fun onArtistItemClicked(item: MyArtistsItem) {}

}