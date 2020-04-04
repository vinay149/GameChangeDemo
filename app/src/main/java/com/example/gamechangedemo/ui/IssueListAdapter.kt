package com.example.gamechangedemo.ui

import android.app.usage.UsageEvents
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gamechangedemo.GameChangeApplication
import com.example.gamechangedemo.R
import com.example.gamechangedemo.databinding.LayoutIssueItemBinding
import com.example.gamechangedemo.model.Issue
import com.example.gamechangedemo.model.IssueListEntity
import kotlinx.android.synthetic.main.layout_issue_item.view.*

class IssueListAdapter : PagedListAdapter<IssueListEntity, IssueListAdapter.IssueViewHolder>(DIFF) {


    val issueTitle: MutableLiveData<String> = MutableLiveData()
    val issueBody: MutableLiveData<String> = MutableLiveData()
    lateinit var binding: LayoutIssueItemBinding
    lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IssueViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_issue_item, parent, false)
        binding.adapter = this
        return IssueViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("Issue", "Issue${item}")
        item?.let {
            holder.setData(item)
        }
    }

    inner class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemData: IssueListEntity? = null

        init {
            itemView.cd_issue_item.setOnClickListener {
                itemData?.number?.let { issueId ->
                    GameChangeApplication.instance.appComponent.rxBus()
                        .send(ClickedHandlerOfIssueItem.of(issueId))
                }
            }
        }

        fun setData(item: IssueListEntity) {
            itemData = item
            issueTitle.value = item.title
            issueBody.value = item.body
        }

    }

    companion object {

        private val DIFF = object : DiffUtil.ItemCallback<IssueListEntity>() {
            override fun areItemsTheSame(
                oldItem: IssueListEntity,
                newItem: IssueListEntity
            ): Boolean {
                return oldItem.title == oldItem.title
            }

            override fun areContentsTheSame(
                oldItem: IssueListEntity,
                newItem: IssueListEntity
            ): Boolean {
                return oldItem.body == oldItem.body
            }

        }
    }
}

open class Evnet(val value: Any)
class ClickedHandlerOfIssueItem(issueID: Long) : Evnet(issueID) {
    companion object {
        fun of(issueID: Long) = ClickedHandlerOfIssueItem(issueID)
    }
}