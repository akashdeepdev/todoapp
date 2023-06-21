package com.example.todo_app.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.todo_app.R
import com.example.todo_app.databinding.FragmentSettingBinding
import com.example.todo_app.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment(),View.OnClickListener {
    private lateinit var binding:FragmentSettingBinding
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.user = authViewModel.currentUser
        Glide.with(this)
            .load(authViewModel.currentUser?.photoUrl)
            .centerCrop()
            .placeholder(R.drawable.baseline_account_circle_24)
            .into(binding.profileImage);
        binding.logout.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(p0: View?) {
        if(p0==binding.logout){
            authViewModel.logout()
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    companion object {
        const val TAG = "SETTING_SCREEN"
        fun newInstance() = SettingFragment()
    }
}