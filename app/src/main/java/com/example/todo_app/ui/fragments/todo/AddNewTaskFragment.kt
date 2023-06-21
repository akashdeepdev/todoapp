package com.example.todo_app.ui.fragments.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.todo_app.R
import com.example.todo_app.databinding.FragmentAddNewTaskBinding
import com.example.todo_app.viewmodels.TodoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewTaskFragment(private val todoViewModel: TodoViewModel) : BottomSheetDialogFragment(),View.OnClickListener {
    private lateinit var binding:FragmentAddNewTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_task, container, false)
        binding.todoViewModel = todoViewModel
        binding.saveTask.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(p0: View?) {
        if(p0==binding.saveTask){
            this.dismiss()
            todoViewModel.addNewTask()
        }
    }

    companion object {
        const val TAG = "ADD_TASK_SCREEN"
        fun newInstance(todoViewModel: TodoViewModel) = AddNewTaskFragment(todoViewModel)
    }


}