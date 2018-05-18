package com.noisyninja.androidlistpoc.layers.di

import android.content.Context
import android.content.res.Resources
import com.noisyninja.androidlistpoc.NinjaApp
import com.noisyninja.androidlistpoc.layers.AppExecutors
import com.noisyninja.androidlistpoc.layers.UtilModule
import com.noisyninja.androidlistpoc.layers.database.DataBaseModule
import com.noisyninja.androidlistpoc.layers.database.viewmodel.ViewModelFactory
import com.noisyninja.androidlistpoc.layers.network.HttpClient
import com.noisyninja.androidlistpoc.layers.network.NetworkModule
import com.squareup.leakcanary.RefWatcher
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton

/**
 * module containing dependency modules
 * Created by sudiptadutta on 27/04/18.
 */

@Module
class NinjaModule(private val application: NinjaApp, val refWatcher: RefWatcher) {

    @Provides
    @Singleton
    fun provideApplication(): NinjaApp {
        return application
    }

    @Provides
    @Singleton
    fun provideRefWatch(): RefWatcher {
        return refWatcher
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideResources(): Resources {
        return application.resources
    }

    @Provides
    @Singleton
    fun provideHttpClient(context: Context, utilModule: UtilModule): HttpClient {
        return HttpClient(context, utilModule)
    }

    @Provides
    @Singleton
    fun provideNetwork(httpClient: HttpClient): NetworkModule {
        return NetworkModule(httpClient)
    }

    @Provides
    @Singleton
    @Named("diskIOExecutor")
    fun diskIOExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    @Named("networkIOExecutor")
    fun networkIOExecutor(): Executor {
        return Executors.newFixedThreadPool(3)
    }

    @Provides
    @Singleton
    fun mainThreadExecutor(): Executor {
        return AppExecutors.MainThreadExecutor()
    }

    @Provides
    @Singleton
    fun provideExecutor(@Named("diskIOExecutor") diskIOExecutor: Executor, @Named("networkIOExecutor") networkIOExecutor: Executor, mainThreadExecutor: AppExecutors.MainThreadExecutor): AppExecutors {
        return AppExecutors(diskIOExecutor, networkIOExecutor, mainThreadExecutor)
    }

    @Provides
    @Singleton
    fun provideDataBase(utilModule: UtilModule, application: NinjaApp): DataBaseModule {
        return DataBaseModule(utilModule, application)
    }

    @Provides
    @Singleton
    fun provideUtil(context: Context): UtilModule {
        return UtilModule(context)
    }
}