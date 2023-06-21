package com.example.todo_app.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

 class ViewPagerAdapter(private val fragment: Fragment):FragmentStateAdapter(fragment) {
     private val fragmentsList = ArrayList<Fragment>()
     override fun getItemCount(): Int = fragmentsList.size

     override fun createFragment(position: Int): Fragment {
         return  fragmentsList[position]
     }
     fun updateFragmentList(fragments:ArrayList<Fragment>){
         fragmentsList.clear()
         fragmentsList.addAll(fragments)
     }
 }