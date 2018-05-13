package com.noisyninja.androidlistpoc.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import com.noisyninja.androidlistpoc.BuildConfig
import com.noisyninja.androidlistpoc.layers.database.viewmodel.MeViewModel
import com.noisyninja.androidlistpoc.layers.di.NinjaComponent
import com.noisyninja.androidlistpoc.layers.network.ICallback
import com.noisyninja.androidlistpoc.model.Me
import com.noisyninja.androidlistpoc.model.MeResponse

/**
 * main presenter
 * Created by sudiptadutta on 12/05/18.
 */

class MainPresenter internal constructor(private val iMainActivity: IMainActivity, private val ninjaComponent: NinjaComponent) : IMainPresenter, ICallback<MeResponse> {

    init {
        getList()
    }

    override fun getList() {
        ninjaComponent.network().getPeople(this, 1, BuildConfig.RESULT_COUNT.toInt())
    }

    override fun showDetail(meId: Int) {
        /* val intent = Intent(ninjaApplication?.applicationContext, DetailActivity::class.java)
          intent.putExtra("customerID", id)
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
          ninjaApplication?.applicationContext?.startActivity(intent)*/
    }

    override fun onSuccess(result: MeResponse?) {
        ninjaComponent.util().logI("net response")
        handleResponse(result?.people)
    }


    override fun onError(t: Throwable) {

        val meViewModel: MeViewModel = ViewModelProviders.of(iMainActivity as AppCompatActivity, ninjaComponent.
                vmf()).get(MeViewModel::class.java)

        meViewModel.getMe().
                observe(iMainActivity as AppCompatActivity, object : Observer<List<Me>> {
                    override fun onChanged(@Nullable result: List<Me>?) {

                        ninjaComponent.util().logI("db response")
                        // customerViewModel.getCustomers().removeObserver(this)//to not update
                        handleResponse(result)
                    }
                })
    }

    private fun handleResponse(result: List<Me>?) {
        if (result == null) {
            ninjaComponent.util().logI("null response")
            iMainActivity.setList(ArrayList())
        } else {
            ninjaComponent.util().logI("got response")
            iMainActivity.setList(ArrayList(result))
            ninjaComponent.database2().insertAll(result)
        }
    }
}
