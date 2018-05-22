package com.noisyninja.androidlistpoc

import android.content.res.Resources
import android.support.test.InstrumentationRegistry
import com.noisyninja.androidlistpoc.layers.AppExecutors
import com.noisyninja.androidlistpoc.layers.RefWatcherModule
import com.noisyninja.androidlistpoc.layers.UtilModule
import com.noisyninja.androidlistpoc.layers.database.DataBaseModule
import com.noisyninja.androidlistpoc.layers.database.viewmodel.MeViewModel
import com.noisyninja.androidlistpoc.layers.database.viewmodel.ViewModelFactory
import com.noisyninja.androidlistpoc.layers.di.NinjaComponent
import com.noisyninja.androidlistpoc.layers.network.NetworkModule
import org.mockito.Mockito

/**
 * Mock test application with mocked dagger dependencies
 * Created by sudiptadutta on 20/05/18.
 */
class TestApplication : NinjaApp() {

    val viewModelFactory = Mockito.mock(ViewModelFactory::class.java)
    val meViewModel = Mockito.mock(MeViewModel::class.java)

    override val ninjaComponent: NinjaComponent by lazy {

        DaggerTestComponent.builder().database(Mockito.mock(DataBaseModule::class.java))
                .app(InstrumentationRegistry.getTargetContext().applicationContext as TestApplication)
                .appContext(InstrumentationRegistry.getTargetContext())
                .executor(Mockito.mock(AppExecutors::class.java))
                .network(Mockito.mock(NetworkModule::class.java))
                .resources(Mockito.mock(Resources::class.java))
                .util(Mockito.mock(UtilModule::class.java))
                .refWatcher(Mockito.mock(RefWatcherModule::class.java))
                .vmf(viewModelFactory)
                .build()
        //.systemModule(SystemModule(this, LeakCanary.install(this)))
        //.repositoryModule(RepositoryModule())
        //.database()
    }

    init {
        Mockito.`when`(viewModelFactory.create(MeViewModel::class.java)).thenReturn(meViewModel)
    }
}