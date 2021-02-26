package com.uhi5d.spotibud.share

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.home.HomePagerAdapter
import com.uhi5d.spotibud.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_share.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ShareFragment : Fragment() {
    private var TAG = "Share Fragment"

    private var viewModel: MainViewModel? = null
    private var token:String? = null
    private lateinit var adapter : HomePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initShareViewPager()
        initShareButton()
    }
    private fun initShareButton(){
        shareButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        buttonShare.setOnClickListener {
            val targetView = getTargetView()
            val bitmap = getBitmapFromView(targetView)
            val screenShotPath = getScreenshot(bitmap!!)
            shareOnSocialMedia(screenShotPath)
        }
    }
    private fun initShareViewPager(){
        val titles = arrayListOf<String>("1", "2","3", "4")
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
    private fun getScreenshot(bitmap: Bitmap): File {
        var newFile : File? = null
        try{
            newFile = File(requireContext().filesDir,"screenShot.png")
            Log.d(TAG, "getScreenshot: ${newFile.absolutePath}")
            val out = FileOutputStream(newFile)
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out)
            out.flush()
            out.close()
        }catch (e: IOException){
            Log.d(TAG, "onViewCreated: ${e.message}")
        }
        return newFile!!
    }
    // getting current view as bitmap
    private fun getBitmapFromView(view:View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width,view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
    //getting image from given file path
    private fun shareOnSocialMedia(path: File){
        if (path.exists()){
            // Create the URI from the media
            var imageUri = FileProvider.getUriForFile(requireContext(),
                "com.uhi5d.spotibud.provider",path)
            Log.d(TAG, "shareOnInstagram: file exists $imageUri")
            val sourceApplication = "com.uhi5d.spotibud"
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