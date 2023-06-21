package com.example.todo_app.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todo_app.R
import com.example.todo_app.databinding.FragmentSignupBinding
import com.example.todo_app.utils.AppContants
import com.example.todo_app.utils.LogUtils
import com.example.todo_app.utils.Resource
import com.example.todo_app.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentSignupBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()
        viewModel.signupLiveData.observe(requireActivity()) { observeSignupFlow(it) }
        binding.apply {
            buttonLoginUser.setOnClickListener(this@SignupFragment)
        }
        return binding.root
    }

    private fun observeSignupFlow(value: Resource<FirebaseUser>?){
        value.let {
            when(it){
                is Resource.Loading->{
                    toggleSignupButton(false)
                }
                is Resource.Success->{
                    toggleSignupButton(true)
                    findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
                }
                is Resource.Failure->{
                    toggleSignupButton(true)
                    it.apply {
                        if(this.error.type===AppContants.EMAIL){
                            binding.email.error= this.error.message.toString()
                        }

                        if(this.error.type===AppContants.PASSWORD){
                            binding.password.error= this.error.message.toString()
                        }

                        if(this.error.type===AppContants.NAME){
                            binding.name.error= this.error.message.toString()
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

    private fun toggleSignupButton(enabled:Boolean){
        binding.buttonCreateAccount.isClickable = enabled
        if(enabled) {
            binding.buttonCreateAccount.text = "Sign Up"
        }else{
            binding.buttonCreateAccount.text = "Loading..."
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding.buttonLoginUser->{
                findNavController().popBackStack()
            }
        }
    }

    companion object {
        const val TAG = "SIGN_UP_SCREEN"
    }


}