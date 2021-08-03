package com.uhi5d.statibud.presentation.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.uhi5d.statibud.R
import com.uhi5d.statibud.application.PkceUtil
import com.uhi5d.statibud.application.ToastHelper
import com.uhi5d.statibud.databinding.FragmentLoginBinding
import com.uhi5d.statibud.di.TOKEN_BASE_URL
import com.uhi5d.statibud.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var toastHelper: ToastHelper

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var codeVerifier: String
    private lateinit var codeChallenge: String
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.codeVerifier.observe(viewLifecycleOwner){
            if (it.isNullOrBlank()){
                val pkceUtil = PkceUtil()
                val cv = pkceUtil.generateCodeVerifier()
                val cc = pkceUtil.generateCodeChallenge(cv)
                viewModel.setCodeVerifier(cv)
                viewModel.setCodeChallenge(cc)
            }
            codeVerifier = it
        }
        viewModel.codeChallenge.observe(viewLifecycleOwner){codeChallenge = it}

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse("https://accounts.spotify.com/authorize" +
                    "?response_type=code&client_id=$CLIENT_ID" +
                    "&redirect_uri=$REDIRECT_URI" +
                    "&scope=$SCOPE" +
                    "&state=besirkaraogluisthehighlyrecommendedstatehere" +
                    "&code_challenge=$codeChallenge" +
                    "&code_challenge_method=S256"))
            buttonLoadingState()
            startActivity(intent)
        }
        viewModel.token.observe(viewLifecycleOwner){ state ->

            when(state){
                is DataState.Success -> {
                    token = state.data.accessToken
                    buttonSuccessState()
                    try {
                        view.findNavController().navigate(R.id.action_loginFragment_to_inAppNav)
                    }catch (e:Exception){
                        throw Exception(e)
                    }
                }
                is DataState.Fail ->
                    toastHelper.errorMessage(requireContext(),state.e.message!!)
                DataState.Loading -> {
                    buttonLoadingState()
                }
            }

        }




    }

    override fun onResume() {
        super.onResume()
        val uri = requireActivity().intent.data
        if (uri!= null && uri.toString().startsWith(REDIRECT_URI)){
            if (uri.toString().contains("code")){
                val code = uri.getQueryParameter("code")
                if (code != null) {
                    viewModel.getToken(
                        TOKEN_BASE_URL,
                        CLIENT_ID,
                        "authorization_code",code,
                        REDIRECT_URI,
                        codeVerifier)
                }else{
                    toastHelper.sendToast(getString(R.string.auth_response_error))
                }
            }else{
                toastHelper.sendToast(String.format(getString(R.string.auth_denied),uri.getQueryParameter("error")))
            }
        }
    }


    private fun buttonLoadingState(){
        with(binding.buttonLogin){
            isEnabled = false
            text = getString(R.string.please_wait)
            setCompoundDrawables(null,null,null,null)
        }
    }

    private fun buttonSuccessState(){
        with(binding.buttonLogin){
            isEnabled = true
            text = getString(R.string.button_login)
            setCompoundDrawables(null,null,AppCompatResources.getDrawable(requireContext(),R.drawable.ic_spotify_logo_with_text),null)
        }
    }
}