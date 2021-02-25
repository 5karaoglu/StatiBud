package com.uhi5d.spotibud.home

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.*
import com.uhi5d.spotibud.main.MainViewModel
import com.uhi5d.spotibud.model.*
import com.uhi5d.spotibud.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(),
    RecommendationsAdapter.OnItemClickListener,
    RecentTracksAdapter.OnItemClickListener,
    TrackFinderAdapter.OnItemClickListener{
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

        initExit()
        initHomeTfWarn()
        initVisibility()
            //getting token
            val sharedPreferences = requireActivity().getSharedPreferences(
                "spotifystatsapp",
                Context.MODE_PRIVATE
            )
            token = sharedPreferences.getString("token", "")
            Log.d(TAG, "onViewCreated:$token ")
        // ViewModel components
        var factory = CustomViewModelFactory(this, requireContext())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        initRecentTracks()
        initUserInfo()
        checkTrackFinderTracks()
        getRecommendations(token!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liRecommendationPb.visibility = View.VISIBLE
        recyclerRecommendation.visibility = View.GONE
    }

    private fun initExit(){
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.dialog_text)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_accept
                ) { dialog, which -> requireActivity().finish() }
                .setNegativeButton(R.string.dialog_deny,null)
                .show()
        }
        callback.isEnabled = true
    }
    private fun initUserInfo(){
        viewModel!!.user.observe(requireActivity(),
            Observer<User> { t -> profileInit(t!!) })
        Thread{
            viewModel!!.getUser(token!!)
        }.start()

    }

    private fun initVisibility(){
        liRecommendationPb.visibility = View.VISIBLE
        recyclerRecommendation.visibility = View.GONE
        liRecentPb.visibility = View.VISIBLE
        coLayoutRecent.visibility = View.GONE
    }
    private fun profileInit(user: User){
        tvHomeHeader.text = String.format(getString(R.string.welcome_header), user.display_name)

        buttonProfile.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("userImage", user.images[0].url)
            bundle.putString("userId", user.id)
            bundle.putString("userDisplayName", user.display_name)
            bundle.putString("userEmail", user.email)
            bundle.putString("userFollowers", user.followers.total.toString())
            bundle.putString("userCountry", user.country)
            bundle.putString("userAccountStatus", user.product)
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment, bundle)
        }
    }


    private fun initRecentTracks(){
        viewModel!!.recentTracks.observe(requireActivity(),
            Observer<RecentTracks> { t ->
                generateDataRecentTracks(t!!, 5)
                recyclerButtonClick(t!!)
            })
        Thread{
            viewModel!!.getRecentTracks(token!!)
        }.start()

    }
    private fun generateDataRecentTracks(tracks: RecentTracks, customItemCount: Int){
        liRecentPb.visibility = View.GONE
        coLayoutRecent.visibility = View.VISIBLE

        var adapter : RecentTracksAdapter = RecentTracksAdapter(
            requireContext(),
            tracks,
            this,
            customItemCount
        )
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerRecentHome.layoutManager = layoutManager
        recyclerRecentHome.adapter = adapter
    }
    private fun recyclerButtonClick(tracks: RecentTracks){
        var size = 0
        buttonRecyclerExpand.setOnClickListener {
            if (size == 0){
                generateDataRecentTracks(tracks, tracks.limit)
                buttonRecyclerExpand.text = getString(R.string.show_less)
                size = 1
            }else{
                generateDataRecentTracks(tracks, 5)
                buttonRecyclerExpand.text = getString(R.string.show_more)
                size = 0
            }

        }
    }
    private fun getRecommendations(token: String){
        var aS : String? = null
        var tS : String? = null
        viewModel!!.artistsListShortTerm.observe(viewLifecycleOwner,
            Observer<Artists> { t ->
               if (aS == null){
                   aS = artistSeed(t!!)
                   Thread{
                       viewModel!!.getMyTracksLimited(token, "short_term", 2)
                   }.start()
               }
            })
        viewModel!!.tracksListShortTerm.observe(viewLifecycleOwner,
            Observer<Tracks> { t ->
                if (tS == null){
                    tS = trackSeed(t!!)
                    Thread{
                        viewModel!!.getRecommendations(token, aS!!, tS!!)
                    }.start()
                }
            })
        viewModel!!.recommendations.observe(viewLifecycleOwner,
            Observer<Recommendations> { t ->
                generateRecommendations(t!!)
            })
        Thread{
            viewModel!!.getMyArtistsLimited(token!!, "short_term", 2)
        }.start()
        }
    private fun generateRecommendations(recommendations: Recommendations){
        liRecommendationPb.visibility = View.GONE
        recyclerRecommendation.visibility = View.VISIBLE

        var adapter = RecommendationsAdapter(requireContext(), recommendations, this)
        var layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerRecommendation.layoutManager = layoutManager
        recyclerRecommendation.adapter = adapter
    }
    //recommendations onclick
    override fun onItemClicked(recommendationTrack: RecommendationTrack) {
        val bundle = Bundle()
        bundle.putString("id", recommendationTrack.id)
        bundle.putString("artistId", recommendationTrack.album.artists[0].id)
        bundle.putString("name", recommendationTrack.name)
        bundle.putString("image", recommendationTrack.album.images[0].url)
        findNavController().navigate(R.id.action_homeFragment_to_detailedTrackFragment, bundle)
    }
    private fun artistSeed(artists: Artists): String {
        var list = ""
        for (i in artists.items){
            list = if (list == ""){
                i.id
            }else{
                "$list,${i.id}"
            }
        }
        Log.d(TAG, "artistSeed:$list ")
        return  list
    }
    private fun trackSeed(tracks: Tracks): String {
        var list = ""
        for (i in tracks.items){
            list = if (list == ""){
                i.id
            }else{
                "$list,${i.id}"
            }
        }
        Log.d(TAG, "trackSeed:$list ")
        return  list
    }
        //recent tracks onclick
    override fun onItemClicked(recentTrack: Items) {
            val bundle = Bundle()
            bundle.putString("id", recentTrack.track.id)
            bundle.putString("artistId", recentTrack.track.album.artists[0].id)
            bundle.putString("name", recentTrack.track.name)
            bundle.putString("image", recentTrack.track.album.images[0].url)
            findNavController().navigate(R.id.action_homeFragment_to_detailedTrackFragment, bundle)
    }
    private fun checkTrackFinderTracks(){
        viewModel!!.trackFinderTracks.observe(viewLifecycleOwner,
            { trackFinderTracks ->
                if (!trackFinderTracks.isNullOrEmpty()) {
                    Log.d(TAG, "checkTrackFinderTracks: ${trackFinderTracks[0].trackName}")
                    buttonClearList.visibility = View.VISIBLE
                    reLayoutHomeTf.visibility = View.VISIBLE
                    liHomeTfWarn.visibility = View.GONE
                    recyclerHomeTf.visibility=View.VISIBLE

                    val adapter = TrackFinderAdapter(requireContext(), trackFinderTracks, this)
                    val layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    recyclerHomeTf.layoutManager = layoutManager
                    recyclerHomeTf.adapter = adapter
                } else {
                    liHomeTfWarn.visibility = View.VISIBLE
                }
            })
        Thread{
            viewModel!!.trackFinderGetAll()
        }.start()
    }
    private fun initHomeTfWarn(){
        tvHomeGoTf.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        tvHomeGoTf.setOnClickListener {
        }
        buttonClearList.setOnClickListener {
            Thread{
                viewModel!!.trackFinderDeleteAll()
            }.start()
            recyclerHomeTf.visibility=View.GONE
            liHomeTfWarn.visibility=View.VISIBLE
            buttonClearList.visibility=View.GONE
            Log.d(TAG, "initHomeTfWarn: Deleting Done!")
        }
    }
    //TrackFinderItem OnClick
    override fun onItemClicked(track: TrackFinderTracks) {
        val bundle = Bundle()
        bundle.putString("id", track.trackId)
        bundle.putString("artistId", track.artistId)
        bundle.putString("name", track.trackName)
        bundle.putString("image", track.albumImage)
        findNavController().navigate(R.id.action_homeFragment_to_detailedTrackFragment, bundle)
    }


}