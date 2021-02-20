package com.example.contestifyfirsttry.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.*
import com.example.contestifyfirsttry.main.MainViewModel
import com.example.contestifyfirsttry.model.*
import com.example.contestifyfirsttry.share.ShareLayoutFour
import com.example.contestifyfirsttry.share.ShareLayoutOne
import com.example.contestifyfirsttry.share.ShareLayoutThree
import com.example.contestifyfirsttry.share.ShareLayoutTwo
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class HomeFragment : Fragment(),
    RecommendationsAdapter.OnItemClickListener,
    RecentTracksAdapter.OnItemClickListener{
    private var TAG = "Home Fragment"

    private var viewModel: MainViewModel? = null
    private var token:String? = null
    private lateinit var adapter : HomePagerAdapter

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
        initVisibility()
        initShareViewPager()
        initShareButton()
        token = getToken()
        // ViewModel components
        var factory = CustomViewModelFactory(this, requireContext())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        initRecentTracks()
        getRecommendations(token!!)
        initUserInfo()

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
        viewModel!!.getUser(token!!)
    }
    private fun initShareButton(){
        buttonShare.setOnClickListener {
            val targetView = getTargetView()
            val bitmap = getBitmapFromView(targetView)
            val screenShotPath = getScreenshot(bitmap!!)
            shareOnSocialMedia(screenShotPath)
        }
    }

    override fun onStart() {
        super.onStart()
        initShareViewPager()
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

    private fun getToken():String{
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences(
            "spotifystatsapp",
            Context.MODE_PRIVATE
        )
        val token = sharedPreferences.getString("token", "")

        Log.d(TAG, "onViewCreated:$token ")
        return token!!
    }

    private fun initRecentTracks(){
        viewModel!!.recentTracks.observe(requireActivity(),
            Observer<RecentTracks> { t ->
                generateDataRecentTracks(t!!, 5)
                recyclerButtonClick(t!!)
            })

        viewModel!!.getRecentTracks(token!!)
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
        var tS : String
        viewModel!!.artistsListShortTerm.observe(viewLifecycleOwner,
            Observer<Artists> { t ->
                aS = artistSeed(t!!)
                viewModel!!.getMyTracksLimited(token!!, "short_term", 2)
            })
        viewModel!!.tracksListShortTerm.observe(viewLifecycleOwner,
            Observer<Tracks> { t ->
                tS = trackSeed(t!!)
                viewModel!!.getRecommendations(token!!, aS!!, tS!!)
            })
        viewModel!!.recommendations.observe(viewLifecycleOwner,
            Observer<Recommendations> { t ->
                generateRecommendations(t!!)
            })
        viewModel!!.getMyArtistsLimited(token!!, "short_term", 2)
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
        Log.d(TAG, "artistSeedRaw:$list ")
        //list = functions.encodeString(list)
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
        Log.d(TAG, "trackSeedRaw:$list ")
        //list = functions.encodeString(list)
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

    private fun initShareViewPager(){
        val titles = arrayListOf<String>("No.1", "No.2","No.3", "No.4")
        val tabList = arrayListOf<Fragment>(ShareLayoutOne(), ShareLayoutTwo(),ShareLayoutThree(),ShareLayoutFour())
        adapter = HomePagerAdapter(childFragmentManager, titles, tabList)
        pagerHome.adapter = adapter
        pagerHome.offscreenPageLimit = 4
        tabLayoutHome.setupWithViewPager(pagerHome)}

    ////////////////// Sharing image on social media section////////////
    private fun getTargetView(): View {
        var view : View? = null
        when(pagerHome.currentItem){
            0 -> view = requireActivity().findViewById<View>(R.id.shareLayout1)
            1 -> view = requireActivity().findViewById<View>(R.id.shareLayout2)
            2 -> view = requireActivity().findViewById<View>(R.id.shareLayout3)
            3 -> view = requireActivity().findViewById<View>(R.id.shareLayout4)
        }
        return view!!
    }

    //saving bitmap to phone
    private fun getScreenshot(bitmap: Bitmap):File{
        var newFile : File? = null
        try{
            newFile = File(requireContext().filesDir,"screenShot.png")
            Log.d(TAG, "getScreenshot: ${newFile.absolutePath}")
            val out = FileOutputStream(newFile)
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out)
            out.flush()
            out.close()
        }catch (e:IOException){
            Log.d(TAG, "onViewCreated: ${e.message}")
        }
        return newFile!!
    }
    // getting current view as bitmap
    private fun getBitmapFromView(view:View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
    //getting image from given file path
    private fun shareOnSocialMedia(path: File){
        if (path.exists()){
            // Create the URI from the media
            var imageUri = FileProvider.getUriForFile(requireContext(),
                "com.example.contestifyfirsttry.provider",path)
            Log.d(TAG, "shareOnInstagram: file exists $imageUri")
            val sourceApplication = "com.example.contestifyfirsttry"
            // Create the new Intent using the 'Send' action.
            val share = Intent(Intent.ACTION_SEND)
             // Set the MIME type
            share.type = "image/*"
            // Add the URI to the Intent.
            share.putExtra("source_application", sourceApplication)
            share.putExtra(Intent.EXTRA_STREAM, imageUri)
            // Broadcast the Intent.
            startActivity(Intent.createChooser(share, "Share to"))
        }else{
            Log.d(TAG, "shareOnInstagram: file dont exists")
        }
    }



}