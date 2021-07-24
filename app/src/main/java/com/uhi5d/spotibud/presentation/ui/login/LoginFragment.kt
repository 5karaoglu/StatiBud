package com.uhi5d.spotibud.presentation.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.application.PkceUtil
import com.uhi5d.spotibud.databinding.FragmentLoginBinding
import com.uhi5d.spotibud.di.TOKEN_BASE_URL
import com.uhi5d.spotibud.util.DataState
import com.uhi5d.spotibud.util.showIf
import com.uhi5d.spotibud.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
            Log.d("TAG", "onViewCreated: $codeChallenge")
            startActivity(intent)
        }
        viewModel.token.observe(viewLifecycleOwner){ state ->
            binding.pb.showIf { state is DataState.Loading }
            when(state){
                is DataState.Success -> {
                    token = state.data.accessToken
                    showToast(String.format(getString(R.string.token_success),state.data.expiresIn))
                }
                is DataState.Fail ->
                    showToast(String.format(getString(R.string.token_fail),state.e.message))
            }
        }



        viewModel.t.observe(viewLifecycleOwner){
            if (it == token){
                val action = LoginFragmentDirections.actionLoginFragmentToNavigation()
                findNavController().navigate(action)
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
                    showToast(getString(R.string.auth_response_error))
                }
            }else{
             showToast(String.format(getString(R.string.auth_denied),uri.getQueryParameter("error")))
            }
        }
    }
}