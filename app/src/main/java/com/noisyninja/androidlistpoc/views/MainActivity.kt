package com.noisyninja.androidlistpoc.views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager
import com.noisyninja.androidlistpoc.R
import com.noisyninja.androidlistpoc.layers.di.NinjaInjector.ninjaComponent
import com.noisyninja.androidlistpoc.model.Me
import com.noisyninja.androidlistpoc.views.custom.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


open class MainActivity : AppCompatActivity(), IMainActivity {

    private var mResultList: ArrayList<ArrayList<Me>> = ArrayList()
    private lateinit var mIMainPresenter: IMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mIMainPresenter = MainPresenter(this, ninjaComponent)

        setupList()
        mIMainPresenter.getList()
    }

    //region UI
    private fun setupList() {
        val mLayoutManager = StickyHeaderGridLayoutManager(3)
        mLayoutManager.setHeaderBottomOverlapMargin(resources.getDimensionPixelSize(R.dimen.text_margin))

        recyclerList.layoutManager = mLayoutManager
        recyclerList.adapter = MainAdapter(mResultList, mIMainPresenter)
        recyclerList.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        recyclerList.itemAnimator = object : DefaultItemAnimator() {
            override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
                dispatchRemoveFinished(holder)
                return false
            }
        }

        refresh_layout.setOnRefreshListener {
            mIMainPresenter.getList()
        }

        fab.setOnClickListener { view ->
            /*
            * this ugly bit of code is because StickyHeaderGridLayoutManager
            * can't handle notifydata when the data source change has different section and offsets
            */
            mIMainPresenter.reverseList(mResultList)
            recyclerList.adapter = null //lose reference of last adapter to free memory
            recyclerList.adapter = MainAdapter(mResultList, mIMainPresenter)  //create new adapter to prevent notifydatasetchanged crash

            Snackbar.make(view, ninjaComponent.util().getStringRes(R.string.reverse), Snackbar.LENGTH_LONG)
                    //.setAction("Action", null)
                    .show()
        }
    }

    override fun setList(result: ArrayList<ArrayList<Me>>) {
        mResultList.clear()
        mResultList.addAll(result)
        handleShowError(false, null)
    }

    /**
     * show an error message if loading fails
     */
    private fun handleShowError(isError: Boolean, t: Throwable?) {
        runOnUiThread({
            recyclerList.adapter = MainAdapter(mResultList, mIMainPresenter)
            recyclerList.adapter.notifyDataSetChanged()
            refresh_layout.isRefreshing = false

            if (isError) {
                recyclerList.visibility = GONE
                recyclerText.visibility = VISIBLE
                recyclerText.text = t?.message
            } else {
                recyclerList.visibility = VISIBLE
                recyclerText.visibility = GONE
            }
        })
    }

}
