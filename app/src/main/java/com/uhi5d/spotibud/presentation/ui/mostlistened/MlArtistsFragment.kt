package com.uhi5d.spotibud.presentation.ui.mostlistened

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhi5d.spotibud.databinding.FragmentMlArtistsBinding
import com.uhi5d.spotibud.domain.model.MyArtistsItem
import com.uhi5d.spotibud.domain.model.mytracks.MyTracksItem
import com.uhi5d.spotibud.presentation.ui.detailed.artist.toDetailedArtistFragmentModel
import com.uhi5d.spotibud.util.DataState
import com.uhi5d.spotibud.util.hide
import com.uhi5d.spotibud.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MlArtistsFragment : Fragment(),
    MostListenedAdapter.OnItemClickListener {

    private val viewModel: MostListenedViewModel by viewModels()
    private var _binding: FragmentMlArtistsBinding? = null
    private val binding get() =  _binding!!

    private lateinit var shortList: List<MyArtistsItem>
    private lateinit var mediumList: List<MyArtistsItem>
    private lateinit var longList: List<MyArtistsItem>

    private var checkedRadioButton = 0
    private lateinit var mostListenedAdapter: MostListenedAdapter
    private val margin = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mostListenedAdapter = MostListenedAdapter(requireContext(),this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMlArtistsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            recycler.adapter = mostListenedAdapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
            recycler.addItemDecoration(MlItemDecoration(margin))
            rg.setOnCheckedChangeListener(changedListener)
        }

        viewModel.myArtistsShort.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Success -> {
                    shortList = state.data.items!!
                    setScreenToSuccess()
                }
                is DataState.Fail -> {
                }
            }
        }
        viewModel.myArtistsMedium.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Success -> {
                    mediumList = state.data.items!!
                    setScreenToSuccess()
                }
                is DataState.Fail -> {
                }
            }
        }
        viewModel.myArtistsLong.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Success -> {
                    longList = state.data.items!!
                    setScreenToSuccess()
                }
                is DataState.Fail -> {
                }
            }
        }
    }

    private val changedListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        when(checkedId){
            binding.rb1.id -> {
                if (this::shortList.isInitialized) {
                    mostListenedAdapter.setArtistsList(shortList)
                    checkedRadioButton = 0
                }
            }
            binding.rb2.id -> {
                if (this::mediumList.isInitialized) {
                    mostListenedAdapter.setArtistsList(mediumList)
                    checkedRadioButton = 1
                }
            }
            binding.rb3.id -> {
                if (this::longList.isInitialized) {
                    mostListenedAdapter.setArtistsList(longList)
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
    }

    override fun onArtistItemClicked(item: MyArtistsItem) {
        val action = MlArtistsFragmentDirections.actionMlArtistsFragmentToDetailedArtistFragment(
            item.toDetailedArtistFragmentModel()
        )
        findNavController().navigate(action)
    }

}