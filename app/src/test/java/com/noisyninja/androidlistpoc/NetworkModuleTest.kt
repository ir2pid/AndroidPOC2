package com.noisyninja.androidlistpoc

import com.google.gson.Gson
import com.noisyninja.androidlistpoc.layers.Utils
import com.noisyninja.androidlistpoc.layers.network.NetworkModule
import com.noisyninja.androidlistpoc.model.MeResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NetworkModuleTest : BaseRepository() {

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setUpMocks()
        //setupReturns()
        setUpRepository()
        mMockWebServer.enqueue(MockResponse().setBody(Gson().toJson(meResponse)))

        //to make sure subscribeOn and observeOn run on same thread
        //async call becomes synchronous, thus waits for response
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }

    }


    @Test
    fun serverCallWithSuccess() {
        //Given
        val url = BuildConfig.BASE_URL

        val interceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(mMockWebServer.url(url))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val remoteDataSource = NetworkModule(retrofit)

        val meResponse = remoteDataSource
                .getPeople(page, BuildConfig.RESULT_COUNT.toInt(), BuildConfig.NETSYNC_SEED_VALUE.toInt())

        meResponse.subscribe(mSubscriber)
        mSubscriber.assertNoErrors()
        mSubscriber.assertValue({ it ->
            it.people.size == 100
        })
    }

    /**
     * test for network availability
     */
    @Test
    fun serverCallWithError() {
        //this is an invalid uri
        val url = BuildConfig.BASE_URL + BuildConfig.API_URI + "/"

        val interceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(mMockWebServer.url(url))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val remoteDataSource = NetworkModule(retrofit)

        val meResponse = remoteDataSource
                .getPeople(1, -0, BuildConfig.NETSYNC_SEED_VALUE.toInt())

        meResponse.subscribe(mSubscriber)
        mSubscriber.assertError({ t: Throwable -> t.message!!.isNotEmpty() })

    }

}