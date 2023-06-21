package com.example.todo_app.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.data.models.Todo
import com.example.todo_app.databinding.TodoItemCardBinding
import com.example.todo_app.interfaces.ItemClickListener
import com.example.todo_app.utils.LogUtils
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class TodoListAdapter @Inject constructor (private var user:FirebaseUser,private val onItemClickListener: ItemClickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var todoList = ArrayList<Todo>()
    private val tempList = ArrayList<Todo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<TodoItemCardBinding>(inflater,R.layout.todo_item_card,parent,false)
        return TodoItemViewHolder(binding)
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as TodoItemViewHolder
        val currentItem = todoList[position]
        val bind = viewHolder.bind(currentItem)
        bind.user = user
        bind.deleteTask.setOnClickListener(){
            deleteItem(position)
            onItemClickListener.onClickItemCard(currentItem,position)
        }
    }


    private fun deleteItem(position: Int){
        todoList.remove(todoList[position])
        notifyItemChanged(position)
    }

    fun updateListData(todos:ArrayList<Todo>){
        todoList = todos
        createTempList(todos)
        notifyDataSetChanged()
    }

    private fun createTempList(todos:ArrayList<Todo>){
        tempList.clear()
        tempList.addAll(todos)
    }

    fun filterList(newText: String?) {
        todoList = tempList
        if(newText!=null){
            val filteredList = ArrayList<Todo>()

            for(i in todoList){
                if(i.task.toString().uppercase().contains(newText.uppercase())){
                    filteredList.add(i)
                }
            }
            todoList = filteredList
            notifyDataSetChanged()
        }
    }

    inner class TodoItemViewHolder(private val binding: TodoItemCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Todo):TodoItemCardBinding{
            binding.apply {
                todoModel = item
                if (item.completed){
                    task.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
            return binding
        }
    }
}