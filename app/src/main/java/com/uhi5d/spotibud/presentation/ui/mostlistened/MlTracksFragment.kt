package com.uhi5d.spotibud.presentation.ui.mostlistened

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhi5d.spotibud.databinding.FragmentMlTracksBinding
import com.uhi5d.spotibud.domain.model.MyArtistsItem
import com.uhi5d.spotibud.domain.model.mytracks.MyTracksItem
import com.uhi5d.spotibud.util.hide
import com.uhi5d.spotibud.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MlTracksFragment : Fragment(),
    MostListenedAdapter.OnItemClickListener {

    private val viewModel: MostListenedViewModel by viewModels()
    private var _binding: FragmentMlTracksBinding? = null
    private val binding get() =  _binding!!

    private var list : MutableMap<String,List<MyTracksItem>>? = null
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
       _binding = FragmentMlTracksBinding.inflate(inflater,container,false)
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
            recycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            recycler.addItemDecoration(MlItemDecoration(margin))
        }
        binding.rg.setOnCheckedChangeListener(changedListener)
        for(i in TimeRange.values()){
            viewModel.getMyTracks(i.str)
        }
        viewModel.myTracks.observe(viewLifecycleOwner){
            binding.shimmerLayout.hide()
            binding.recycler.show()
            list = it
        }
    }

    private val changedListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        when(group.checkedRadioButtonId){
            binding.rb1.id -> {mostListenedAdapter.setTracksList(
                list?.get(TimeRange.SHORT.str)!!
            )
                checkedRadioButton = 0}
            binding.rb2.id -> {mostListenedAdapter.setTracksList(
                list?.get(TimeRange.MEDIUM.str)!!
            )
                checkedRadioButton = 1}
            binding.rb3.id -> {mostListenedAdapter.setTracksList(
                list?.get(TimeRange.LONG.str)!!
            )
                checkedRadioButton = 2}
        }
    }

    override fun OnTrackItemClicked(item: MyTracksItem) {
        TODO("Not yet implemented")
    }

    override fun OnArtistItemClicked(item: MyArtistsItem) {
        TODO("Not yet implemented")
    }

}