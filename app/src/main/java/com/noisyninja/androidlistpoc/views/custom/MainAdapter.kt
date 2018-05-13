package com.noisyninja.androidlistpoc.views.custom

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.noisyninja.androidlistpoc.R
import com.noisyninja.androidlistpoc.databinding.ListMainBinding
import com.noisyninja.androidlistpoc.model.Me
import com.noisyninja.androidlistpoc.views.IMainPresenter

/**
 * main adapter for sticky grids layout
 * Created by sudiptadutta on 12/05/18.
 */

class MainAdapter(private val mResultsList: ArrayList<Me>, private val mIMainPresenter: IMainPresenter) : RecyclerView.Adapter<MainAdapter.MeViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MeViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = DataBindingUtil.inflate<ListMainBinding>(inflater, R.layout.list_main, viewGroup, false)
        return MeViewHolder(binding)
    }

    override fun onBindViewHolder(tableViewHolder: MeViewHolder, position: Int) {
        val table = mResultsList.get(position)
        tableViewHolder.bind(table, mIMainPresenter)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.list_main
    }

    override fun getItemCount(): Int {
        return mResultsList.size
    }

    inner class MeViewHolder(private val mListDetailBinding: ListMainBinding) : RecyclerView.ViewHolder(mListDetailBinding.root) {

        fun bind(results: Me, mainPresenter: IMainPresenter) {

            mListDetailBinding.me = results
            mListDetailBinding.mainPresenter = mainPresenter
            mListDetailBinding.executePendingBindings()
        }
    }
}
