package com.example.gamechangedemo.ui.comment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechangedemo.GameChangeApplication
import com.example.gamechangedemo.R
import com.example.gamechangedemo.data.CommentRepositoryImpl
import com.example.gamechangedemo.databinding.LayoutDetailsPageBinding
import com.example.gamechangedemo.di.comment.CommentComponent
import com.example.gamechangedemo.di.comment.CommentViewModelFactory
import com.example.gamechangedemo.ui.Dialog
import com.example.gamechangedemo.ui.IssueListViewModel
import javax.inject.Inject

class CommentPageFragment(val issueListViewModel: IssueListViewModel):Fragment()
{

    @Inject
    lateinit var repositoryImpl: CommentRepositoryImpl

    lateinit var binding:LayoutDetailsPageBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter:CommentPageAdapter
    lateinit var viewModel:CommentViewModel
    lateinit var component:CommentComponent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_details_page,container,false)
        adapter = CommentPageAdapter()
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initResource()
        issueListViewModel.issueId?.let {
            viewModel.fetchComment(it)
        }
        viewModel.getAllCommentListById()?.observe(this, Observer {
            Log.d("commentData","getCommentData${it}")
            if(it.size==0){
                showPopUp()
            }else {
                adapter.submitList(it)
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }
    private fun initResource(){
        recyclerView = binding.rvRecylerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onAttach(context: Context) {
        component = GameChangeApplication.instance.appComponent.commentComponent().create()
        component.inject(this)
        var viewModelFactory: ViewModelProvider.Factory = CommentViewModelFactory(repositoryImpl)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(CommentViewModel::class.java)
        super.onAttach(context)
    }

    private fun showPopUp() {
        val fragmentManager = fragmentManager
        val dialog = Dialog()
        fragmentManager?.let {
            dialog.show(fragmentManager, "no_comment_dialog")
        }
    }
}