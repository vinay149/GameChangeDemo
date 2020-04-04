package com.example.gamechangedemo.ui.comment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechangedemo.R
import com.example.gamechangedemo.databinding.LayoutCommentItemBindingImpl
import kotlinx.android.synthetic.main.layout_comment_item.view.*

class CommentPageAdapter:ListAdapter<Comment, CommentPageAdapter.CommentViewHolder>(DIFF) {


    var user:MutableLiveData<String> = MutableLiveData()
    var body:MutableLiveData<String> = MutableLiveData()
    lateinit var bindingImpl:LayoutCommentItemBindingImpl
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
         val inflater = LayoutInflater.from(parent.context)
          bindingImpl =  DataBindingUtil.inflate(inflater, R.layout.layout_comment_item,parent,false)
         val view = bindingImpl.root
         return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {itemData->
            holder.setData(itemData)
        }
    }

    inner class CommentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun setData(item:Comment){
            Log.d("DataHere","item${item}")
            itemView.tv_commnet_user.text = item.user.login
            itemView.tv_comment_body.text = item.body
            //user.value = item.user.login
            //body.value = item.body
        }
    }

    companion object{
        private val DIFF = object :DiffUtil.ItemCallback<Comment>(){
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.body == oldItem.body
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return  oldItem==newItem
            }

        }
    }
}

data class Comment(val user:UserData, val body:String)

data class UserData(val login:String)
