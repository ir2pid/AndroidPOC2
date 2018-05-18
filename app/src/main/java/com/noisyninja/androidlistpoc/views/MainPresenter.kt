package com.noisyninja.androidlistpoc.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import com.noisyninja.androidlistpoc.layers.database.viewmodel.MeViewModel
import com.noisyninja.androidlistpoc.layers.di.NinjaComponent
import com.noisyninja.androidlistpoc.model.Me
import java.util.*
import kotlin.collections.ArrayList

/**
 * main presenter
 * Created by sudiptadutta on 12/05/18.
 */

class MainPresenter internal constructor(private val iMainActivity: IMainActivity, private val ninjaComponent: NinjaComponent) : IMainPresenter {

    private var meViewModel: MeViewModel = ViewModelProviders.of(iMainActivity as AppCompatActivity, ninjaComponent.
            vmf()).get(MeViewModel::class.java)

    init {
        getList()
    }

    override fun getList() {

        meViewModel.getMe().
                observe(iMainActivity as AppCompatActivity, object : Observer<List<Me>> {
                    override fun onChanged(@Nullable result: List<Me>?) {
                        //meViewModel.getMe().removeObserver(this)//to not update
                        handleResponse(result)
                    }
                })
    }

    override fun showDetail(me: Me) {
        //todo stub to open detail
        val intent = Intent(ninjaComponent.appContext(), DetailActivity::class.java)
        intent.putExtra("meID", me.name.first)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ninjaComponent.appContext().startActivity(intent)
    }

    override fun reverseList(arrayList: ArrayList<ArrayList<Me>>): ArrayList<ArrayList<Me>> {
        Collections.reverse(arrayList)

        //hack to prevent stickygridadapter from accessing invalid indexes, we set old list to zero and add new copy
        return ArrayList(arrayList)
    }

    private fun handleResponse(result: List<Me>?) {
        if (result == null) {
            ninjaComponent.util().logI("null response")
            iMainActivity.setList(ArrayList())
        } else {
            ninjaComponent.util().logI("got response")

            val array: ArrayList<ArrayList<Me>> = makeGridLayoutReady(result)
            //ninjaComponent.util().logI(ninjaComponent.util().toJson(array))
            iMainActivity.setList(array)
        }
    }

    /**
     * this creates a nested array of users for the gridlayout and sticky headers
     */
    private fun makeGridLayoutReady(result: List<Me>): ArrayList<ArrayList<Me>> {
        val array: ArrayList<ArrayList<Me>> = ArrayList()
        var array2: ArrayList<Me> = ArrayList()
        var counter = 0
        var pageCount = 0
        for (me: Me in result) {
            me.page = pageCount
            array2.add(me)
            counter++
            if (counter % 9 == 0) {
                pageCount++
                array.add(array2)
                array2 = ArrayList()
            }
        }
        if (array.size < counter) {//adding leftovers
            array.add(array2)
        }
        return array
    }


}
