package com.noisyninja.androidlistpoc.layers.network

import com.noisyninja.androidlistpoc.layers.Utils
import com.noisyninja.androidlistpoc.model.MeResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * network module to make network calls
 * Created by sudiptadutta on 27/04/18.
 */

open class NetworkModule @Inject constructor(private val mHttpClient: HttpClient) {

    init {
        Utils.logI(this.javaClass, "NetworkModule created")
    }

    fun getPeople(iCallback: ICallback<*>, page: Int, count: Int, seed: Int): GenericObserver<Any> {
        return getCustomerObservable(page, count, seed).subscribeWith(getObserver(iCallback))
    }

    private fun getCustomerObservable(page: Int, count: Int, seed: Int): Observable<MeResponse> {
        return mHttpClient.client.create(INetworkDao::class.java)
                .getPeople(page, count, seed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun <T> getObserver(iCallback: ICallback<*>): GenericObserver<T> {
        return GenericObserver(iCallback)
    }
}
