package com.uhi5d.spotibud.presentation.ui.home.profile

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.databinding.FragmentProfileBinding
import com.uhi5d.spotibud.domain.model.currentuser.CurrentUser
import com.uhi5d.spotibud.domain.model.devices.Devices
import com.uhi5d.spotibud.util.DataState
import com.uhi5d.spotibud.util.showIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val TAG = "Profile Fragment"
    private val viewModel : ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfileInfo()
        viewModel.profileInfo.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> {setUser(state.data)
                initCollapsingToolbar(state.data)}
                DataState.Empty -> TODO()
                is DataState.Fail -> TODO()
                DataState.Loading -> TODO()
            }
        }
        viewModel.getAvailableDevices()
        viewModel.devices.observe(viewLifecycleOwner){
            binding.shimmerLayout.showIf {_ -> it is DataState.Loading }
            when(it){
                is DataState.Success -> {generateAvailableDevices(it.data)}
                DataState.Empty -> TODO()
                is DataState.Fail -> TODO()
                DataState.Loading -> TODO()
            }
        }

    }

    private fun generateAvailableDevices(devices: Devices) {
        val adapter = AvailableDeviceAdapter(requireContext())
        adapter.setDevices(devices)
        val layoutManager = LinearLayoutManager(requireContext())
        with(binding) {
            recyclerDevices.layoutManager = layoutManager
            recyclerDevices.adapter = adapter
        }
    }
    private fun initCollapsingToolbar(user: CurrentUser){
        detailedToolbarProfile.setNavigationOnClickListener {
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
                collapsingToolbarArtist.title = user.displayName
                isShow = true
            } else if (isShow){
                collapsingToolbarArtist.title = " "
                isShow = false
            }})
        /////////////////////////////////////////////////////////////////////////////////////
    }
    private fun setUser(user: CurrentUser){
        with(user){
            Picasso.get()
                .load(images?.get(0)?.url)
                .noFade()
                .into(ivProfilePhoto)

            tvParallaxHeaderProfile.text = displayName
            tvId.text = id
            tvEmail.text = email
            tvFollowers.text = followers?.total.toString()
            tvCountry.text = country
            tvAccountStatus.text = product
        }

        fabProfile.setOnClickListener {
            try {
                val uri = Uri.parse("http://open.spotify.com/user/${id}")
                val intent = Intent(Intent.ACTION_VIEW,uri)
                startActivity(intent)
            }catch (ex: ActivityNotFoundException){
                Toast.makeText(requireContext(), "Error: ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}