package com.noisyninja.androidlistpoc.layers.network;

import com.noisyninja.androidlistpoc.model.MeResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * network module to make network calls
 * Created by sudiptadutta on 27/04/18.
 */

public class NetworkModule {
    private HttpClient mHttpClient;

    @Inject
    public NetworkModule(HttpClient httpClient) {
        mHttpClient = httpClient;
    }

    public GenericObserver<Object> getPeople(final ICallback iCallback, int page, int count, int seed) {
        return getCustomerObservable(page, count, seed).subscribeWith(getObserver(iCallback));
    }

    private Observable<MeResponse> getCustomerObservable(int page, int count, int seed) {
        return mHttpClient.getClient().create(INetworkDao.class)
                .getPeople(page, count, seed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> GenericObserver<T> getObserver(final ICallback iCallback) {
        return new GenericObserver<>(iCallback);
    }
}
