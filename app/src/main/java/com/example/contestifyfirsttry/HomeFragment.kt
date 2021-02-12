package com.example.contestifyfirsttry

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.model.RecentTracks
import com.example.contestifyfirsttry.model.User
import com.example.contestifyfirsttry.recent.RecentTracksAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.ivProfilePhoto
import kotlinx.android.synthetic.main.fragment_profile.tvCountry
import kotlinx.android.synthetic.main.fragment_profile.tvDisplayName
import kotlinx.android.synthetic.main.fragment_profile.tvEmail
import kotlinx.android.synthetic.main.fragment_profile.tvFollowers
import kotlinx.android.synthetic.main.fragment_profile.tvId


class HomeFragment : Fragment() {
    private var TAG = "Home Fragment"

    private var viewModel: MainViewModel? = null
    private var token:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //disabling onbackpressed
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
        callback.isEnabled = true
        token = getToken()
        viewModel = MainViewModel(this)
        getUserInfo(token!!)
        recentInıt()
    }

    private fun getToken():String{
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        Log.d(TAG, "onViewCreated:$token ")
        return token!!
    }
    private fun getUserInfo(token: String){
        var user: User? = null
        viewModel!!.user.observe(requireActivity(),
            Observer<User> { t -> setUser(t) })

        viewModel!!.getUser(token)

    }
    private fun setUser(user: User){
        tvHomeHeader.text = String.format(getString(R.string.welcome_header),user.display_name)

        Picasso.get()
            .load(user.images[0].url)
            .noFade()
            .into(ivProfilePhoto)

        tvId.text = user.id
        tvDisplayName.text = user.display_name
        tvEmail.text = user.email
        tvFollowers.text = user.followers.total.toString()
        tvCountry.text = user.country
    }
    private fun recentInıt(){
        viewModel!!.recentTracks.observe(viewLifecycleOwner,
            Observer<RecentTracks> { t ->
                generateDataRecentTracks(t!!,5)
                recyclerButtonClick(t!!)})

        viewModel!!.getRecentTracks(token!!)
    }
    private fun generateDataRecentTracks(tracks: RecentTracks,customItemCount:Int){
        var adapter : RecentTracksAdapter = RecentTracksAdapter(requireContext(),tracks,viewModel!!,customItemCount)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerRecentHome.layoutManager = layoutManager
        recyclerRecentHome.adapter = adapter
    }
    private fun recyclerButtonClick(tracks: RecentTracks){
        var size = 0
        buttonRecyclerExpand.setOnClickListener {
            if (size == 0){
                generateDataRecentTracks(tracks,tracks.limit)
                buttonRecyclerExpand.text = getString(R.string.show_less)
                size = 1
            }else{
                generateDataRecentTracks(tracks,5)
                buttonRecyclerExpand.text = getString(R.string.show_more)
                size = 0
            }

        }
    }
}