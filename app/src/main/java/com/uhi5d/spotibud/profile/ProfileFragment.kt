package com.uhi5d.spotibud.profile

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.main.MainViewModel
import com.uhi5d.spotibud.model.Device
import com.uhi5d.spotibud.model.Devices
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(), AvailableDeviceAdapter.OnItemClickListener {
    private val TAG = "Profile Fragment"
    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        val factory = CustomViewModelFactory(this,requireContext())
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)
        viewModel.availableDevices.observe(viewLifecycleOwner,
            {t -> generateAvailableDevices(t!!)})
        viewModel.getAvailableDevices(requireContext(),token!!)

        val bundle = requireArguments()
        initCollapsingToolbar(bundle)
        setUser(bundle)



    }

    private fun generateAvailableDevices(devices: Devices){
        val adapter = AvailableDeviceAdapter(requireContext(),devices,this)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerDevices.layoutManager = layoutManager
        recyclerDevices.adapter = adapter
    }
    private fun initCollapsingToolbar(bundle: Bundle){
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
                collapsingToolbarArtist.title = bundle.getString("userDisplayName")
                isShow = true
            } else if (isShow){
                collapsingToolbarArtist.title = " "
                isShow = false
            }})
        /////////////////////////////////////////////////////////////////////////////////////
    }
    private fun setUser(bundle: Bundle){
        val image = bundle.getString("userImage")
        val id = bundle.getString("userId")
        val displayName = bundle.getString("userDisplayName")
        val email = bundle.getString("userEmail")
        val followers = bundle.getString("userFollowers")
        val country = bundle.getString("userCountry")
        val accountStatus = bundle.getString("userAccountStatus")

        Picasso.get()
            .load(image)
            .noFade()
            .into(ivProfilePhoto)

        tvParallaxHeaderProfile.text = displayName
        tvId.text = id
        tvEmail.text = email
        tvFollowers.text = followers
        tvCountry.text = country
        tvAccountStatus.text = accountStatus

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

    override fun onItemClicked(device: Device) {

    }

}