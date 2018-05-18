package com.noisyninja.androidlistpoc.layers.database.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.noisyninja.androidlistpoc.BuildConfig.NETSYNC_SEED_VALUE
import com.noisyninja.androidlistpoc.BuildConfig.RESULT_COUNT
import com.noisyninja.androidlistpoc.R
import com.noisyninja.androidlistpoc.layers.Utils
import com.noisyninja.androidlistpoc.layers.di.NinjaInjector.ninjaComponent
import com.noisyninja.androidlistpoc.layers.network.ICallback
import com.noisyninja.androidlistpoc.model.Me
import com.noisyninja.androidlistpoc.model.MeResponse
import com.noisyninja.androidlistpoc.model.Name
import io.reactivex.disposables.CompositeDisposable


/**
 * view model for Me
 * Created by sudiptadutta on 10/05/18.
 */

class MeViewModel : ViewModel(), ICallback<MeResponse> {

    private var errorResponse: Me = Me(Name(ninjaComponent.resources().getString(R.string.error_net)))
    var compositeDisposables = CompositeDisposable()


    private val meLiveData: LiveData<List<Me>>

    init {
        Utils.logI(this.javaClass, "MeViewModel")
        meLiveData = ninjaComponent.database().all
    }

    fun getMe(): LiveData<List<Me>> {
        Utils.logI(this.javaClass, "getMe")
        if (meLiveData.value == null || meLiveData.value!!.isEmpty() || meLiveData.value!!.size < 2) {
            Utils.logI(this.javaClass, "getMe network")
            compositeDisposables
                    .add(ninjaComponent.network()
                            .getPeople(this, 1, RESULT_COUNT.toInt(), NETSYNC_SEED_VALUE.toInt()))
        } else {
            Utils.logI(this.javaClass, "getMe database")
        }
        return meLiveData
    }

    override fun onSuccess(result: MeResponse?) {
        ninjaComponent.util().logI("net response")

        ninjaComponent.database().delete(errorResponse)
        ninjaComponent.database().insertAll(result?.people)
        dispose()

    }

    override fun onError(t: Throwable) {
        ninjaComponent.util().logI("no response")

        ninjaComponent.database().insert(errorResponse)
        dispose()
    }

    private fun dispose() {
        Utils.logI(this.javaClass, "disposing observable")
        if (!compositeDisposables.isDisposed) {
            compositeDisposables.dispose()
        }
    }
}
