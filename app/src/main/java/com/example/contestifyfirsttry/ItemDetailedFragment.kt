package com.example.contestifyfirsttry

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.contestifyfirsttry.model.Item
import com.example.contestifyfirsttry.model.RecentTracks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_item_detailed.*

class ItemDetailedFragment : Fragment(){
    private val TAG = "Detailed Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getting currentId
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        val bundle = requireArguments()
        val name = bundle.get("name") as String
        val id = bundle.get("id") as String
        val image = bundle.get("image") as String
        setData(name,id,image)

    }

    fun setData(name:String,id:String,image:String){
        Log.d(TAG, "setData: ${name}")
        Picasso.get()
            .load(image)
            .fit().centerCrop()
            .into(imageView)
    }
}