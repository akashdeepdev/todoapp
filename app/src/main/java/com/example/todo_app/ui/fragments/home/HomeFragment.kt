package com.example.todo_app.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todo_app.R
import com.example.todo_app.adapters.ViewPagerAdapter
import com.example.todo_app.databinding.FragmentHomeBinding
import com.example.todo_app.ui.fragments.todo.AddNewTaskFragment
import com.example.todo_app.ui.fragments.todo.CompletedTodoFragment
import com.example.todo_app.ui.fragments.todo.PendingTodoFragment
import com.example.todo_app.viewmodels.AuthViewModel
import com.example.todo_app.viewmodels.TodoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val todoViewModel by viewModels<TodoViewModel>()
    private lateinit var vpAdapter:ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        vpAdapter = ViewPagerAdapter(this)
        initializeSetup()
        return binding.root
    }

    private fun initializeSetup() {
        binding.apply {
            viewPager.isUserInputEnabled = false
            vpAdapter.updateFragmentList(arrayListOf(PendingTodoFragment.newInstance(),CompletedTodoFragment.newInstance(),SettingFragment.newInstance()))
            viewPager.adapter = vpAdapter
            bottomNavBar.itemActiveIndicatorHeight = 0
            bottomNavBar.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
                when(it.itemId){
                    R.id.pendingTodoFragment->{
                        viewPager.currentItem = 0
                        return@OnItemSelectedListener  true
                    }
                    R.id.completedTodoFragment->{
                        viewPager.currentItem = 1
                        return@OnItemSelectedListener  true
                    }
                    R.id.settingFragment->{
                        viewPager.currentItem = 2
                        return@OnItemSelectedListener  true
                    }
                    else-> false
                }
            })

        }
    }

    companion object {
        const val TAG = "HOME_SCREEN"
    }
}