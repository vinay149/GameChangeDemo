package com.example.gamechangedemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechangedemo.GameChangeApplication
import com.example.gamechangedemo.R
import com.example.gamechangedemo.di.AppComponent
import com.example.gamechangedemo.di.HomePageComponent
import com.example.gamechangedemo.di.ViewModelFactory
import com.example.gamechangedemo.ui.comment.CommentPageFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var homePageComponent: AppComponent
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel:IssueListViewModel
    lateinit var adapter:IssueListAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var clickHandler: ClickHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_content)
        init()
    }

    private fun init(){
        val issueFragment = IssueFragment()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.
            add(R.id.fragment_container,issueFragment,"issue_fragment").commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.findFragmentByTag("comment_fragment")!=null){
            supportFragmentManager.popBackStack()
            //Log.d("okkkk","################")
            //super.onBackPressed()
        }else {
            super.onBackPressed()
            //supportFragmentManager.popBackStack("issue_fragment",FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}
