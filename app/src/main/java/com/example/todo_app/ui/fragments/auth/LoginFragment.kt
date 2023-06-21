package com.example.todo_app.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todo_app.R
import com.example.todo_app.databinding.FragmentLoginBinding
import com.example.todo_app.utils.AppContants
import com.example.todo_app.utils.LogUtils
import com.example.todo_app.utils.Resource
import com.example.todo_app.utils.await
import com.example.todo_app.viewmodels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        viewModel.loginLiveData.observe(requireActivity()) { observeLoginFlow(it) }
        binding.apply {
            buttonSignup.setOnClickListener(this@LoginFragment)
            buttonLoginByGoogle.setOnClickListener(this@LoginFragment)
        }
        return binding.root
    }

    override fun onClick(view: View?) {
        when(view){
        binding.buttonSignup -> {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
            binding.buttonLoginByGoogle->{
                signInWithGoogle()
            }

        }
    }

    private fun observeLoginFlow(value:Resource<FirebaseUser>?){
            value.let {
                when(it){
                    is Resource.Loading->{
                        toggleLoginButton(false)
                    }
                    is Resource.Success->{
                        toggleLoginButton(true)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    is Resource.Failure->{
                        toggleLoginButton(true)
                        it.apply {
                            if(error.type==AppContants.EMAIL){
                                binding.email.error= error.message.toString()
                            }

                            if(error.type==AppContants.PASSWORD){
                                binding.password.error= error.message.toString()
                            }

                            if(error.type==AppContants.EXCEPTION){
                                LogUtils.showToast(requireContext(),error.message)
                            }
                        }
                    }
                    else->{}
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.signInWithGoogle(requestCode,data)
    }

    private fun toggleLoginButton(enabled:Boolean){
        binding.buttonLogin.isClickable = enabled
        if(enabled) {
            binding.buttonLogin.text = "Log In"
        }else{
            binding.buttonLogin.text = "Loading..."
        }
    }

    companion object {
        const val TAG = "LOGIN_SCREEN"
        const val RC_SIGN_IN = 123
    }

}