package com.example.gamechangedemo.ui

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
import com.example.gamechangedemo.databinding.ActivityMainBinding
import com.example.gamechangedemo.databinding.ActivityMainBindingImpl
import com.example.gamechangedemo.di.AppComponent
import com.example.gamechangedemo.ui.comment.CommentPageFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class IssueFragment:Fragment(){

    lateinit var homePageComponent: AppComponent
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel:IssueListViewModel
    lateinit var adapter:IssueListAdapter
    lateinit var binding:ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    lateinit var clickHandler: ClickHandler


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.activity_main,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        viewModel.syncData()
        viewModel.getLatestIssueList()?.observe(this, Observer {
            it?.let { value->
                adapter.submitList(value)
                Log.d("LatestIssue", "data${it}")
            }
        })

        GameChangeApplication.instance.appComponent.rxBus().listen(ClickedHandlerOfIssueItem::class.java)
            .subscribe {
                Log.d("OpenIt", "Open${it.value}")
                it.value?.let {
                    viewModel.updateIssueId(it as Long)
                    navigateToCommentPage()
                }
            }
    }

    override fun onAttach(context: Context) {
        homePageComponent = ( GameChangeApplication.instance).appComponent
        homePageComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(IssueListViewModel::class.java)
        super.onAttach(context)
    }

    private fun navigateToCommentPage(){
        val commentFragment = CommentPageFragment(viewModel)
        val fragmentManager = getFragmentManager()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.
            replace(R.id.fragment_container,commentFragment)?.commit()
    }


    private fun init(){
        recyclerView = rv_issue_list
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = IssueListAdapter()
        recyclerView.adapter = adapter
    }
}