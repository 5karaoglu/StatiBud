package com.example.contestifyfirsttry.Profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private var TAG = "Profile Fragment"

    private var viewModel: MainViewModel? = null
    private var token:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //disabling onbackpressed
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){}
        callback.isEnabled = true
        token = getToken()
        viewModel = MainViewModel(this)
        getUserInfo(token!!)

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
}