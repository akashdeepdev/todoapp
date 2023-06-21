package com.example.todo_app.ui.fragments.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.todo_app.R
import com.example.todo_app.adapters.TodoListAdapter
import com.example.todo_app.data.models.Todo
import com.example.todo_app.databinding.FragmentCompletedTodoBinding
import com.example.todo_app.interfaces.ItemClickListener
import com.example.todo_app.utils.LogUtils
import com.example.todo_app.utils.Resource
import com.example.todo_app.viewmodels.AuthViewModel
import com.example.todo_app.viewmodels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletedTodoFragment : Fragment(),ItemClickListener,OnRefreshListener, SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentCompletedTodoBinding
    private lateinit var todoListAdapter: TodoListAdapter
    private val authViewModel by viewModels<AuthViewModel>()
    private val todoViewModel by viewModels<TodoViewModel>()
    private var todoList = ArrayList<Todo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_completed_todo, container, false)
        todoListAdapter = TodoListAdapter(authViewModel.currentUser!!,this)
        binding.listAdapter = todoListAdapter
        binding.swipeToRefresh.setOnRefreshListener(this)
        binding.searchTodo.setOnQueryTextListener(this)
        todoViewModel.todoListLiveData.observe(requireActivity()){observeTodoListData(it)}
        return binding.root
    }

    private fun observeTodoListData(it: Resource<List<Todo>?>?) {
        it.let {
            when(it){
                is Resource.Loading->{
                    toggleLoader(true)
                }
                is Resource.Success->{
                    toggleLoader(false)
                    val list = it.result!!.filter { it.completed }.toCollection(ArrayList<Todo>())
                    todoList = list
                    todoListAdapter.updateListData(list)
                }
                is Resource.Failure->{
                    toggleLoader(false)
                }
                else->{}
            }
        }
    }

    override fun onClickItemCard(item: Todo, position: Int) {
        LogUtils.showLog(PendingTodoFragment.TAG,item.toString())
        val newTodoList = ArrayList<Todo>()
        todoList.remove(item)
        newTodoList.addAll(todoList)
        todoViewModel.updateTodoList(newTodoList.toList())
        todoViewModel.deleteTask(item.id)
    }
    private fun toggleLoader(isLoading:Boolean){
        binding.apply {
            if(isLoading){
                todoRecyclerView.visibility = View.GONE
                loader.visibility = View.VISIBLE
            }else{
                todoRecyclerView.visibility = View.VISIBLE
                loader.visibility = View.GONE
                binding.swipeToRefresh.isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        todoViewModel.getTodoList()
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        todoListAdapter.filterList(newText)
        return false
    }



    companion object {
        const val TAG = "COMPLETED_TASK_SCREEN"
        fun newInstance() = CompletedTodoFragment()
    }
}